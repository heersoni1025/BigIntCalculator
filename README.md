# 🌸 BigInt Calculator

A JavaFX-based calculator that supports arbitrary-precision integer operations using a custom `BigInt` class — styled with a modern pink-themed UI, animated buttons, and a soft gradient background.

---

## ✨ Features

- Supports all basic operations: `+`, `−`, `×`, `÷`
- Handles **negative numbers** and **very large integers**
- Animated buttons for interactive feedback
- Smooth result fade-in animation
- Clean, rounded UI components with pastel background
- Responsive layout and modern font styling

---

## 💡 BigInt Functionality

This calculator uses a custom-built `BigInt` class to support **arbitrary-precision integer arithmetic**, beyond standard `int` or `long` types.

### ✅ Supported Operations:
- `+` Addition
- `−` Subtraction
- `×` Multiplication
- `÷` Division

---

### 🙌 Acknowledgments

It is **not affiliated with any academic coursework**.

---

### 🧠 Highlights:
- Can handle integers with **thousands of digits**
- Fully supports **negative inputs and outputs**
- Division supports:
  - Negative numerator or denominator (e.g., `-123456789 ÷ 3`)
  - Double negatives (e.g., `-123 ÷ -3` = `41`)
  - Divide-by-zero detection with a friendly error message
- Custom logic (no Java `BigInteger`) using doubling and subtraction

```java
BigInt a = new BigInt("-999999999999999999999");
BigInt b = new BigInt("333333333333333333333");
BigInt result = a.div(b); // ➜ -3


