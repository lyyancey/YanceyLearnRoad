package main

import (
	"fmt"
	"sort"
)

func main() {
	subs := sort.StringSlice{"1011", "1100", "1001"}
	sort.Reverse(subs)
	fmt.Println(subs)
}
