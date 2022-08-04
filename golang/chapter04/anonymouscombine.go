package main

import (
	"fmt"
)

type Address01 struct {
	Region string
	Street string
	No     string
}

type User01 struct {
	ID   int
	Name string
	Addr Address01
}

type Employee struct {
	User01
	Salary float64
}

func main() {
	var me Employee
	fmt.Printf("%T, %#v\n", me, me)

	me02 := Employee{
		User01: User01{
			ID:   01,
			Name: "Yancey",
			Addr: Address01{
				Region: "shandong",
				Street: "dezhou",
				No:     "002",
			},
		},
		Salary: 12.03,
	}
	fmt.Println(me02.User01.ID)
	fmt.Println(me02.Name)
}
