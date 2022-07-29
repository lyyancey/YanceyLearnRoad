package main

import "fmt"

var version string = "1.0"

func main() {
	// 定义变量名为me的string类型
	var me string
	me = "iiii"

	fmt.Printf(me)
	fmt.Printf(version)

	var name, user string = "kk", "woniu"
	fmt.Println(name, user)

	var (
		age    int     = 31
		height float64 = 1.68
	)
	fmt.Println(age, height)

	// 省略类型必须赋初值，类型会根据初值进行推导
	var (
		s = "kk"
		a = 31
	)
	fmt.Println(s, a)
	var ss, aa = "kk", 23
	fmt.Println(ss, aa)

	// 简短声明，只能在函数内部使用
	isBoy := false
	fmt.Println(isBoy)

	//多个赋值
	ss, aa, isBoy = "silence", 22, true
	fmt.Println(ss, aa, isBoy)

	// 交换值
	fmt.Println(s, ss)
	s, ss = ss, s
	fmt.Println(s, ss)

}
