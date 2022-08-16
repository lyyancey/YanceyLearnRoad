package main

import (
	"fmt"
	"runtime"
)

func PrintChar(name string, channel chan int) {
	for ch := 'A'; ch <= 'Z'; ch++ {
		fmt.Printf("%s: %c\n", name, ch)
		runtime.Gosched()
	}
	channel <- 0
}
func main() {
	var channel chan int = make(chan int)
	go PrintChar("1", channel)
	go PrintChar("2", channel)
	for i := 0; i < 2; i++ {
		fmt.Printf("%d", <-channel)
	}
	// time.Sleep(time.Second * 3)
}
