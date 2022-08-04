package main

import "fmt"

type Address struct {
	Region string
	Street string
	No     string
}
type UserInfo struct {
	ID   int
	Name string
	Addr Address
}

func main() {
	var user UserInfo = UserInfo{
		ID:   123,
		Name: "yancey",
		Addr: Address{
			Region: "shandong",
			Street: "dezhou",
			No:     "123",
		},
	}
	fmt.Println(user)
}
