package main

import "fmt"

func main() {
	// 定义
	var nums []int
	fmt.Printf("%T\n", nums)
	fmt.Printf("%#v\n", nums)
	fmt.Println(nums == nil)

	// 赋值
	nums = []int{1, 2, 3}
	fmt.Printf("%#v\n", nums)
	nums = []int{1, 2, 3, 4}
	fmt.Printf("%#v\n", nums)
	// 通过数组切片赋值
	var arrays [10]int = [10]int{1, 2, 3, 4, 5, 6}
	nums = arrays[1:10]
	fmt.Println(nums)
	fmt.Printf("%#v\n", nums)
	//长度， 容量
	fmt.Printf("%#v  %d  %d\n", nums, len(nums), cap(nums))

	// make 函数
	nums = make([]int, 3)
	fmt.Printf("%#v  %d, %d\n", nums, len(nums), cap(nums))

	nums = make([]int, 3, 5)

	// 操作  (增删改查)
	fmt.Println(nums[0])
	fmt.Println(nums[1])
	fmt.Println(nums[2])
	// fmt.Println(nums[3])
	// nums[3] = 2
	nums = append(nums, 1)
	nums = append(nums, 2)
	nums = append(nums, 3)
	fmt.Printf("%#v  %d, %d\n", nums, len(nums), cap(nums))
	for index, v := range nums {
		fmt.Println(index, v)
	}
	nums = make([]int, 3, 10)

	// 切片操作
	fmt.Println(nums[1:5:10])
	n := nums[1:3:8]
	// 切片的容量为 cap(n) - start , 如果没有指定容量，则容量为 cap(nums) - start
	// 切片设置的容量不能比原来的大
	// n = nums[1:3:20]  错误
	fmt.Printf("%T, %#v, %d, %d\n", n, n, len(n), cap(n))
	n = nums[1:3]
	// 切片的容量为 cap(n) - start , 如果没有指定容量，则容量为 cap(nums) - start
	fmt.Printf("%T, %#v, %d, %d\n", n, n, len(n), cap(n))

	// 改变切片的值会影响原数组的值
	nums02 := nums[1:3]
	fmt.Println(nums, nums02)
	nums02[0] = 1
	fmt.Println(nums, nums02)

	nums02 = append(nums02, 3)
	fmt.Println(nums, nums02)

	nums = append(nums, 5)
	fmt.Println(nums, nums02)

	nums = arrays[:]
	fmt.Println(nums, arrays)
	nums[0] = 100
	fmt.Println(nums, arrays)

	//删除
	// copy
	nums04 := []int{1, 2, 3}
	nums05 := []int{10, 20, 30, 40}
	copy(nums05, nums04)
	fmt.Println(nums05)

	nums05 = []int{10, 20, 30, 40}
	copy(nums04, nums05)
	fmt.Println(nums04)

	// 删除索引为0的和索引最后一个

	nums06 := []int{1, 2, 3, 4, 5}
	fmt.Println(nums06[1:])
	fmt.Println(nums06[:len(nums06)-1])

	// 删除中间的元素
	copy(nums06[2:], nums06[3:])
	fmt.Println(nums06)
	fmt.Println(nums06[:len(nums06)-1])

	// 队列和堆栈

	points := [][]int{}
	point02 := make([][]int, 0)
	points = append(points, []int{1, 2, 3})
	points = append(points, []int{3, 4, 0})
	points = append(points, []int{3, 4, 0, 9})
	fmt.Println(points)
	fmt.Printf("%T", point02)
	// 在GO中数组是不可变的
	slice01 := []int{1, 2, 3}
	slice02 := slice01
	slice02[0] = 10
	fmt.Println(slice01, slice02)

	array01 := [3]int{1, 2, 3}
	array02 := array01
	array02[0] = 10
	fmt.Println(array01, array02)

	// 切片删除
	fmt.Println("切片的删除")

	nums07 := []int{1, 3, 5, 8}
	fmt.Println(nums07[:1])
	fmt.Println(nums07[2:])
	nums07 = append(nums07[:1], nums07[2:]...)
	fmt.Println(nums07)
}
