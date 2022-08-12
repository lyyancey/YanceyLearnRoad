package main

import "fmt"

type Sender interface {
	Send(to string, msg string) error
	SendAll(tos []string, msg string) error
}

type EmailSender struct {
	Email string
}

type SmsSender struct {
	Sms string
}

func (sms SmsSender) Send(to, msg string) error {
	fmt.Println(sms.Sms, to, msg)
	return nil
}
func (sms SmsSender) SendAll(tos []string, msg string) error {
	fmt.Println(sms.Sms)
	for _, v := range tos {
		sms.Send(v, msg)
	}
	return nil
}
func (s EmailSender) Send(to, msg string) error {
	fmt.Println("发送邮件给：", to, msg)
	return nil
}
func (sender EmailSender) SendAll(tos []string, msg string) error {
	for _, s := range tos {
		sender.Send(s, msg)
	}
	return nil
}
func do(sender EmailSender) {
	sender.Send("领导", "工作日志")
}
func main() {
	var send Sender = EmailSender{"yancey"}
	fmt.Printf("%T, %v\n", send, send)
	send.Send("yancey", "good morning")
	send.SendAll([]string{"张三", "李四", "王五", "赵六"}, "晚上好")
	send01, ok := send.(EmailSender)
	fmt.Println(ok)
	fmt.Println(send01.Email)

	var send02 Sender = SmsSender{"10086"}
	send02.SendAll([]string{"张三", "李四", "王五", "赵六"}, "晚上好")

	switch v := send02.(type) {
	case SmsSender:
		fmt.Println(v.Sms)
	case EmailSender:
		fmt.Println(v.Email)
	}

}
