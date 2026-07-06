// === PACKAGE ===
package samples

// === IMPORTS — grouped and single ===
import (
	"encoding/json"
	"errors"
	"fmt"
	"math"
	"sync"
	"time"
)

import "os"

// === CONST — iota, single, grouped ===
const UniverseAnswer = 42

const (
	StatusOK    = 200
	StatusTeapot = 418
)

const (
	_  = iota             // 0 — blank
	KB = 1 << (10 * iota) // 1 << 10
	MB                    // 1 << 20
	GB                    // 1 << 30
)

// === VAR — single, grouped ===
var debug = false

var (
	count   int
	label   string
	pi      = math.Pi
	coeffs  = []float64{1.0, 2.0}
)

// === TYPE DEFINITIONS ===
type ID int
type Code string
type Flags uint8

// --- Struct with tags ---
type User struct {
	ID        ID      `json:"id"`
	Name      string  `json:"name,omitempty"`
	Email     string  `json:"email"`
	CreatedAt int64   `json:"created_at"`
	Score     float64 `json:"score"`
}

// --- Embedded struct (composition) ---
type Admin struct {
	User             // embedded
	Role      string `json:"role"`
	TokenHash string `json:"-"`
}

// --- Interface definition ---
type Stringer interface {
	String() string
}

// --- Interface embedding ---
type ReadWriter interface {
	Read(p []byte) (n int, err error)
	Write(p []byte) (n int, err error)
	Close() error
}

// --- Generic type ---
type Pair[T any] struct {
	First  T
	Second T
}

type Numeric interface {
	~int | ~float64
}

type Vec[T Numeric] struct {
	X, Y T
}

// === METHODS: value receiver, pointer receiver ===
func (u User) FullName() string {
	return u.Name
}

func (u *User) GrantScore(points float64) {
	u.Score += points
}

// --- Method on non-struct type ---
func (id ID) IsZero() bool {
	return id == 0
}

// --- Generic method ---
func (v Vec[T]) Dot(other Vec[T]) T {
	return v.X*other.X + v.Y*other.Y
}

// === FUNCTIONS — named returns, multi-return, variadic ===
func divMod(a, b int) (quotient, remainder int) {
	quotient = a / b
	remainder = a % b
	return
}

func sum(values ...int) int {
	total := 0
	for _, v := range values {
		total += v
	}
	return total
}

// --- Function returning error ---
func parseID(raw string) (ID, error) {
	if raw == "" {
		return 0, errors.New("empty id")
	}
	return ID(len(raw)), nil
}

// --- Generic function ---
func zero[T any]() T {
	var v T
	return v
}

// === FUNCTION LITERALS / CLOSURES ===
func makeCounter() func() int {
	n := 0
	return func() int {
		n++
		return n
	}
}

// === DEFER ===
func readFileAtomic(path string) (err error) {
	// simple defer
	defer fmt.Println("cleanup after", path)

	// defer with recover
	defer func() {
		if r := recover(); r != nil {
			err = fmt.Errorf("panic: %v", r)
		}
	}()

	_, err = os.Open(path)
	return
}

// === CONTROL FLOW ===

// --- if/else ---
func classify(n int) string {
	if n < 0 {
		return "negative"
	} else if n == 0 {
		return "zero"
	}
	return "positive"
}

// --- for: classic ---
func sumUpTo(n int) int {
	s := 0
	for i := 0; i < n; i++ {
		s += i
	}
	return s
}

// --- for: while-style ---
func gcd(a, b int) int {
	for b != 0 {
		a, b = b, a%b
	}
	return a
}

// --- for: infinite + break with label ---
func findFirst(haystack []int, needle int) int {
	result := -1
outer:
	for {
		for i, v := range haystack {
			if v == needle {
				result = i
				break outer
			}
		}
		break
	}
	return result
}

// --- range: slice, map ---
func iterateExamples(items []string, scores map[string]int) {
	for i, s := range items {
		fmt.Printf("items[%d]=%s\n", i, s)
	}
	for k, v := range scores {
		fmt.Printf("scores[%s]=%d\n", k, v)
	}
	// continue with label
loop:
	for i := 0; i < 10; i++ {
		for j := 0; j < 10; j++ {
			if i*j == 0 {
				continue loop
			}
		}
	}
	_ = loop
}

// --- switch: expression ---
func httpStatusText(code int) string {
	switch code {
	case 200:
		return "OK"
	case 404:
		return "Not Found"
	case 418:
		return "I'm a teapot"
	default:
		return "Unknown"
	}
}

// --- switch: no expression (boolean) ---
func classifyAge(age int) string {
	switch {
	case age < 0:
		return "invalid"
	case age < 13:
		return "child"
	case age < 20:
		return "teen"
	default:
		return "adult"
	}
}

// --- fallthrough ---
func fallthroughDemo(n int) string {
	switch n {
	case 1:
		return "one"
	case 2:
		return "two"
		// fallthrough would go here; kept as comment to avoid useless fallthrough
	}
	return "other"
}

// === SELECT with channels ===
func selectDemo(ch1, ch2 chan int, done chan struct{}) {
	select {
	case v := <-ch1:
		fmt.Println("got from ch1:", v)
	case v := <-ch2:
		fmt.Println("got from ch2:", v)
	case <-done:
		fmt.Println("done")
	default:
		fmt.Println("no ready channels")
	}
}

// === GOROUTINES ===
func launchWorkers() {
	var wg sync.WaitGroup
	for i := 0; i < 5; i++ {
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			fmt.Println("worker", id, "done")
		}(i)
	}
	wg.Wait()
}

