package main

import (
	"fmt"
	"io/ioutil"
	"os"
)

func main() {
	path := "user.txt"
	file, err := os.Open(path)
	if err != nil {
		fmt.Println(err)
	} else {
		bytes, err := ioutil.ReadAll(file)
		fmt.Println(string(bytes), err)
		file.Close()
	}
}
