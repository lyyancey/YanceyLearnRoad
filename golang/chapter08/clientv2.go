package main

import (
	"bufio"
	"fmt"
	"net"
	"os"
)

func main() {
	addr := "127.0.0.1:9999"
	conn, err := net.Dial("tcp", addr)
	defer conn.Close()
	if err != nil {
		fmt.Println(err)
		os.Exit(-1)
	}
	reader := bufio.NewReader(conn)
	writer := bufio.NewWriter(conn)

	input := bufio.NewScanner(os.Stdin)

	for {
		fmt.Printf("请输入（Q 或 q 退出）：")
		input.Scan()
		inputSting := input.Text()
		if inputSting == "Q" || inputSting == "q" {
			break
		}
		_, err := writer.WriteString(inputSting + "\n")
		writer.Flush()
		if err != nil {
			break
		}
		//fmt.Println(n, err)
		line, err := reader.ReadString('\n')
		if err != nil {
			break
		}
		fmt.Print("服务器：", line)
	}

}
