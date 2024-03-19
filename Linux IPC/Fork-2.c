#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void child(int pid) {
  printf("Child\n");
}

void parent(int pid) {
  printf("Parent\n");
}

int main() {
  pid_t PID = fork();

  if (PID == 0) {
    child(PID);
  } else if (PID > 0) {
    parent(PID);
  } else {
    exit(EXIT_FAILURE);
  }

  if (PID == 0) {
    _exit(EXIT_SUCCESS);
  } else {
    exit(EXIT_SUCCESS);
  }
}