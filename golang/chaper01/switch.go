package main

import "fmt"

func main() {
	var yes string
	fmt.Println("有卖西瓜的吗？")
	fmt.Scan(&yes)

	fmt.Println("老婆的想法：")
	fmt.Println("买十个包子")
	switch yes {
	case "y", "Y":
		fmt.Println("买一个西瓜")
	default:
		fmt.Println("不买西瓜了")
	}

	fmt.Println("老公的想法：")
	switch yes {
	case "Y":
		fmt.Println("买一个包子")
	case "y":
		fmt.Println("买一个包子")
	case "N":
		fmt.Println("买十个包子")
	case "n":
		fmt.Println("买十个包子")
	}

	var score int
	fmt.Println("请输入您的分数:")
	fmt.Scan(&score)
	switch {
	case score >= 80:
		fmt.Println("A")
	case score >= 70:
		fmt.Println("B")
	case score >= 60:
		fmt.Println("C")
	default:
		fmt.Println("D")
	}
}
