# Szerver kód

import numpy as np
import pvm3

def main():
    # Bemeneti adatok, a double értékeket tartalmazó tömb
    array = np.array([1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0])
    
    # Felosztás a résztömbök kiszámításához
    num_chunks = 4
    chunk_size = len(array) // num_chunks
    chunks = [array[i:i+chunk_size] for i in range(0, len(array), chunk_size)]
    
    # Kliensek indítása és résztömbök elküldése
    for i, chunk in enumerate(chunks):
        pvm3.send(i+1, 0, chunk)
    
    # Üzenetszórás a hatványozandó szám elküldéséhez
    base = 2.0
    pvm3.bcast(pvm3.PvmData(base))
    
    # Kliensektől módosított résztömbök fogadása
    modified_chunks = [pvm3.recv(i+1) for i in range(num_chunks)]
    
    # Eredeti és módosított tömb kiírása
    print("Eredeti tömb:")
    print(array)
    print("Módosított tömb:")
    modified_array = np.concatenate(modified_chunks)
    print(modified_array)

if __name__ == "__main__":
    main()
