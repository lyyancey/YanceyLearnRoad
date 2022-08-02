package main

import (
	"fmt"
	"time"
)

func main() {
	now := time.Now()
	fmt.Printf("%T\n", now)
	fmt.Printf("%v\n", now)

	fmt.Println(now.Format("2006/01/02 15:04:05"))
	fmt.Println(now.Format("2006-01-02 15:04:05"))
	fmt.Println(now.Format("2006-01-02"))
	fmt.Println(now.Format("15；04：05"))

	fmt.Println(now.Unix())
	fmt.Println(now.UnixNano())

	t, err := time.Parse("2006/01/02 15:04:05", "2006/01/02 03:04:05")
	fmt.Println(t, err)

	tt := time.Unix(0, 0)
	fmt.Println(tt)
	d := now.Sub(tt)
	fmt.Printf("%T, %v", d, d)
}
