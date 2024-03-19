#include <stdio.h>
#include <unistd.h>

int main() {
  fork();
  printf("Hello world!\n");
}

/*
https://man7.org/linux/man-pages/dir_all_alphabetic.html
ps -ao pid,ppid,psr,comm
*/