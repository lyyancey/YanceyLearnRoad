package main

import "fmt"

func main() {
	var notice chan string = make(chan string)
	fmt.Printf("%T, %v\n", notice, notice)

	go func() {
		fmt.Printf("go start\n")
		notice <- "xxxxx"
		fmt.Printf("go end\n")
	}()
	fmt.Printf("start\n")
	fmt.Println(<-notice)
	fmt.Println("end")
}
