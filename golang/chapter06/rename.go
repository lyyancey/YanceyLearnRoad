package main

import "os"

func main() {
	os.Rename("chapter06/user.log", "chapter06/user.v2.log")
	os.Remove("chapter06/user.v2.log")
}
