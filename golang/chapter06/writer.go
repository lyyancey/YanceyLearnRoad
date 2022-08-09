package main

import (
	"bufio"
	"os"
)

func main() {
	file, err := os.Create("userv2.txt")
	if err == nil {
		defer file.Close()
		writer := bufio.NewWriter(file)
		writer.WriteString("abcdefgh\n")
		writer.Write([]byte("123456"))
		writer.Flush()

	}
}
