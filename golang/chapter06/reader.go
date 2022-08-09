package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
)

func main() {
	file, err := os.Open("user.txt")
	if err == nil {
		defer file.Close()
		reader := bufio.NewReader(file)
		// Read
		/*bytes := make([]byte, 5)
		for {
			n, err := reader.Read(bytes)
			if err != nil {
				if err == io.EOF {
					break
				}
				fmt.Println(err)
			} else {
				fmt.Println(n, string(bytes))
			}
		}*/
		// ReadLine
		/*for {
			line, isPrefix, err := reader.ReadLine()
			if err != nil {
				if err != io.EOF {
					fmt.Println(err)
				}
				break
			} else {
				fmt.Println(isPrefix, err, string(line))
			}
		}*/
		//ReadSlice
		for {
			line, err := reader.ReadSlice('\n')
			if err != nil {
				if err != io.EOF {
					fmt.Println(err)
				}
				break
			} else {
				fmt.Println(string(line))
			}
		}
	}
}
