package main

import (
	"fmt"
	"time"
)

func main() {
	fmt.Println(time.Now())
	channel := time.After(3 * time.Second)
	//fmt.Println(<-channel)
	fmt.Println(<-channel)
	ticker := time.Tick(3 * time.Second)
	fmt.Println(<-ticker)
	fmt.Println(<-ticker)
	fmt.Println(<-ticker)
	fmt.Println(<-ticker)
	for now := range ticker {
		fmt.Print(now)
	}
}
