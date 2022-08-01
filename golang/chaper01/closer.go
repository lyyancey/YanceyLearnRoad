package main

import "fmt"

func main() {
	name := "KK"
	print := func() {
		fmt.Println(name)
	}
	print()

	addBase := func(base int) func(int) int {
		return func(i int) int {
			return i + base
		}
	}
	add8 := addBase(8)
	fmt.Println(add8(16))
	fmt.Println(addBase(3)(6))

}
