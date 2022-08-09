package main

import (
	"fmt"
	"os"
	"path/filepath"
)

func main() {
	fmt.Println(filepath.Abs("."))
	fmt.Println(os.Args)
	fmt.Println(filepath.Abs(os.Args[0]))
	fmt.Println(filepath.Base("D:\\code\\YanceyLearnRoad\\golang\\user.txt"))
	fmt.Println(filepath.Base(os.Args[0]))
	fmt.Println(filepath.Dir(os.Args[0]))

	fmt.Println(filepath.Ext(os.Args[0]))

	dirptah := filepath.Dir(os.Args[0])
	iniPath := filepath.Join(dirptah, "conf", "ip.ini")
	fmt.Println(iniPath)

	fmt.Println(filepath.Glob("../*.exe"))

	filepath.Walk(".", func(path string, fileInfo os.FileInfo, err error) error {
		fmt.Println(path)
		return nil
	})
}
