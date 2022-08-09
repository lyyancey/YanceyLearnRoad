package main

import (
	"fmt"
	"os"
)

func main() {
	file, err := os.Create("chapter06/user2.txt")
	if err != nil {
		fmt.Println(err)
	} else {
		file.Write([]byte("abc123@!"))
		file.WriteString("xyz")
		file.Close()
	}
}
