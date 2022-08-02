package calc

import "testing"

func TestAdd(t *testing.T) {
	if 4 != Add(1, 3) {
		t.Error("1+3 != 4")
	}
}
