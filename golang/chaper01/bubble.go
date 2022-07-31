package main

import "fmt"

func main() {
	heights := []int{1, 2, 5, 6, 4, 7, 8, 3, 2}
	BubbleSortAdvance(&heights, 0, len(heights))
	fmt.Println(heights)
}

func bubbleSort(eles *[]int, lo, hi int) {
	for !bubble(eles, lo, hi) {
		hi--
	}
}
func bubble(eles *[]int, lo, hi int) bool {
	var sorted bool = true
	lo++
	for lo < hi {
		if (*eles)[lo] < (*eles)[lo-1] {
			sorted = false
			(*eles)[lo], (*eles)[lo-1] = (*eles)[lo-1], (*eles)[lo]
		}
		lo++
	}
	return sorted
}

func BubbleSortAdvance(eles *[]int, lo, hi int) {
	for lo < hi {
		hi = bubbleAdvance(eles, lo, hi)
	}
}
func bubbleAdvance(eles *[]int, lo, hi int) int {
	last := lo
	lo++
	for lo < hi {
		if (*eles)[lo-1] > (*eles)[lo] {
			(*eles)[lo-1], (*eles)[lo] = (*eles)[lo], (*eles)[lo-1]
			last = lo
		}
		lo++
	}
	return last
}
