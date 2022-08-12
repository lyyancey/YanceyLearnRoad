package main

import (
	"encoding/json"
	"fmt"
)

type Size int

const (
	Large = iota
	Medium
	Small
)

func (s Size) MarshalText() ([]byte, error) {
	switch s {
	case Large:
		return []byte("large"), nil
	case Medium:
		return []byte("medium"), nil
	case Small:
		return []byte("small"), nil
	default:
		return []byte("unknow"), nil
	}
}

func (s *Size) UnmarshalText(bytes []byte) error {
	switch string(bytes) {
	case "large":
		*s = Large
	case "medium":
		*s = Medium
	case "small":
		*s = Small
	default:
		*s = Small
	}
	return nil
}
func main() {
	var size Size = Small
	bytes, _ := json.Marshal(size)
	fmt.Println(string(bytes))
	var size02 Size
	fmt.Println(size02)
	json.Unmarshal(bytes, &size02)
	fmt.Println(size02)
}
