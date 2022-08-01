package main

import "fmt"

func test() (err error) {
	defer func() {
		if e := recover(); e != nil {
			err = fmt.Errorf("%v", e)
		}
	}()
	panic("error")
}
func main() {
	/* 	defer func() {
	   		err := recover()
	   		fmt.Printf("%T, %v", err, err)
	   	}()

	   	fmt.Println("main start")
	   	panic("error")
	   	fmt.Println("over") */

	err := test()
	fmt.Println(err)
}
