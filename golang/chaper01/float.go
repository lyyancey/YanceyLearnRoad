package main

import "fmt"

func main() {
	// float32 float64 零值为0
	// 字面量：十进制表示法 科学技术表示法
	//M E N M * 10^N

	var height float32 = 3.12
	fmt.Printf("%T, %f\n", height, height)
	// 操作 + - * /  ++ --
	// 关系运算  > >= < <= 一般不进行 == !=
	// 赋值运算 = += -= /= *=

	// 默认为float64
	fmt.Printf("%T, %f\n", 1.1, 1.1)

	// 复数 complex128, (1+2i) 实部和虚部都是用的float64
	i := 1 + 2i
	fmt.Printf("%T, %v", i, i)
}
