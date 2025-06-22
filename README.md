# 🌸 BigInt Calculator

A JavaFX-based calculator that supports arbitrary-precision integer operations using a custom `BigInt` class — styled with a modern pink-themed UI, animated buttons, and a soft gradient background.


<img width="335" alt="Screenshot 2025-06-22 at 12 53 19 PM" src="https://github.com/user-attachments/assets/be4c097f-9208-40a0-bee0-466947e5e6c5" />



<img width="332" alt="Screenshot 2025-06-22 at 12 56 19 PM" src="https://github.com/user-attachments/assets/55fec78f-0652-421c-9bda-7c6ff7ac3a1e" />



<img width="331" alt="Screenshot 2025-06-22 at 12 56 34 PM" src="https://github.com/user-attachments/assets/f6f743e5-46e1-4195-85db-bb14aeb217d2" />



<img width="332" alt="Screenshot 2025-06-22 at 12 55 07 PM" src="https://github.com/user-attachments/assets/c4af3471-c004-40c0-8c85-42f0f5c7354e" />



<img width="331" alt="Screenshot 2025-06-22 at 12 55 44 PM" src="https://github.com/user-attachments/assets/dded5041-9744-4f6b-ac0d-f8497e2db561" />


---

## ✨ Features

- Supports all basic operations: `+`, `−`, `×`, `÷`
- Handles **negative numbers** and **very large integers**
- Animated buttons for interactive feedback
- Smooth result fade-in animation
- Clean, rounded UI components with a pastel background
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


