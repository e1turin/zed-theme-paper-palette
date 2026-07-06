// C++ sample for Alabaster theme
// Demonstrates common C++ language features

#include <algorithm>
#include <array>
#include <cstdint>
#include <iostream>
#include <map>
#include <memory>
#include <optional>
#include <set>
#include <string>
#include <variant>
#include <vector>

// ============================================
//  Preprocessor
// ============================================

#define MAX_SIZE 1000
#define PI 3.14159
#define SQUARE(x) ((x) * (x))

// ============================================
//  Namespace
// ============================================

namespace shapes {
    inline namespace v2 {
        constexpr double pi = 3.141592653589793;
    }
}

namespace utils {
    template <typename T>
    T identity(T value) { return value; }
}

// ============================================
//  Enum
// ============================================

enum Color { Red, Green, Blue };

enum class Status : uint8_t {
    Pending = 0,
    Active = 1,
    Inactive = 2,
    Error = 3,
};

// ============================================
//  Type Aliases
// ============================================

using StringVec = std::vector<std::string>;
using ShapePtr = std::unique_ptr<class Shape>;

template <typename T>
using Result = std::optional<T>;

// ============================================
//  Abstract Base Class
// ============================================

class Shape {
public:
    Shape() = default;
    Shape(const std::string& name) : name_(name) {}
    virtual ~Shape() = default;

    // Pure virtual
    virtual double area() const = 0;
    virtual double perimeter() const = 0;

    // Virtual with default implementation
    virtual std::string draw() const {
        return "Shape(" + name_ + ")";
    }

    // Non-virtual
    std::string name() const { return name_; }
    void setName(const std::string& name) { name_ = name; }

private:
    std::string name_;
};

// ============================================
//  Inheritance & Polymorphism
// ============================================

class Circle final : public Shape {
public:
    Circle(double radius, const std::string& name = "Circle")
        : Shape(name), radius_(radius) {
        if (radius <= 0) {
            throw std::invalid_argument("Radius must be positive");
        }
    }

    double area() const override {
        return shapes::pi * radius_ * radius_;
    }

    double perimeter() const override {
        return 2.0 * shapes::pi * radius_;
    }

    std::string draw() const override {
        return "Circle(radius=" + std::to_string(radius_) + ")";
    }

    double radius() const { return radius_; }

private:
    double radius_;
};

class Rectangle final : public Shape {
public:
    Rectangle(double w, double h, const std::string& name = "Rectangle")
        : Shape(name), width_(w), height_(h) {}

    double area() const override {
        return width_ * height_;
    }

    double perimeter() const override {
        return 2.0 * (width_ + height_);
    }

    std::string draw() const override {
        return "Rectangle(w=" + std::to_string(width_)
             + ", h=" + std::to_string(height_) + ")";
    }

private:
    double width_;
    double height_;
};

// ============================================
//  Template Class
// ============================================

template <typename T, size_t N>
class Buffer {
public:
    Buffer() : size_(N) {}

    void push(const T& value) {
        if (pos_ < N) {
            data_[pos_++] = value;
        }
    }

    T& operator[](size_t index) {
        return data_[index];
    }

    const T& operator[](size_t index) const {
        return data_[index];
    }

    size_t size() const { return size_; }
    size_t count() const { return pos_; }
    bool empty() const { return pos_ == 0; }
    bool full() const { return pos_ >= N; }

    auto begin() { return data_.begin(); }
    auto begin() const { return data_.begin(); }
    auto end() { return data_.begin() + pos_; }
    auto end() const { return data_.begin() + pos_; }

private:
    std::array<T, N> data_{};
    size_t pos_ = 0;
    size_t size_;
};

// Template specialization
template <>
class Buffer<bool, 8> {
public:
    void push(bool value) {
        if (pos_ < 8) {
            bits_ |= (static_cast<uint8_t>(value) << pos_);
            pos_++;
        }
    }
    size_t count() const { return pos_; }
private:
    uint8_t bits_ = 0;
    size_t pos_ = 0;
};

