package main

import (
	"fmt"
	"os"
)

func main() {
	file, err := os.OpenFile("user.txt", os.O_APPEND|os.O_CREATE, os.ModePerm)
	bytes := make([]byte, 100)
	n, _ := file.Read(bytes)
	// 偏移量， 相对位置 0, 1, 2
	fmt.Println(file.Seek(0, 2))
	n1, err := file.Read(bytes)
	fmt.Println(n, n1, err)
	file.Close()
}
