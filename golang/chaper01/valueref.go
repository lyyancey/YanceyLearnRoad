package main

import "fmt"

func main() {
	array := [3]string{"A", "B", "C"}
	slice := []string{"A", "B", "C"}

	arrayA := array
	sliceA := slice

	arrayA[0] = "Z"
	sliceA[0] = "A"

	fmt.Println(array, arrayA)
	fmt.Println(slice, sliceA)
	a := 3
	fmt.Printf("%v\n", &a)
	A(&a)

	//int bool float32 float64 array slice map point

	// 值类型 ： int bool float array point
	// 引用类型： slice  map
}

func A(p *int) {
	fmt.Printf("%p", &p)
}
