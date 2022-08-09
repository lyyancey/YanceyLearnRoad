package main

import "fmt"

type Dog01 struct {
	name string
}

func (dog *Dog01) Call() {
	fmt.Printf("%s : 汪汪\n", dog.name)
}
func (dog *Dog01) SetName(name string) {
	dog.name = name
}

func main() {
	dog := Dog01{"豆豆"}
	m1 := dog.Call
	fmt.Printf("%T\n", m1)
	m2 := dog.SetName
	m2("hhhh")
	dog.Call()
	m1()
}
