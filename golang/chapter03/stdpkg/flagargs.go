package main

import (
	"flag"
	"fmt"
)

func main() {
	var host string
	var port int
	var verbor bool
	var help bool

	// 绑定命令行参数与变量的关系
	flag.IntVar(&port, "P", 22, "ssh port")
	flag.StringVar(&host, "H", "127.0.0.1", "ssh host")
	flag.BoolVar(&verbor, "V", false, "detail log")
	flag.BoolVar(&help, "h", false, "help")
	flag.Usage = func() {
		flag.PrintDefaults()
		fmt.Println(host, port, verbor)
	}
	// 解析命令
	flag.Parse()

	if help {
		flag.Usage()
	} else {
		fmt.Println(host, port, verbor)
		fmt.Println(flag.Args())
	}
}