// ============================================
//  Template Functions
// ============================================

template <typename T>
T min(T a, T b) {
    return (a < b) ? a : b;
}

template <typename T, typename... Args>
auto sum(T first, Args... rest) {
    if constexpr (sizeof...(rest) == 0) {
        return first;
    } else {
        return first + sum(rest...);
    }
}

// Concept (C++20)
template <typename T>
concept Numeric = std::is_arithmetic_v<T>;

template <Numeric T>
T doubleValue(T value) {
    return value * 2;
}

// ============================================
//  Operator Overloading
// ============================================

class Vector2 {
public:
    Vector2() = default;
    Vector2(double x, double y) : x_(x), y_(y) {}

    Vector2 operator+(const Vector2& other) const {
        return Vector2(x_ + other.x_, y_ + other.y_);
    }

    Vector2 operator*(double scalar) const {
        return Vector2(x_ * scalar, y_ * scalar);
    }

    friend Vector2 operator*(double scalar, const Vector2& v) {
        return Vector2(v.x_ * scalar, v.y_ * scalar);
    }

    Vector2& operator+=(const Vector2& other) {
        x_ += other.x_;
        y_ += other.y_;
        return *this;
    }

    bool operator==(const Vector2& other) const {
        return x_ == other.x_ && y_ == other.y_;
    }

    friend std::ostream& operator<<(std::ostream& os, const Vector2& v) {
        os << "(" << v.x_ << ", " << v.y_ << ")";
        return os;
    }

    double x() const { return x_; }
    double y() const { return y_; }

private:
    double x_ = 0.0;
    double y_ = 0.0;
};

// ============================================
//  Move Semantics
// ============================================

class Resource {
public:
    Resource() : data_(new int[1024]) {
        std::cout << "Resource acquired\n";
    }

    ~Resource() {
        delete[] data_;
        std::cout << "Resource released\n";
    }

    // Move constructor
    Resource(Resource&& other) noexcept
        : data_(std::exchange(other.data_, nullptr)) {}

    // Move assignment
    Resource& operator=(Resource&& other) noexcept {
        if (this != &other) {
            delete[] data_;
            data_ = std::exchange(other.data_, nullptr);
        }
        return *this;
    }

    // Delete copy
    Resource(const Resource&) = delete;
    Resource& operator=(const Resource&) = delete;

private:
    int* data_;
};

// ============================================
//  Static Members
// ============================================

class Registry {
public:
    static Registry& instance() {
        static Registry inst;
        return inst;
    }

    void registerShape(const Shape& shape) {
        names_.insert(shape.name());
    }

    static size_t maxEntries() { return 1000; }

private:
    Registry() = default;
    std::set<std::string> names_;
};

// ============================================
//  Free Functions
// ============================================

// Overloaded functions
void print(int value) {
    std::cout << "int: " << value << "\n";
}

void print(double value) {
    std::cout << "double: " << value << "\n";
}

void print(const std::string& value) {
    std::cout << "string: " << value << "\n";
}

// Function with default arguments
void log(const std::string& msg,
         const std::string& level = "INFO") {
    std::cout << "[" << level << "] " << msg << "\n";
}

// ============================================
//  Lambda Expressions
// ============================================

auto makeMultiplier(double factor) {
    return [factor](double x) { return x * factor; };
}

// ============================================
//  Main
// ============================================

