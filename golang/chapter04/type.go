package main

import "fmt"

type Counter int

//type User map[string]string

type Callback func(...string)

func main() {
	var count Counter
	count++
	fmt.Println(count)

	var me User = make(User)
	me["name"] = "Yancey"
	me["addr"] = "shandong"
	fmt.Printf("%T, %v", me, me)
	var list Callback = func(args ...string) {
		for i, v := range args {
			fmt.Println(i, v)
		}
	}
	list("A", "B", "C", "D")

	var counter2 int = 10
	fmt.Println(Counter(counter2) > count)
}
