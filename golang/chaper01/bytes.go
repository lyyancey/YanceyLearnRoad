package main

import (
	"bytes"
	"fmt"
)

func main() {
	var bytes01 []byte = []byte{'a', 'b', 'c'}
	fmt.Printf("%T, %#v\n", bytes01, bytes01)

	s := string(bytes01)
	fmt.Printf("%T, %#v\n", s, s)

	fmt.Println(bytes.Compare([]byte("abc"), []byte("abd")))
	fmt.Println(bytes.Index([]byte("abcdefabc"), []byte("def")))
	fmt.Println(bytes.Contains([]byte("abcdefabc"), []byte("defd")))
}
