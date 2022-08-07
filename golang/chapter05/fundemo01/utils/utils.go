package utils

type StructName struct {
	ID   int
	name string
}
type Address struct {
	Regin string
	Num   int
}
type User struct {
	ID   int
	name string
	Add  Address
}

var UserInfo = User{
	ID:   123,
	name: "yancey",
	Add: Address{
		Regin: "shandong",
		Num:   123,
	},
}
