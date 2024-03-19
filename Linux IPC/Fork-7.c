#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string.h>
#include <sys/wait.h>

int main() {
  // int shmget(key_t key, size_t size, int shmflg): visszaad egy azonositot egy memoriaszegmenshez
  //    key: kulcs az uj memoriaszegmenshez
  //    size: memoriaszegmens merete (getconf PAGE_SIZE)
  //    shmflg: az adott user jogosult-e hozzaferni a memoriaszegmenshez
  int shmid = shmget(IPC_PRIVATE, 4096, 0666 | IPC_CREAT);

  // void *shmat(int shmid, const void *shmaddr, int shmflg): a hivo folyamat nevterehez adja a memoriaszegmenst
  //    shmid: memoriaszegmens azonositoja
  //    shmaddr: a memoriacim, ahova a szegmenst hozza szeretnenk csatolni; NULL-lal inicializalva a rendszer valaszt
  //    shmflg: korlatozasokat lehet megadni, pl. SHM_RDONLY: read only, SHM_EXEC: a szegmens tartalma vegrehajthato
  char * text = (char * ) shmat(shmid, NULL, 0);

  strcpy(text, "Initial");
  printf("%s\n", text);
  pid_t PID = fork();

  if (PID == 0) {
    strcpy(text, "ModifiedByChild");
  } else if (PID > 0) {
    wait(NULL);
    printf("%s\n", text);

    // ipcs 

    // int shmctl(int shmid, int cmd, struct shmid_ds *buf): a megosztott memoriaszegmens kezelese:
    //    shmid: a szegmens azonositoja
    //    cmd: az elvegzendo muvelet, pl. IPC_RMID: torles, IPC_STAT: szegmens adatainak lekerdezese (pl. mikor volt utoljara hasznalva)
    //    buf: ha IPC_STAT az elozo, akkor ebbe a strukturaba kerul eltarolasra az eredmeny
    //shmctl(shmid,  IPC_RMID, 0);
  } else {
    exit(EXIT_FAILURE);
  }
}

