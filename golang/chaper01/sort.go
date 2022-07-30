package main

import (
	"fmt"
	"sort"
)

func main() {
	nums := []int{4, 5, 7, 8, 6}
	sort.Ints(nums)
	fmt.Println(nums)

	names := []string{"test", "kk", "123", "sss"}
	sort.Strings(names)
	fmt.Println(names)

	height := []float64{1.1, 2.2, 4.5, 4.3}
	sort.Float64s(height)
	fmt.Println(height)
}
