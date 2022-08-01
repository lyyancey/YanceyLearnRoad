package main

import "fmt"

func main() {
	fmt.Println(addN(100))
	fmt.Println(N(1))

	var f func(int) int = addN

	fmt.Printf("%T", f)
	fmt.Println(f(12))
}

func addN(n int) int {
	if n == 1 {
		return n
	}
	return n + addN(n-1)
}
func N(n int) int {
	if n == 0 {
		return 1
	}
	return n * N(n-1)
}
