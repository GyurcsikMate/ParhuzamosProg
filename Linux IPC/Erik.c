#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "semaphore.h"

int main() {
    int shmid = shmget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);
    int * x = (int *) shmat(shmid, NULL, 0);

    *x = 2;

    int semaphore = CSinit(1);

    int i;
    pid_t PID;

    for (i = 0; i < 10; i++) {
        PID = fork();

        if (PID == 0) {
            break;
        }
        else if (PID < 0) {
            exit(EXIT_FAILURE);
        }
    }

    if (PID == 0) {
        if (getpid() % 2 == 0) {
            sleep(3);
            CSwait(semaphore);
            //printf("x = %d", *x);
            *x *= 3;
            //printf(" * 3 = %d\n", *x);
            CSsignal(semaphore);
        }
        else {
            sleep(1);
            CSwait(semaphore);
            //printf("x = %d", *x);
            *x += 7;
            //printf(" + 7 = %d\n", *x);
            CSsignal(semaphore);
        }
    }
    else if (PID > 0) {
        for (int i = 0; i < 10; i++)
            wait(NULL);

        CSwait(semaphore);
        printf("%d\n", *x);
        CSsignal(semaphore);
    }
}