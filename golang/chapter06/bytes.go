package main

import (
	"bytes"
	"fmt"
)

func main() {
	buffer := bytes.NewBufferString("abcderfy")
	buffer.Write([]byte("1234"))
	buffer.Write([]byte("!*&^"))
	fmt.Println(buffer.String())

	bytes := make([]byte, 2)
	buffer.Read(bytes)
	fmt.Println(string(bytes))
	line, _ := buffer.ReadString('*')
	fmt.Println(line)
}
