package main

import (
	"encoding/gob"
	"os"
	"time"
)

type User struct {
	ID       int
	Name     string
	Birthday time.Time
	Addr     string
	Tel      string
}

func main() {
	users := map[int]User{
		1: User{1, "张三", time.Now(), "山东", "1234567898"},
		2: User{2, "李四", time.Now(), "上海", "134567898"},
		3: User{3, "王五", time.Now(), "安徽", "34567898"},
	}
	file, err := os.Create("user.gob")
	if err == nil {
		defer file.Close()
		encoder := gob.NewEncoder(file)
		encoder.Encode(users)
	}
}