// === CHANNELS ===
func channelDemo() {
	ch := make(chan int, 3) // buffered
	ch <- 10
	ch <- 20
	ch <- 30
	close(ch)

	for v := range ch {
		fmt.Println(v)
	}

	// unbuffered
	done := make(chan struct{})
	go func() {
		done <- struct{}{}
	}()
	<-done
}

// === sync.Mutex ===
type Counter struct {
	mu    sync.Mutex
	value int
}

func (c *Counter) Inc() {
	c.mu.Lock()
	c.value++
	c.mu.Unlock()
}

// === POINTERS ===
func pointerDemo() {
	x := 42
	p := &x
	*p = 21
	fmt.Println(x) // 21

	// pointer to struct
	u := &User{Name: "Alice"}
	u.GrantScore(10) // implicit (*u).GrantScore
}

// === make / new ===
func makeNewDemo() {
	sl := make([]int, 0, 10) // len=0 cap=10
	m := make(map[string]int, 100)

	p := new(int)  // *int, zero value
	pp := new(User) // *User, zero value
	_, _, _ = sl, m, p
	_ = pp
}

// === SLICE ops ===
func sliceDemo() {
	nums := []int{1, 2, 3, 4, 5}
	nums = append(nums, 6, 7)

	sub := nums[1:4] // [2,3,4]
	_ = sub

	dst := make([]int, 3)
	copy(dst, nums[:3])

	fmt.Println("len:", len(nums), "cap:", cap(nums))
	fmt.Println("sub:", nums[0:2])
}

// === MAP ops ===
func mapDemo() {
	m := map[string]int{
		"a": 1,
		"b": 2,
	}
	m["c"] = 3

	delete(m, "a")

	// comma-ok idiom
	v, ok := m["b"]
	if ok {
		fmt.Println(v)
	}
}

// === ERROR HANDLING ===
func errorDemo(path string) error {
	if path == "" {
		return errors.New("path is empty")
	}
	if _, err := os.Open(path); err != nil {
		return fmt.Errorf("open %s: %w", path, err)
	}
	return nil
}

// === PANIC / RECOVER ===
func safeCall(fn func()) (err error) {
	defer func() {
		if r := recover(); r != nil {
			err = fmt.Errorf("recovered: %v", r)
		}
	}()
	fn()
	return
}

// === TYPE ASSERTIONS ===
func typeAssertionDemo(v any) string {
	// comma-ok form
	s, ok := v.(string)
	if ok {
		return s
	}
	// plain assertion (could panic)
	_ = v.(int)
	return ""
}

// === TYPE SWITCH ===
func typeSwitchDemo(v any) string {
	switch v := v.(type) {
	case nil:
		return "nil"
	case int:
		return fmt.Sprintf("int:%d", v)
	case string:
		return fmt.Sprintf("string:%q", v)
	case bool:
		return fmt.Sprintf("bool:%v", v)
	case error:
		return fmt.Sprintf("error:%s", v)
	case []byte:
		return fmt.Sprintf("bytes:%x", v)
	default:
		return fmt.Sprintf("unknown:%T", v)
	}
}

// === BLANK IDENTIFIER ===
func blankDemo() {
	_ = 42                      // value ignored
	f, _ := os.Open("/dev/null") // error ignored
	_ = f
	for range 5 { // Go 1.22 — no iteration variables
		fmt.Print(".")
	}
}

// === LITERALS: string, rune, int, float, hex, binary, octal, imaginary, complex ===
func literalDemo() {
	// string
	s1 := "hello\nworld"    // interpreted
	s2 := `raw "string" here` // raw

	// rune
	r1 := 'a'
	r2 := 'あ'
	r3 := '\n'

	// int
	i1 := 42
	i2 := 0xFF     // hex
	i3 := 0b1010   // binary
	i4 := 0o644    // octal

	// float
	f1 := 3.14
	f2 := 1.5e10

	// imaginary / complex
	c1 := 1 + 2i
	c2 := complex(3, 4)

	// bool / nil
	b1 := true
	b2 := false
	var p *int = nil

	_, _, _, _, _, _, _, _, _, _, _ = s1, s2, r1, r2, r3, i1, i2, i3, i4, f1, f2
	_, _, _, _, _ = c1, c2, b1, b2, p
}

// === fmt verbs ===
func fmtVerbDemo(u User) {
	fmt.Printf("%%s:  %s\n", u.Name)
	fmt.Printf("%%d:  %d\n", u.ID)
	fmt.Printf("%%v:  %v\n", u)
	fmt.Printf("%%+v: %+v\n", u)
	fmt.Printf("%%#v: %#v\n", u)
	fmt.Printf("%%T:  %T\n", u)
}

// === json with struct tags ===
func jsonDemo() {
	u := User{ID: 1, Name: "Bob", Email: "bob@example.com"}
	data, _ := json.Marshal(u)
	fmt.Println(string(data))

	var u2 User
	_ = json.Unmarshal(data, &u2)
}

// === time.Duration ===
func durationDemo() {
	d := 5*time.Second + 500*time.Millisecond
	_ = d

	t := time.Now()
	_ = t.Format(time.RFC3339)
	_ = t.Add(10 * time.Minute)
}

// === SHORT VARIABLE DECLARATION ===
func shortVarDemo() {
	a := 1
	b, c := "hello", 3.14
	d, e := complex(1, 2), false
	_, _, _, _ = a, b, c, d, e
}

// === MULTIPLE init() ===
func init() {
	fmt.Println("first init")
}

func init() {
	fmt.Println("second init")
}
