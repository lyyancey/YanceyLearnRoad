package main

import (
	"encoding/json"
	"fmt"
)

type User struct {
	ID   int    `json:"id,string"`
	Name string `json:"name"`
	Sex  int    `json:"sex,int,omitempty"`
	Tel  string
	Addr string
}

func main() {
	user := User{1, "yancey", 0, "123456789", "山东"}
	bytes, _ := json.MarshalIndent(user, "", "\t")
	fmt.Println(string(bytes))
	var user02 User
	json.Unmarshal(bytes, &user02)
	fmt.Println(user02)
}
