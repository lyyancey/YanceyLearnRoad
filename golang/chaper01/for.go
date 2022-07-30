package main

import "fmt"

func main() {
	result := 0
	for i := 1; i <= 100; i++ {
		result += i
	}
	fmt.Println(result)

	result = 0
	i := 0
	for i <= 100 {
		result += i
		i++
	}
	fmt.Println(result)

	i = 0
	for {
		fmt.Println(i)
		if i >= 100 {
			break
		}
		i++
	}

	desc := "我爱中国"
	for i, ch := range desc {
		fmt.Println(i)
		fmt.Println(ch)
	}
}
