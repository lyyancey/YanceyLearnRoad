package main

import (
	"fmt"
	"runtime"
)

func main() {
	channel := make(chan string, 2)
	go func() {
		for i := 0; i < 2; i++ {
			channel <- "x"
			runtime.Gosched()
		}
	}()
	fmt.Println(len(channel))
	//fmt.Println(<-channel)
	fmt.Println(len(channel))
	//time.Sleep(time.Second * 3)
	fmt.Println(len(channel))
	close(channel)
	for ch := range channel { // 需要有某个例程关闭管道，否则会发生死锁
		fmt.Println(ch)
	}
	//fmt.Println(<-channel)
	fmt.Println(len(channel))
	//close(channel) // 关闭管道之后可以读但是不能写了

}
