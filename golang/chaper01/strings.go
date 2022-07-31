package main

import (
	"fmt"
	"strings"
)

func main() {
	fmt.Println(strings.Compare("abc", "bcd"))
	fmt.Println(strings.Contains("abd", "ad"))
	fmt.Println(strings.ContainsAny("abd", "ac"))
	fmt.Println(strings.Count("abcdbcad", "ab"))
	fmt.Printf("%q", strings.Fields("abc def\neee\raaa\fxxxx\vadddd"))

	fmt.Println(strings.HasPrefix("abcd", "ab"))
	fmt.Println(strings.HasSuffix("abcdef", "dabc"))
	fmt.Println(strings.Index("defabcdef", "dabc"))
	fmt.Println(strings.Index("defabcdef", "def"))
	fmt.Println(strings.LastIndex("defabcdef", "def"))

	fmt.Println(strings.Split("abcdef;abc;abc", ";"))
	fmt.Println(strings.Join([]string{"abc", "def", "eee"}, ":"))
	fmt.Println(strings.Repeat("abc", 3))

	fmt.Println(strings.Replace("abcabcabcab", "ab", "xxx", 1))
	fmt.Println(strings.Replace("abcabcabcab", "ab", "xxx", -1))
	fmt.Println(strings.ReplaceAll("abcabcabcab", "ab", "xxx"))

	fmt.Println(strings.ToLower("abcABC"))
	fmt.Println(strings.ToUpper("abcABC"))

	fmt.Println(strings.Title("abcD"))
	fmt.Println(strings.Trim("   hhhh   ", " "))
	fmt.Println(strings.TrimSpace("    ssdsdfs  "))
}
