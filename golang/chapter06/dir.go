package main

import "os"

func main() {
	//创建文件夹
	os.Mkdir("chapter06/test01", 0644)
	// 重命名文件夹
	os.Rename("chapter06/test01", "chapter06/test02")
	// 移除文件夹
	os.Remove("chapter06/test02")
	os.MkdirAll("chapter06/test03/hello", 0644)
	// 目录不为空删不掉
	os.Remove("chapter06/test03/hello")
	// 这样就可以删掉
	os.RemoveAll("chapter06/test03/hello")

}
