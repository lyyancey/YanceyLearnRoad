package main

import (
	"fmt"
	"users/user"
)

func main() {
	ss := user.GetUserName()
	fmt.Println(ss)
}
