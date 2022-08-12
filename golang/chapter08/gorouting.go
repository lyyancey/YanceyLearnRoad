package main

import (
	"fmt"
	"runtime"
	"time"
)

func PrintChars(name string) {

	for i := 'A'; i <= 'Z'; i++ {
		fmt.Printf("%s, %c\n", name, i)
		runtime.Gosched()
	}
}
func main() {
	go PrintChars("1")
	go PrintChars("2")
	PrintChars("3")
	time.Sleep(3 * time.Second)
}
