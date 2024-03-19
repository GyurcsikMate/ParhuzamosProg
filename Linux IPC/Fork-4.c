#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

int main() {
  int i;

  for (i = 0; i < 4; i++) {
    fork();
    printf("Hello world! \n");
  }

  /*
  fork();
  fork();
  fork();
  fork();
  printf("Hello world! \n");
  */

  /*
  if(fork() || fork()){
    fork();
  }
  printf("1 ");
  */
}