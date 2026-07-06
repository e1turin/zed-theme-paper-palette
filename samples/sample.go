// Go sample for Alabaster theme
// Demonstrates common Go language features

package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"math"
	"os"
	"sync"
	"time"
)

// Global constants
const (
	MaxRetries = 3
	Pi         = 3.14159
	Debug      = true
	Version    = "1.0.0"
)

// iota enumeration pattern
type Status int

const (
	StatusPending Status = iota
	StatusActive
	StatusInactive
	StatusError
)

// Stringer implementation for Status
func (s Status) String() string {
	switch s {
	case StatusPending:
		return "Pending"
	case StatusActive:
		return "Active"
	case StatusInactive:
		return "Inactive"
	case StatusError:
		return "Error"
	default:
		return "Unknown"
	}
}

// Global variables
var (
	counter  = 0
	isActive = false
	mu       sync.Mutex
)

// Struct definition
type User struct {
	Name    string
	Age     int
	IsAdmin bool
}

// Struct with tags (JSON serialization)
type Config struct {
	Host    string        `json:"host"`
	Port    int           `json:"port"`
	Timeout time.Duration `json:"timeout"`
	Debug   bool          `json:"debug"`
}

// Embedded struct (composition over inheritance)
type AdminUser struct {
	User
	Role string
}

// Interface definition
type Shape interface {
	Area() float64
	Perimeter() float64
}

// Interface composition
type DrawableShape interface {
	Shape
	Draw() string
}

// Pointer receiver method
type Circle struct {
	Radius float64
	Name   string
}

func NewCircle(radius float64, name string) *Circle {
	return &Circle{Radius: radius, Name: name}
}

func (c *Circle) Area() float64 {
	if c.Radius <= 0 {
		return 0.0
	}
	return Pi * math.Pow(c.Radius, 2)
}

func (c *Circle) Perimeter() float64 {
	return 2 * Pi * c.Radius
}

func (c *Circle) Draw() string {
	return fmt.Sprintf("Circle(radius=%.2f, name=%q)", c.Radius, c.Name)
}

// Value receiver method
type Rectangle struct {
	Width  float64
	Height float64
}

func (r Rectangle) Area() float64 {
	return r.Width * r.Height
}

func (r Rectangle) Perimeter() float64 {
	return 2 * (r.Width + r.Height)
}

func (r Rectangle) Draw() string {
	return fmt.Sprintf("Rectangle(w=%.2f, h=%.2f)", r.Width, r.Height)
}

// Variadic function
func sum(values ...int) int {
	total := 0
	for _, v := range values {
		total += v
	}
	return total
}

// Function returning multiple values (error pattern)
func divide(a, b float64) (float64, error) {
	if b == 0 {
		return 0, errors.New("division by zero")
	}
	return a / b, nil
}

// Generic function (Go 1.18+)
func identity[T any](value T) T {
	return value
}

// Generic constraint
type Number interface {
	~int | ~float64
}

func double[T Number](value T) T {
	return value * 2
}

// Method on non-struct type
type Score int

func (s Score) IsPassing() bool {
	return s >= 60
}

// Factory function
func NewConfig(host string, port int) *Config {
	return &Config{
		Host:    host,
		Port:    port,
		Timeout: 30 * time.Second,
		Debug:   false,
	}
}

// Init function runs on package load
func init() {
	if Debug {
		fmt.Println("Debug mode enabled")
	}
}

