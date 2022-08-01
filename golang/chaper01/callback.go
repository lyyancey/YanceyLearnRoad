package main

import "fmt"

func main() {

	var callback func(...string) = list
	print(callback, "A", "B", "C", "D")
	var value func(...string) = values
	print(value, "A", "B", "C", "D", "E")
	print(func(args ...string) {
		for i, v := range args {
			fmt.Println(i+1, ":", v)
		}
	}, "A", "B", "C", "D", "E")

	sayHello := func(name string) {
		fmt.Println("Hello", name)
	}
	sayHello("Yancey")

	func(name string) {
		fmt.Println("Hi", name)
	}("Yancey")

}

func print(callback func(...string), args ...string) {
	fmt.Println("print 函数的输出")
	callback(args...)
}
func list(args ...string) {
	for i, v := range args {
		fmt.Println(i, v)
	}
}
func values(list ...string) {
	for _, v := range list {
		fmt.Println(v)
	}
}
