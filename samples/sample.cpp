// =============================================================================
// C++ Syntax Highlighting Sample — Light Theme Test File
// Covers C++11 through C++20 constructs in compact form.
// Not meant to compile — syntax coverage only.
// =============================================================================

// === PREPROCESSOR ===

#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <array>
#include <optional>
#include <variant>
#include <string>
#include <memory>
#include <algorithm>
#include <typeinfo>
#include <type_traits>

#define MACRO_CONST 42
#define MACRO_FN(x) ((x) * (x))
#pragma once
#pragma GCC diagnostic push

// === NAMESPACE ===

namespace outer {
inline namespace inner {
    namespace v1 {
        int value = 1;
    } // namespace v1
} // namespace inner
} // namespace outer

namespace ns1::ns2::inline ns3 {
    int x = 0;
}

// === ENUMERATIONS ===

enum Color { Red, Green, Blue };
enum Status : uint8_t { OK = 0, Warning, Error };
enum class Direction : char { North, South, East, West };
enum class Flags : unsigned { None = 0, Read = 1, Write = 2 };

// === CLASS — BASE with virtual, pure virtual, access specifiers ===

class Base {
public:
    Base() = default;
    explicit Base(int a) : value_(a) {}
    Base(const Base&) = default;
    Base(Base&&) noexcept = default;
    virtual ~Base() = default;

    virtual void foo() const = 0;
    virtual int bar(int x) const;

    [[nodiscard]] int getValue() const { return value_; }

    static int instanceCount() { return count_; }

protected:
    [[maybe_unused]] int protected_field_ = 0;

private:
    int value_ = 42;
    const int id_ = nextId();
    static int count_;

    static int nextId() { return count_++; }

    friend void friendFunc(const Base& b);
    friend class FriendClass;
};

int Base::count_ = 0;

int Base::bar(int x) const { return x * 2; }

// === INHERITANCE ===

class Derived final : public Base {
public:
    using Base::Base;

    Derived() : Base(1), extra_(3.14) {}
    Derived(const Derived&) = delete;
    Derived(Derived&&) noexcept = default;

    Derived& operator=(const Derived&) = delete;
    Derived& operator=(Derived&&) noexcept = default;

    void foo() const override {}
    int bar(int x) const override final { return x * 3; }

private:
    double extra_ = 0.0;
};

// === ABSTRACT CLASS ===

class Interface {
public:
    virtual ~Interface() = default;
    virtual void doit() = 0;
};

// === FRIEND ===

class FriendClass {
public:
    void inspect(const Base& b) {
        (void)b.value_;
    }
};

void friendFunc(const Base& b) {
    (void)b.value_;
}

// === OPERATOR OVERLOADING ===

class Vector2 {
public:
    Vector2() = default;
    Vector2(double x, double y) : x_(x), y_(y) {}

    Vector2 operator+(const Vector2& rhs) const {
        return {x_ + rhs.x_, y_ + rhs.y_};
    }

    Vector2& operator+=(const Vector2& rhs) {
        x_ += rhs.x_; y_ += rhs.y_;
        return *this;
    }

    Vector2 operator-() const { return {-x_, -y_}; }

    bool operator==(const Vector2& rhs) const = default;

    friend std::ostream& operator<<(std::ostream& os, const Vector2& v) {
        return os << '(' << v.x_ << ", " << v.y_ << ')';
    }

    friend Vector2 operator*(double s, const Vector2& v) {
        return {s * v.x_, s * v.y_};
    }

private:
    double x_ = 0.0, y_ = 0.0;
};

// === TEMPLATES — class and function ===

template <typename T, size_t N>
class Array {
public:
    T& operator[](size_t i) { return data_[i]; }
    const T& operator[](size_t i) const { return data_[i]; }
    size_t size() const { return N; }

    T* begin() { return data_; }
    T* end() { return data_ + N; }

private:
    T data_[N] = {};
};

template <typename T>
T max(T a, T b) {
    return (a > b) ? a : b;
}

// === TEMPLATE SPECIALIZATION ===

template <>
class Array<bool, 1> {
public:
    bool operator[](size_t) const { return false; }
    size_t size() const { return 1; }
};

// === VARIADIC TEMPLATES ===

template <typename... Args>
auto sum(Args... args) {
    return (... + args);  // fold expression
}

template <typename... Args>
void printAll(Args&&... args) {
    ((std::cout << std::forward<Args>(args) << ' '), ...);
}

// === AUTO, DECLTYPE, CONSTEXPR, CONEVAL ===

auto autoVar = 42;
constexpr auto constexprVal = 3.14159;

template <typename T, typename U>
auto add(T a, U b) -> decltype(a + b) {
    return a + b;
}

decltype(auto) forwardLike(auto&& x) {
    return std::forward<decltype(x)>(x);
}

consteval int square(int n) { return n * n; }

