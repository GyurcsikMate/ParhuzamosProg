package main

import (
	"bufio"
	"fmt"
	"os"
	"sync"
	"unicode"
)

func main() {
	var wg sync.WaitGroup
	wg.Add(2)

	stats := make([]int, 3)

	ch := make(chan rune)

	// Goroutine A
	go func() {
		defer wg.Done()
		reader := bufio.NewReader(os.Stdin)
		for {
			r, _, err := reader.ReadRune()
			if err != nil {
				close(ch)
				return
			}
			ch <- r
		}
	}()

	// Goroutine B
	go func() {
		defer wg.Done()
		for r := range ch {
			if unicode.IsLower(r) {
				stats[0]++
			} else if unicode.IsUpper(r) {
				stats[1]++
			} else if unicode.IsDigit(r) {
				stats[2]++
			}
		}
	}()

	// Wait for completion
	wg.Wait()

	// Display statistics
	fmt.Println("Statistics:")
	fmt.Printf("Lowercase characters: %d\n", stats[0])
	fmt.Printf("Uppercase characters: %d\n", stats[1])
	fmt.Printf("Digits: %d\n", stats[2])
}
