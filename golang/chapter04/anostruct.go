package main

import "fmt"

func main() {
	var me struct {
		ID   int
		name string
	}
	me.name = "Hello"
	me.ID = 123

	fmt.Printf("%T\n", me)
	fmt.Printf("%#v\n", me)
	fmt.Println(me.ID)

	me2 := struct {
		ID   int
		name string
	}{12, "yancey"}
	fmt.Println(me2.name)
}
