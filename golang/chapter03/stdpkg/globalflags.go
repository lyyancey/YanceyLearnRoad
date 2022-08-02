package main

import (
	"flag"
	"fmt"
)

func main() {
	// 绑定命令行参数与变量的关系
	port := flag.Int("P", 22, "ssh port")
	host := flag.String("H", "127.0.0.1", "ssh host")
	vober := flag.Bool("V", false, "detail log")
	help := flag.Bool("h", false, "help")
	// 解析命令
	flag.Parse()
	fmt.Printf("%T, %T, %T, %T", port, host, vober, help)
}
