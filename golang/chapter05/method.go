package main

import "fmt"

type Dog struct {
	name string
}

func (dog Dog) Call() {
	fmt.Printf("%s: 汪汪\n", dog.name)
}
func (dog *Dog) SetName(newName string) {
	(*dog).name = newName
}
func main() {
	dog := Dog{
		name: "豆豆",
	}
	dog.Call()
	(&dog).SetName("小狗")
	dog.Call()

	pdog := &Dog{"yang"}
	pdog.Call()
	pdog.SetName("Hea")
	pdog.Call()
}
