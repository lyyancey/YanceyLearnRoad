package main

import "fmt"

type Cat struct {
	name string
}

func (cat Cat) Call() {
	fmt.Printf("%s: 汪汪\n", cat.name)
}
func (cat *Cat) SetName(name string) {
	cat.name = name
}

func main() {
	m1 := Cat.Call
	m2 := (*Cat).SetName
	fmt.Printf("%T, %T\n", m1, m2)
	cat := Cat{"豆豆"}
	m1(cat)
	m2(&cat, "yancey")
	cat.Call()

}
