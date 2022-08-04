package main

import (
	"fmt"
	"time"
)

type User struct {
	ID       int
	name     string
	birthday time.Time
	Addr     string
	Tel      string
	Remark   string
}

func main() {
	var me User
	fmt.Printf("%T,\n", me)
	fmt.Printf("%#v\n", me)
	fmt.Println(me)
	fmt.Println()

	var me2 User = User{1, "yancey", time.Now(), "shandong", "15652223453", "ssss"}
	fmt.Println(me2)
	me2.ID = 45
	fmt.Println(me2)

	var me3 User = User{}
	fmt.Println(me3)

	var me4 User = User{ID: 12, name: "Yancey", birthday: time.Now(), Addr: "dezhou", Tel: "1234565"}
	fmt.Println(me4)
	fmt.Println()

	var me5 User = User{1,
		"yancey",
		time.Now(),
		"shandong",
		"15652223453",
		"ssss",
	}
	fmt.Println(me5)

	var pointer *User = &me
	fmt.Printf("%T\n", pointer)
	fmt.Printf("%#v\n", pointer)

	var pointer01 *User = new(User)
	fmt.Println(pointer01)
	(*pointer01).Tel = "12345678901"
	pointer01.ID = 123
	fmt.Println(*pointer01)

	var pointer02 *int = new(int)
	fmt.Println(pointer02)

}
