package main

import "fmt"

func main() {
	// 整数类型
	// int / int* / uint /uint* / uintptr / byte / rune
	// 字面量：十进制， 八进制， 十六进制0x111
	// int 范围和机器有关32位系统为4字节， 64位系统为8字节
	var age int = 21
	fmt.Printf("%T, %d\n", age, age)
	fmt.Println("八进制0777: ", 0777)
	//操作
	//算数运算 + - * / ++ --
	// ++ -- 只能在变量后面

	//关系运算 == != > >= < <=

	//位运算 & | ^ << >> &^(位清空 对应位为1时清为0)

	// 赋值运算 (= += -= *= /= %= &= |= ^= <<= >>= &^=)

	// int uint byte rune int*
	var intA int = 10
	var uintB uint = 3
	// fmt.Println(intA + uintB)
	fmt.Println(intA + int(uintB))
	fmt.Println(uint(intA) + uintB)

	// byte rune
	var a byte = 'A'
	var w rune = '中'
	fmt.Println(a, w)

	fmt.Printf("%T, %d %b, %o, %x\n", age, age, age, age, age)
	fmt.Printf("%T, %c\n", a, a)
	fmt.Printf("%T, %q %U\n", w, w, w)
	// 左对齐一共占5个单位左边不够用0填充
	// 默认为又对齐用空格填充
	fmt.Printf("%-05d\n", age)
	// 右对齐5个单位左边不够用0填充
	fmt.Printf("%05d\n", age)
}
