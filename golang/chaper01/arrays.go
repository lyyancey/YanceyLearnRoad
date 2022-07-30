package main

import "fmt"

func main() {
	var nums [10]int
	var t2 [5]bool
	var t3 [5]string
	fmt.Printf("%T", nums)
	fmt.Println(t2)
	fmt.Printf("%q\n", t3)

	// 字面量
	nums = [10]int{1, 2, 3}
	fmt.Println(nums)

	nums = [10]int{5: 10, 9: 80}
	fmt.Println(nums)

	nums = [...]int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	fmt.Println(nums)

	nums01 := [10]int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	fmt.Printf("%T, %#v\n", nums01, nums01)

	nums02 := [3]int{1, 3, 4}
	nums03 := [3]int{1, 2, 4}
	fmt.Println(nums02 == nums03)

	/* 	nums04 := [4]int{1, 2, 4, 6}
	   	nums05 := [3]int{1, 2, 4}
	   	fmt.Println(nums04 == nums05) */

	// 获取数组的长度
	fmt.Println(len(nums03))
	for index, value := range nums {
		fmt.Println("index:", index, " value :", value)
	}

	//切片
	fmt.Printf("%T", nums[0:8:9])
	fmt.Printf("%v", nums[0:8:9])

	var marrays [3][2]int
	for _, v := range marrays {
		for _, vv := range v {
			fmt.Println(vv)
		}
	}
	fmt.Println(marrays)

}
