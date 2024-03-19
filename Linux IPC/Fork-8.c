#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <unistd.h>
#include "semaphore.h"

static int cnt = 0;

void work(const char * msg) {
  printf("%s %d\n", msg, cnt++);
  sleep(1);
}

int main() {
  int shmid = CSinit(0);

  if (fork()) {
    work("Parent");
    work("Parent");
    CSwait(shmid);
    work("Parent");
    work("Parent");
    work("Parent");
    work("Parent");
  } else {
    work("Child");
    work("Child");
    work("Child");
    work("Child");
    CSsignal(shmid);
    work("Child");
    work("Child");
  }
  
  // semctl(shmid, 1, IPC_RMID);
}