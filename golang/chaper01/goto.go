package main

import "fmt"

func main() {
	fmt.Println("老婆的想法: ")
	var yes string
	fmt.Println("买十个包子。")
	fmt.Println("有卖西瓜的吗？(Y/N)")
	fmt.Scan(&yes)
	if yes == "N" || yes == "n" {
		goto END
	}
	fmt.Println("买一个西瓜。")
END:
	fmt.Println("结束")

	sum := 0
	i := 1
START:
	if i > 100 {
		goto FOREND
	}
	sum += i
	i++
	goto START
FOREND:
	fmt.Println(sum)

BREAKEND:
	for i := 0; i < 5; i++ {
		for j := 0; j < 5; j++ {
			if i*j == 9 {
				break BREAKEND
			}
			fmt.Println(i, j, i*j)
		}
	}

}
