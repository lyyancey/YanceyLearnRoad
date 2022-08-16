package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {
	go func() {
		time.Sleep(3 * time.Second)
		runtime.Gosched()
	}()
	fmt.Println(runtime.GOROOT())
	fmt.Println(runtime.NumCPU())
	fmt.Println(runtime.GOMAXPROCS(1))
	fmt.Println(runtime.NumGoroutine())
}
