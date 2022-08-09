package main

import (
	"fmt"
	"io"
	"os"
)

func main() {
	path := "user.txt"
	file, err := os.Open(path)
	// fmt.Println(err)
	// fmt.Println(file)
	// fmt.Printf("%T", file)
	if err != nil {
		fmt.Println(err)
	} else {
		var bytes = make([]byte, 20)
		for {
			_, err := file.Read(bytes)
			if err == io.EOF {
				break
			} else if err != nil {
				fmt.Println(err)
			} else {
				fmt.Println(string(bytes))
			}
		}
	}
	defer file.Close()
}
