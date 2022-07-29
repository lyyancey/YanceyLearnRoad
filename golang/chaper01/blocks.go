package main

import "fmt"

func main() {
	// 作用域：定义标识符可以使用的范围
	// 在GO中用{}来定义作用域的范围

	outer := 1
	{
		inner := 2
		outer := 3
		fmt.Println(outer)
		fmt.Println(inner)
	}
	fmt.Println(outer)

}
