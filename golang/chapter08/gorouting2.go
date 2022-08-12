package main

import (
	"fmt"
	"runtime"
	"sync"
)

func main() {
	group := &sync.WaitGroup{}
	group.Add(2)
	go func() {
		for ch := 'A'; ch <= 'Z'; ch++ {
			fmt.Printf("%s: %c\n", "1", ch)
			runtime.Gosched()
		}
		group.Done()
	}()

	go func() {
		for ch := 'A'; ch <= 'Z'; ch++ {
			fmt.Printf("%s: %c\n", "2", ch)
			runtime.Gosched()
		}
		group.Done()
	}()
}
