package main

import "fmt"

func main() {
	// "" => 可解释的字符串
	// `` => 原生字符串， 一般用来写正则表达式
	//特殊字符串 \r \n \t \f \b \v
	var name string = "yancey\n"
	var desc string = `我来自中国\n`
	// \可以作为转义字符
	fmt.Println(name)
	fmt.Println(desc)

	// 操作
	// 字符串拼接 +
	fmt.Println("我叫" + "刘勇")
	// 关系运算
	fmt.Println("ab" == "bb")
	fmt.Println("ab" != "bb")
	fmt.Println("ab" < "bb")
	fmt.Println("ab" > "bb")
	fmt.Println("ab" >= "bb")
	fmt.Println("ab" <= "bb")

	// 赋值运算
	s := "我叫"
	s += "yancey"
	fmt.Println(s)

	// 字符串定义内容必须智能为ascii
	// 索引 0 - n-1
	ss := "abcdefg"
	fmt.Printf("%s", ss)
	fmt.Printf("%T, %c\n", ss[0], ss[0])
	// 切片 [1, 3)
	fmt.Println(ss[1:3])
	fmt.Println(len(ss))
	// 中文
	zhs := "我爱中国"
	fmt.Printf("%T, %c\n", zhs[0], zhs[0])
	fmt.Println(len(zhs))
}
