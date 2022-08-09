package main

import (
	"fmt"
	"os"
	"strconv"
	"time"
)

func main() {
	path := "user.log"
	file, err := os.OpenFile(path, os.O_APPEND|os.O_CREATE, os.ModePerm)
	fmt.Println(file, err)
	if err == nil {
		file.WriteString(strconv.FormatInt(time.Now().Unix(), 10))
		file.WriteString("\n")
		file.Close()
	}
}