int main() {
    // Literals
    auto integer = 42;
    auto floating = 3.14;
    auto hex = 0xFF;
    auto binary = 0b1010;
    auto octal = 0755;       // octal
    auto longVal = 42L;
    auto unsignedVal = 42u;
    auto floatVal = 3.14f;
    auto doubleVal = 3.14;
    auto charVal = 'A';
    auto wideChar = L'A';
    auto stringVal = "hello";
    auto rawString = R"(raw\nstring)";
    auto boolTrue = true;
    auto boolFalse = false;

    // nullptr
    int* ptr = nullptr;

    // References
    int x = 42;
    int& ref = x;
    const int& cref = x;

    // Dynamic allocation (smart pointers)
    auto circle = std::make_unique<Circle>(5.0, "My Circle");
    auto rect = std::make_shared<Rectangle>(10.0, 20.0);

    // Polymorphism via virtual functions
    Shape* shapes[] = { circle.get(), rect.get() };
    for (const auto* s : shapes) {
        std::cout << s->draw() << " area=" << s->area() << "\n";
    }

    // Templates
    auto m = min(3, 7);
    auto m2 = min(3.14, 2.71);
    auto m3 = min<std::string>("apple", "banana");

    // Variadic template
    auto s = sum(1, 2, 3, 4, 5);
    auto s2 = sum(1.5, 2.5, 3.0);

    // Buffer template
    Buffer<int, 5> buf;
    buf.push(10);
    buf.push(20);
    buf.push(30);

    for (const auto& v : buf) {
        std::cout << v << "\n";
    }

    // Lambda
    auto double_ = [](int x) { return x * 2; };
    auto result = double_(21);

    auto mult = makeMultiplier(3.0);
    auto multResult = mult(4.5);

    // STL algorithms with lambdas
    std::vector<int> nums = {1, 2, 3, 4, 5, 6, 7, 8};
    std::vector<int> evens;

    std::copy_if(nums.begin(), nums.end(),
                 std::back_inserter(evens),
                 [](int n) { return n % 2 == 0; });

    // Range-based for loop
    for (int n : evens) {
        std::cout << n << " ";
    }
    std::cout << "\n";

    // Map with structured bindings (C++17)
    std::map<std::string, int> scores = {
        {"Alice", 95},
        {"Bob", 87},
        {"Carol", 92},
    };

    for (const auto& [name, score] : scores) {
        std::cout << name << ": " << score << "\n";
    }

    // Optional (C++17)
    std::optional<int> maybeValue = 42;
    if (maybeValue.has_value()) {
        std::cout << "Value: " << *maybeValue << "\n";
    }

    std::optional<int> empty = std::nullopt;
    auto fallback = empty.value_or(-1);

    // Variant (C++17)
    std::variant<int, double, std::string> v = 42;
    v = 3.14;
    v = "hello";

    if (std::holds_alternative<std::string>(v)) {
        std::cout << "String: " << std::get<std::string>(v) << "\n";
    }

    // Visit pattern
    auto visitor = [](const auto& val) {
        std::cout << "Variant value\n";
    };
    std::visit(visitor, v);

    // constexpr
    constexpr int arraySize = 100;
    constexpr double piSquared = shapes::pi * shapes::pi;

    // static_assert
    static_assert(arraySize > 0, "Array size must be positive");
    static_assert(sizeof(int) >= 4, "Expected 32-bit ints");

    // Operator overloading
    Vector2 v1(3.0, 4.0);
    Vector2 v2(1.0, 2.0);
    auto v3 = v1 + v2;
    auto v4 = v1 * 2.0;
    auto v5 = 2.0 * v1;

    std::cout << v3 << " " << v4 << "\n";

    // Move semantics
    Resource res1;
    Resource res2 = std::move(res1);

    // Exceptions
    try {
        Circle bad(-1.0);
    } catch (const std::invalid_argument& e) {
        std::cout << "Caught: " << e.what() << "\n";
    }

    // C-style and C++ casts
    double d = 3.14;
    int i = static_cast<int>(d);
    const int* constPtr = &i;
    int* mutablePtr = const_cast<int*>(constPtr);

    // auto and decltype
    auto a = 42;
    decltype(a) b = 100;
    using TypeOfA = decltype(a);

    // Initializer list
    std::vector<int> initList = {1, 2, 3, 4, 5};

    // Using functions
    print(42);
    print(3.14);
    print("hello");
    log("Server started");
    log("Error occurred", "ERROR");

    // Static member usage
    Registry::instance().registerShape(*circle);
    std::cout << "Max entries: " << Registry::maxEntries() << "\n";

    return 0;
}
