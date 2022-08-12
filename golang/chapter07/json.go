package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	/*
		json.Marshal()
		json.Unmarshal()
	*/
	name := []string{"张三", "李四", "王五"}
	bytes, err := json.Marshal(name)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(string((bytes)))
	}

	users := []map[string]string{{"name": "张三", "add": "上海"}, {"name": "李四", "add": "山东"}, {"name": "王五", "add": "浙江"}}
	userjson, err0 := json.Marshal(users)
	if err0 == nil {
		fmt.Println(string(userjson))
	}
	var names02 []string
	err = json.Unmarshal(bytes, &names02)
	if err == nil {
		fmt.Println(names02)
	}
	var usersSer []map[string]string
	err = json.Unmarshal(userjson, &usersSer)
	fmt.Println(usersSer)

	userjsonInd, err1 := json.MarshalIndent(users, "", "\t")
	if err1 == nil {
		fmt.Println(string(userjsonInd))
	}
	fmt.Println(json.Valid([]byte("[]")))
}
