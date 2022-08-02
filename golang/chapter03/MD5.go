package main

import (
	"crypto/md5"
	"encoding/hex"
	"fmt"
)

func main() {
	// fmt.Printf("%x", md5.Sum([]byte("i'am yancey")))
	bytes := md5.Sum([]byte("i'am yancey"))
	ss := fmt.Sprintf("%x", bytes)
	fmt.Println(ss)

	fmt.Println(hex.EncodeToString(bytes[:]))

	m := md5.New()
	m.Write([]byte("i'am "))
	m.Write([]byte("yancey"))
	fmt.Printf("%x", m.Sum(nil))

}
