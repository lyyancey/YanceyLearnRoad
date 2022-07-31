package main

import "fmt"

func main() {
	addN(1, 2, 3, 4, 5)
}
func addN(args ...int) {
	fmt.Printf("%T", args)
	fmt.Println(args)
	cacl("+", args...)
}
func add(a, b int, args ...int) int {
	tatal := a + b
	cacl("+", args...)
	for _, v := range args {
		tatal += v
	}
	return tatal
}
func cacl(op string, args ...int) {
	switch op {
	case "+":
		for _, v := range args {
			fmt.Println(v)
		}
	}
}
