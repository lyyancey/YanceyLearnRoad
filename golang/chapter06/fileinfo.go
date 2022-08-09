package main

import (
	"fmt"
	"os"
)

func main() {
	// 判断文件是否存在
	file, err := os.Open("xxxx")
	if err != nil {
		if os.IsNotExist(err) {
			fmt.Println("文件不存在")
		}
	} else {
		file.Close()
	}

	path := "chapter06"

	fileInfo, err := os.Stat(path)
	if err != nil {
		if os.IsNotExist(err) {
			fmt.Println("文件不存在")
		}
	} else {
		fmt.Println(fileInfo.Name())
		fmt.Println(fileInfo.IsDir())
		fmt.Println(fileInfo.ModTime())
		fmt.Println(fileInfo.Size())

		if fileInfo.IsDir() {
			dirfile, err := os.Open(path)
			if err == nil {
				defer dirfile.Close()
				childrens, _ := dirfile.Readdir(-1)
				for _, children := range childrens {
					fmt.Println(children.Name(), children.Size(), children.IsDir(), children.Mode())
				}
			}
		}
	}
}
