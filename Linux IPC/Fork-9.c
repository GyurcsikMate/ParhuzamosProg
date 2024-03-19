#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <unistd.h>
#include "semaphore.h"

void work(const char * msg) {
  int i;

  for (i = 0; i < 10; ++i) {
    printf("%s %2d\n", msg, i);
    sleep(1);
  }
}

int main() {
  int shmid = CSinit(1);

  if (fork()) {
    CSwait(shmid);
    work("Parent");
    CSsignal(shmid);
  } else {
    CSwait(shmid);
    work("Child");
    CSsignal(shmid);
  }
  
}