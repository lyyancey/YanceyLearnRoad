package main

import "fmt"

func main() {
	var name string
	fmt.Println("请输入名称：")
	fmt.Scan(&name)
	fmt.Println("你输入的内容是：", name)

	var age int
	fmt.Println("请输入你的年龄:")
	fmt.Scan(&age)
	fmt.Println("你的年龄是：", age)

	var height float64
	fmt.Println("请输入你的身高：")
	fmt.Scan(&height)
	fmt.Println("你的身高是:", height)

}
