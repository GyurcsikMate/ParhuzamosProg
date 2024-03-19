#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void child() {
  sleep(3);
  printf("Child\n");
}

void parent() {
  wait(NULL);
  printf("Parent\n");
}

int main() {
  pid_t PID = fork();

  if (PID == 0) {
    child();
  } else if (PID > 0) {
    parent();
  } else {
    exit(EXIT_FAILURE);
  }

  if (PID == 0) {
    _exit(EXIT_SUCCESS);
  } else {
    exit(EXIT_SUCCESS);
  }
}