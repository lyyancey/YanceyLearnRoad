package main

import "log"

func main() {
	log.SetPrefix("Yancey   ")
	// log.SetFlags(log.Flags() | log.Lshortfile)
	log.SetFlags(log.Flags() | log.Llongfile)
	log.Println("打印日志")
	log.Fatalln("Fatal log")
	log.Panicln("Panic log")

}
