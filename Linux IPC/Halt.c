#include <stdio.h>
#include <stdlib.h>

// IPC: fork, wait, sleep
#include <unistd.h>
#include <sys/wait.h>

// IPC: shared memory segments,
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/ipc.h>

// IPC: semaphore
#include "semaphore.h"

#define noop

int main()
{
    int shmid = shmget(IPC_PRIVATE, 1, IPC_CREAT | 0666); // Request a shared memory segment and get it's id.
    int* shared_seg = (int*)shmat(shmid, NULL, 0); // Get the correctly (in this case as an integer) sized memory address for the shared segment
    *shared_seg = 42; // Set it to something positive as this was asked. (Maybe could be unsigned?)

    pid_t PID; // We will store the fork's id result here.
    int semaphore_set_id = critical_section_init(SEMAPHORE_VALUE_MUTUAL_EXCLUSION); // Initiate a semaphore set and get it's id.

    // First we should create a child processes.
    for(size_t i = 0; i < 10; i++) // We want to create 10 children.
    {
        PID = fork();
        if(PID == 0) // we are the child
        {
            break; // we only create the child process, for now, child doesn't do anything
        }
        else if(PID > 0)
        {
            noop; // parents don't do anything when creating the paralell forks
        }
        else // we could not fork
        {
            exit(EXIT_FAILURE); // exit with failure code
        }
    }

    // At this point we have 10 children from the parent.

    if(PID == 0) // If we are a child.
    {

        if((getpid() % 2) == 0)
        {
            sleep(3);
            critical_section_enter_wait(semaphore_set_id); // Enter critical section on semaphore set (by design, on the the set's first semaphore).
            (*shared_seg) *= 3;
            critical_section_send_signal(semaphore_set_id); // Leave critical section on semaphore set (by design, on the the set's first semaphore) and signal availability.
        }
        else
        {
            sleep(1);
            critical_section_enter_wait(semaphore_set_id); // Enter critical section on semaphore set (by design, on the the set's first semaphore).
            (*shared_seg) += 7;
            critical_section_send_signal(semaphore_set_id); // Leave critical section on semaphore set (by design, on the the set's first semaphore) and signal availability.
        }

    }

    else if(PID > 0) // If we are the parent.
    {
        for(size_t i = 0; i < 10; i++) // We will wait for 10 times.
        {
            wait(NULL); // Wait for the child process to complete.
            printf("%d\n", *shared_seg); // Print out the shared segment as an integer.
        }
    }

    return 0; // All is A-OK.
}