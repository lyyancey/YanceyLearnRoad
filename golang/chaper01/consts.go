package main

import "fmt"

func main() {
	// 常量，不能修改值，且必须赋初值
	const NAME string = "Hello"
	// 可以省略类型
	const Hello = "sss"
	// 可以定义多个常量，且类型不同
	const ww, ee, ss = "qqq", "qqwww", 23
	const (
		name   = "xxx"
		age    = 23
		isbody = true
	)
	fmt.Println(NAME)
	// 如果后面的值和前面的值相同，就可以把后续的值和类型同时省略掉， 必须是值和类型同时省略掉
	const (
		C7 int = 1
		C8
		C9
		C10 float64 = 3.14
		C11
		C2
		C3 string = "kk"
		C4
	)

	fmt.Println(C7, C8, C9, C10, C11, C2, C3, C4)
	// 枚举 const + iota ioat 在每个括号内初始值设为0 每次定义新值就会自动+1
	const (
		E1 int = iota
		E2
		E3
	)
	fmt.Println(E1, E2, E3)
	const (
		B1 = (iota + 1) * 100
		B2
		B3
	)
	fmt.Println(B1, B2, B3)
}
