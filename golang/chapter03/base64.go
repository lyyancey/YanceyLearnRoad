package main

import (
	"encoding/base64"
	"fmt"
)

func main() {
	x := base64.StdEncoding.EncodeToString([]byte("I'm yancey"))
	fmt.Println(x)
	bytes, err := base64.StdEncoding.DecodeString(x)
	fmt.Println(err)
	fmt.Println(string(bytes))
}
