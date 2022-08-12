package main

import (
	"fmt"
	"runtime"
	"sync"
)

func main() {
	n := 3
	group := &sync.WaitGroup{}
	for i := 0; i < n; i++ {
		group.Add(1)
		go func(id int) {
			for ch := 'A'; ch <= 'Z'; ch++ {
				fmt.Printf("%d, %c\n", id, ch)
				runtime.Gosched()
			}
			group.Done()
		}(i)
	}
	group.Wait()
}
