package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	// 一个操作3s未执行完成任务就超时
	result := make(chan int)
	//timeout := make(chan int)
	rand.Seed(time.Now().Unix())
	go func() {
		var y = rand.Int() % 10
		var x = time.Duration(y) * time.Second
		time.Sleep(x)
		fmt.Println(y)
		result <- 0
	}()
	/*go func() {
		time.Sleep(5 * time.Second)
		timeout <- 0
	}()*/
	select {
	case <-result:
		fmt.Println("执行完成！")
	case <-time.After(3 * time.Second):
		fmt.Println("执行超时")

	}
}
