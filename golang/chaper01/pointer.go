package main

import "fmt"

func main() {
	A := 2
	B := A
	B = 4
	fmt.Println(A)
	fmt.Println(B)
	fmt.Println(A)

	// 指针
	var C *int = &A
	*C = 5
	fmt.Printf("%T, %d\n", C, *C)
	fmt.Printf("%T, %p\n", C, C)
	fmt.Println(A)

}
