#include <sys/sem.h> 

int CSinit(int value) {
  // int semget(key_t key, int nsems, int semflg): uj szemafor(halmaz) letrehozasa
  //    nsems: a szemaforok szama (nem az erteke!)
  int semid = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);

  // int semctl(int semid, int semnum, int cmd, ...): a szemafor inicializalasat illetve allapotlekerdezest vegez
  //    semid: szemaforhalmaz azonositoja
  //    semnum: a szemafor indexe a halmazban
  //    cmd: az elvegzendo muvelet pl. SETVAL: a szemafor ertekenek megadasa, GETVAL: az ertek lekerdezese, IPC_STAT, IPC_RMID
  //    ...: opcionalis parameter, egy union tipus az elozo utasitas parameterevel vagy a lekerdezes eredmeny-strukturajaval
  semctl(semid, 0, SETVAL, value); 
  return semid;
}

static void CSoper(int semid, int op) {
  struct sembuf sb;

  sb.sem_num = 0; // a szemafor indexe a halmazon belul
  sb.sem_flg = 0; // 0 eseten a muvelet blokkol, csak akkor folytatodik a futas, ha a szemafor muvelet befejezodott
  sb.sem_op = op; // pozitiv szam: noveli a szemafor erteket, negativ szam: csokkenti a szemafor erteket

  //  int semop(int semid, struct sembuf *sops, size_t nsops): a szemaforon vegrehajthato muveletet vegzi el
  //    sops: struktura vagy tomb, amely tartalmazza a vegrehajtando muveleteket
  //    nsops: a strukturak szama
  semop(semid, & sb, 1);
}

void CSwait(int semid) {
  CSoper(semid, -1);
}

void CSsignal(int semid) {
  CSoper(semid, 1);
}