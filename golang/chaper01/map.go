package main

import "fmt"

func main() {
	var score map[string]int
	fmt.Printf("%T %#v\n", score, score)
	fmt.Println(score == nil)

	score = map[string]int{"刘勇": 8, "Yancey": 9, "kk": 10}
	fmt.Println(score)

	scores := make(map[string]int)
	fmt.Println(scores)

	// 增删改查
	fmt.Println(score["刘勇"])
	fmt.Println(score["hello"])
	v, ok := score["yy"]
	fmt.Println(v, ok)

	if v, ok := score["Yancey"]; ok {
		fmt.Println(v)
	}

	score["Yancey"] = 20
	fmt.Println(score["Yancey"])
	score["yancey"] = 123
	fmt.Println(score["yancey"])

	delete(score, "yancey")
	fmt.Println(score["yancey"])
	score["llyancey"] = 1234
	fmt.Println(score["lyyancey"])

	fmt.Println(len(score))

	for k, v := range score {
		fmt.Println(k, v)
	}

	var users map[string]map[string]string
	users = map[string]map[string]string{"yancey": {"addr": "shandong", "age": "123"}}
	fmt.Println(users)
	users["hello"] = map[string]string{"addr": "dezhou", "age": "123"}
	fmt.Println(users["hello"])
	delete(users, "yancey")
	fmt.Println(users)
	users["hello"]["addr"] = "jining"
	fmt.Println(users)
	delete(users["hello"], "age")
	fmt.Println(users)
}
