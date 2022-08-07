package main

import "fmt"

type User struct {
	id   int
	name string
}

func (user User) GetID() int {
	return user.id
}
func (user User) GetName() string {
	return user.name
}
func (user *User) SetName(name string) {
	user.name = name
}
func (user *User) SetID(ID int) {
	user.id = ID
}

type Employee struct {
	User
	Salary float64
}

func main() {
	var me Employee = Employee{
		User:   User{1, "KK"},
		Salary: 1234.9,
	}
	fmt.Println(me.GetID())
	fmt.Println(me.GetName())
	me.SetID(123)
	me.SetName("Yancey")
	fmt.Println(me.GetName())
	fmt.Println(me.GetID())
}