func main() {
	// String literals
	rawString := `Raw string literal\nnot escaped`
	interpreted := "Regular string with \nescape sequences"
	formatted := fmt.Sprintf("Formatted: %d, %s", 42, "hello")
	multiLine := "line one\n" +
		"line two\n" +
		"line three"

	// Numeric literals
	integer := 42
	floating := 3.14
	hex := 0xFF
	binary := 0b1010
	octal := 0o755
	imaginary := 1 + 2i
	unsigned := uint64(100)
	byteVal := byte('A')
	runeVal := '😊'

	// Boolean and nil
	isTrue := true
	isFalse := false
	var nothing *int = nil

	// Pointers
	val := 42
	ptr := &val
	deref := *ptr
	*ptr = 100

	// Creating struct instances
	user := User{
		Name:    "Alice",
		Age:     30,
		IsAdmin: true,
	}

	// Anonymous struct
	anon := struct {
		X int
		Y int
	}{X: 10, Y: 20}

	// Embedded struct
	admin := AdminUser{
		User: User{Name: "Bob", Age: 28, IsAdmin: true},
		Role: "superadmin",
	}

	// Pointer to struct
	circle := NewCircle(5.0, "My Circle")
	rect := Rectangle{Width: 10.0, Height: 20.0}

	// Interface values
	var s Shape = circle
	area := s.Area()
	perim := s.Perimeter()

	// Type assertion
	if c, ok := s.(*Circle); ok {
		fmt.Println("It's a circle:", c.Name)
	}

	// Type switch
	switch v := s.(type) {
	case *Circle:
		fmt.Println("Circle with radius:", v.Radius)
	case Rectangle:
		fmt.Println("Rectangle:", v.Width, "x", v.Height)
	default:
		fmt.Println("Unknown shape")
	}

	// Switch statement
	status := StatusActive
	switch status {
	case StatusPending:
		fmt.Println("Pending...")
	case StatusActive:
		fmt.Println("Active")
	case StatusInactive:
		fmt.Println("Inactive")
	case StatusError:
		fmt.Println("Error")
	default:
		fmt.Println("Unknown status")
	}

	// Switch with no expression (if-else chain)
	score := 85
	switch {
	case score >= 90:
		fmt.Println("A")
	case score >= 80:
		fmt.Println("B")
	case score >= 70:
		fmt.Println("C")
	default:
		fmt.Println("F")
	}

	// For loop (classic)
	for i := 0; i < MaxRetries; i++ {
		if i%2 == 0 {
			continue
		}
		if i > 5 {
			break
		}
		fmt.Println(i)
	}

	// For loop (while-style)
	i := 0
	for i < 3 {
		fmt.Println(i)
		i++
	}

	// Infinite loop with break
	total := 0
	for {
		total++
		if total > 5 {
			break
		}
	}

	// Range over slice
	items := []string{"a", "b", "c"}
	for index, value := range items {
		fmt.Printf("%d: %s\n", index, value)
	}

	// Range over map
	scores := map[string]int{
		"Alice": 95,
		"Bob":   87,
		"Carol": 92,
	}
	for name, score := range scores {
		fmt.Printf("%s: %d\n", name, score)
	}

	// Range with blank identifier
	for _, v := range items {
		fmt.Println(v)
	}

	// Slice operations
	var slice []int
	slice = append(slice, 1, 2, 3)
	slice = append(slice, 4, 5) // append multiple
	sub := slice[1:3]           // slicing
	sub = slice[:2]
	sub = slice[2:]
	copied := make([]int, len(slice))
	copy(copied, slice)

	// Make for slice, map, channel
	ch := make(chan int, 10)

	// Map operations
	config := make(map[string]string)
	config["host"] = "localhost"
	config["port"] = "8080"
	value, exists := config["host"]
	delete(config, "port")

	// Error handling
	result, err := divide(10, 0)
	if err != nil {
		fmt.Println("Error:", err)
	} else {
		fmt.Println("Result:", result)
	}

	// Defer
	file, err := os.Open("sample.go")
	if err != nil {
		fmt.Println("File error:", err)
	} else {
		defer file.Close()
	}

	// Deferred function literal
	defer func() {
		if r := recover(); r != nil {
			fmt.Println("Recovered:", r)
		}
	}()

	// Goroutine with WaitGroup
	var wg sync.WaitGroup
	for i := 0; i < 3; i++ {
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			fmt.Println("Goroutine:", id)
		}(i)
	}
	wg.Wait()

	// Channel operations
	ch1 := make(chan string)
	go func() {
		ch1 <- "message from goroutine"
	}()
	msg := <-ch1

	// Select with channels
	ch2 := make(chan int, 1)
	ch2 <- 42
	select {
	case val := <-ch2:
		fmt.Println("Received:", val)
	case <-time.After(1 * time.Second):
		fmt.Println("Timeout")
	default:
		fmt.Println("No value ready")
	}

	// JSON serialization
	config2 := NewConfig("example.com", 8080)
	jsonData, _ := json.MarshalIndent(config2, "", "  ")
	fmt.Println(string(jsonData))

	// Variadic function
	sumResult := sum(1, 2, 3, 4, 5)
	nums := []int{10, 20, 30}
	sumResult2 := sum(nums...) // slice expansion

	// Generic function
	intVal := identity(42)
	strVal := identity("hello")
	doubled := double(21)

	// Labeled for and goto
outer:
	for x := 0; x < 3; x++ {
		for y := 0; y < 3; y++ {
			if x == 1 && y == 1 {
				break outer
			}
		}
	}

	// Zero values
	var zeroInt int
	var zeroStr string
	var zeroBool bool
	var zeroSlice []int
	var zeroMap map[string]int
	var zeroPtr *int

	// Using functions and methods
	greeting := user.Name + " is " + fmt.Sprint(user.Age) + " years old"

	// Print results
	fmt.Println(area, perim, greeting)

	// Silence unused variable diagnostics (demo sample)
	_ = rawString
	_ = interpreted
	_ = formatted
	_ = multiLine
	_ = integer
	_ = floating
	_ = hex
	_ = binary
	_ = octal
	_ = imaginary
	_ = unsigned
	_ = byteVal
	_ = runeVal
	_ = isTrue
	_ = isFalse
	_ = nothing
	_ = deref
	_ = anon
	_ = admin
	_ = rect
	_ = sub
	_ = ch
	_ = value
	_ = exists
	_ = msg
	_ = intVal
	_ = strVal
	_ = doubled
	_ = zeroInt
	_ = zeroStr
	_ = zeroBool
	_ = zeroSlice
	_ = zeroMap
	_ = zeroPtr
	_ = sumResult
	_ = sumResult2
}
