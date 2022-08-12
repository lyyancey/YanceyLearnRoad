package main

import (
	"fmt"
	"reflect"
)

func main() {
	var i int = 1
	fmt.Printf("%T\n", i)
	var tpy reflect.Type = reflect.TypeOf(i)
	fmt.Println(tpy.Name())
	fmt.Println(tpy.Kind() == reflect.Int)
}
