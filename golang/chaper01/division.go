package main

import (
	"errors"
	"fmt"
)

// 返回值定义错误类型
// 创建错误类型的值，errors.New(), fmt.Errorf()
// 无错误 nil
func division(a, b int) (int, error) {
	if b == 0 {
		return -1, errors.New("division buy zero")
	}
	return a / b, nil
}
func main() {

	if a, err := division(3, 0); err == nil {
		fmt.Println(a)
	} else {
		fmt.Println(err)
		fmt.Println(a)
	}
	e := fmt.Errorf("Error: %s", "devision by error")
	fmt.Printf("%T, %v", e, e)

}
