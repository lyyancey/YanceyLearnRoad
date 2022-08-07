package main

import (
	"fmt"
	"utils"
)

func main() {
	var u = utils.User{
		ID:  111,
		Add: utils.Address{"shandong", 123},
	}
	fmt.Println(u)
}
