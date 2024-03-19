#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

void child() {
  printf("Child\n");
}

void parent() {
  printf("Parent\n");
}

int main() {
  pid_t PID;
  int i;

  for (i = 0; i < 4; i++) {
    PID = fork();

    if (PID == 0) {
      break;
    } else if (PID < 0) {
      exit(EXIT_FAILURE);
    }
  }

  if (PID == 0) {
    child();
    _exit(EXIT_SUCCESS);
  } else {
    parent();
    exit(EXIT_SUCCESS);
  }
}