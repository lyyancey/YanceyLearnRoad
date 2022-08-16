package main

import (
	"fmt"
	"sync"
)

func main() {
	var users sync.Map
	users.Store(10, "kk")
	users.Store(20, "Yancey")
	if value, ok := users.Load(30); ok {
		fmt.Println(value.(string), ok)
	}

	if value, ok := users.Load(20); ok {
		fmt.Println(value.(string), ok)
	}
	if value, ok := users.Load(10); ok {
		fmt.Println(value, ok)
	}
}
