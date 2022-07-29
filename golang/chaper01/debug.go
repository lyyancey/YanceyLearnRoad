package main

import "fmt"

func main() {
	var age = 30
	age += 2
	fmt.Println("明年：", age)
	age = age + 1
	fmt.Println("后年：", age)
	age = age + 1

	fmt.Print("打印第一行")
	fmt.Print("打印第二行\n")

	fmt.Printf("%T, %s, %d", "KK", "KK", 1)

}
