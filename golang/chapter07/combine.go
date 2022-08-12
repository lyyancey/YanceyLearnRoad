package main

import "fmt"

type SenderActor interface {
	Send(msg string) error
}
type ReciverActor interface {
	Recive() (string, error)
}
type Client interface {
	SenderActor
	ReciverActor
}

type MSNClient struct {
}

func (msn MSNClient) Send(msg string) error {
	fmt.Println("send", msg)
	return nil
}
func (msn MSNClient) Recive() (string, error) {
	fmt.Println("recive")
	return "msg", nil
}
func main() {
	msn := MSNClient{}
	var s SenderActor = msn
	var r ReciverActor = msn
	var c Client = msn

	s.Send("hello")
	r.Recive()
	c.Send("hhh")
	c.Recive()
}
