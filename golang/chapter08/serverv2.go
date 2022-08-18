package main

import (
	"bufio"
	"fmt"
	"net"
	"os"
	"time"
)

func main() {
	addr := "0.0.0.0:9999"
	listener, err := net.Listen("tcp", addr)
	defer listener.Close()
	if err != nil {
		fmt.Println(err)
		os.Exit(-1)
	}
	fmt.Println("Listener")
	for {
		client, err := listener.Accept()
		if err == nil {
			reader := bufio.NewReader(client)
			writer := bufio.NewWriter(client)
			input := bufio.NewScanner(os.Stdin)
			for {
				line, err := reader.ReadString('\n')
				if err != nil {
					break
				}
				fmt.Println("客户端:", line)
				fmt.Print("请输入：")
				input.Scan()
				inputString := input.Text()
				_, err = writer.WriteString(time.Now().Format(inputString + "\n"))

				writer.Flush()
				if err != nil {
					break
				}
				//fmt.Println(n, err)
			}
		}

	}

}
