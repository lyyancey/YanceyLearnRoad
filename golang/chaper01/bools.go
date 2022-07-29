package main

import "fmt"

func main() {
	// 布尔类型零值为false
	var zero bool
	isBoy := true
	isGirl := false
	fmt.Println(zero, isBoy, isGirl)

	// 操作
	// 逻辑运算 & || !

	// 关系运算 == , !=
	fmt.Printf("%T, %t", isBoy, isBoy)

}
