package main

import (
	"fmt"
	"strconv"
)

func main() {
	// string --> other
	// =>bool
	v, err := strconv.ParseBool("true")
	fmt.Println(v, err)
	// =>
	if v, err := strconv.Atoi("123"); err == nil {
		fmt.Println(v)
	} else {
		fmt.Println(err)
	}

	if v, err := strconv.ParseInt("64", 16, 64); err == nil {
		fmt.Println(v)
	} else {
		fmt.Println(err)
	}
	if v, err := strconv.ParseFloat("123.111", 64); err == nil {
		fmt.Println(v)
	} else {
		fmt.Println(err)
	}
	ss := fmt.Sprintf("%d", 12)
	fmt.Printf(ss)
	sf := fmt.Sprintf("%.3f", 12.01)
	fmt.Println(sf)

	fmt.Println(strconv.FormatBool(false))
	fmt.Println(strconv.FormatInt(12, 16))
	fmt.Println(strconv.FormatFloat(12.22, 'E', -1, 64))
}