constexpr int factorial(int n) {
    if constexpr (n <= 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

static_assert(factorial(5) == 120, "factorial(5) should be 120");

// === CASTS ===

void castExamples() {
    double d = 3.14;
    int i = static_cast<int>(d);
    const int* cp = &i;
    int* p = const_cast<int*>(cp);
    // reinterpret_cast<uintptr_t>(p);
    // dynamic_cast<Derived*>(basePtr);
    (void)p;
}

// === LAMBDA EXPRESSIONS ===

void lambdaExamples() {
    int factor = 2;

    auto times = [factor](int x) -> int { return x * factor; };

    auto genericLambda = [](auto a, auto b) { return a + b; };

    auto mutableLambda = [factor]() mutable { return ++factor; };

    int result = [](int a, int b) { return a + b; }(3, 4);
    (void)result;
}

// === TYPE ALIASES ===

using StringVec = std::vector<std::string>;
typedef std::map<std::string, int> StringMap;
template <typename T>
using Vec = std::vector<T>;

// === SMART POINTERS + MOVE ===

void moveSemantics() {
    auto uptr = std::make_unique<int>(42);
    auto sptr = std::make_shared<double>(3.14);
    std::weak_ptr<double> wptr = sptr;

    std::string s = "hello";
    std::string moved = std::move(s);

    auto locked = wptr.lock();
    (void)locked;
}

// === PERFECT FORWARDING ===

template <typename T, typename... Args>
auto makeWrapper(Args&&... args) {
    return std::make_unique<T>(std::forward<Args>(args)...);
}

// === STL CONTAINERS + RANGE FOR + STRUCTURED BINDINGS ===

void stlExamples() {
    std::vector<int> vec = {1, 2, 3, 4, 5};
    std::map<std::string, int> ages = {{"Alice", 30}, {"Bob", 25}};
    std::set<int> uniq = {3, 1, 4, 1, 5};
    std::array<int, 3> arr = {10, 20, 30};
    std::optional<int> opt = 42;
    std::variant<int, double, std::string> var = 3.14;
    std::string str = "hello world";

    for (int x : vec) {
        (void)x;
    }

    for (const auto& [name, age] : ages) {
        (void)name;
        (void)age;
    }

    if (auto val = std::get_if<double>(&var)) {
        (void)val;
    }
}

// === EXCEPTIONS ===

void exceptionExamples() noexcept {
    try {
        throw std::runtime_error("something went wrong");
    } catch (const std::exception& e) {
        std::cerr << e.what() << '\n';
    } catch (...) {
        // catch-all
    }
}

// === LITERALS ===

void literalExamples() {
    int dec = 42;
    int hex = 0xFF;
    int bin = 0b1010;
    int oct = 071;
    auto f = 3.14f;
    auto d = 2.71828;
    auto sci = 1.5e-3;
    auto ch = 'A';
    auto str = "hello";
    auto raw = R"(raw string with "quotes" and \no escapes)";
    auto wide = L'Ω';
    auto utf8 = u8"utf-8 string";
    auto boolean = true;
    auto null = nullptr;
}

// === ATTRIBUTES ===

[[nodiscard]] int mustUseResult() { return 42; }

[[deprecated("use newFunc instead")]]
void oldFunc() {}

void attrExamples() {
    [[maybe_unused]] int unused = 0;
    [[maybe_unused]] auto result = mustUseResult();
}

// === CONST CORRECTNESS ===

class ConstCorrect {
public:
    void mutating() {}
    void reading() const {}
    const int& ref() const { return val_; }
    int& ref() { return val_; }

private:
    int val_ = 0;
};

// === STATIC MEMBERS ===

struct Counter {
    static int total;
    static int next() { return total++; }

    static inline int inline_static = 0;
};

int Counter::total = 0;

// === OVERLOADED FUNCTIONS + DEFAULT ARGUMENTS ===

void overloaded(int x) { (void)x; }
void overloaded(double x) { (void)x; }
void overloaded(int x, int y) { (void)x; (void)y; }

void defaults(int a, int b = 0, double c = 1.0) {
    (void)a; (void)b; (void)c;
}

// === SIZEOF / ALIGNOF / TYPEID ===

void sizeExamples() {
    auto sz = sizeof(int);
    auto al = alignof(double);
    auto ti = typeid(int);
    (void)sz; (void)al; (void)ti;
}

// === NEW / DELETE ===

void newDeleteExamples() {
    int* p = new int(42);
    delete p;

    int* arr = new int[10];
    delete[] arr;
}

// === THIS POINTER ===

class WithThis {
public:
    void test() {
        this->value_ = 1;
        (*this).value_ = 2;
    }
    WithThis& self() { return *this; }

private:
    int value_ = 0;
};

// === IF CONSTEXPR + SFINAE (type_traits) ===

template <typename T>
auto getValue(T t) {
    if constexpr (std::is_pointer_v<T>) {
        return *t;
    } else {
        return t;
    }
}

// === INLINE VARIABLE ===

inline int global_inline = 100;

// === FINAL SENTINEL ===
// EOF
