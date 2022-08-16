package main

import (
	"fmt"
	"time"
)

func main() {
	var channel chan int = make(chan int, 5)
	var rchannel <-chan int = channel
	var wchannel chan<- int = channel

	go func(channel <-chan int) {
		fmt.Println(<-channel)
	}(rchannel)

	go func(channel chan<- int) {
		wchannel <- 1
	}(wchannel)
	time.Sleep(time.Second * 3)
}
