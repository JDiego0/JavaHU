# 🚀 Corporate Talent Hub - Java Control Flow & Version Evolution

## 📌 Overview

This project implements a console-based employee management system developed in Java, designed to demonstrate the evolution of programming constructs between **Java 8 (Legacy)** and **Java 17/21 (Modern LTS)**.

The system allows dynamic interaction with the user, employee data processing, and robust error handling, following best practices in control flow and data validation.

---

## 🎯 Objective

To build a system that:

* Uses both **traditional (Java 8)** and **modern (Java 17/21)** syntax
* Processes multiple employees dynamically
* Applies validation, exception handling, and data structures
* Demonstrates clean and safe coding practices

---

## ⚙️ Features Implemented

### 🔹 1. Legacy vs Modern Switch

* **Java 8 Switch (Legacy)**:

  * Uses `case:` and `break`
  * Risk of *fall-through* if `break` is omitted

* **Java 17/21 Switch Expression (Modern)**:

  * Uses `->`
  * No need for `break`
  * Safer and more readable

---

### 🔹 2. Dynamic Input Handling

* Uses `Scanner` inside a `do-while` loop
* Keeps the system running until user exits
* Implements `var` (Java 11+) for type inference

---

### 🔹 3. Primitive Type Validation

Validation using `if / else` for:

* `int` → number of employees and age (valid ranges)
* `double` → scores (0.0 - 5.0)
* `double` → salary (non-negative)

✔ Prevents invalid data input
✔ Improves system robustness

---

### 🔹 4. Matrix Processing (double[][])

* Stores employee performance (3 trimesters)
* Uses **nested for loops** to process data
* Calculates average performance per employee

---

### 🔹 5. Casting (double → int)

* Converts average score to simplified integer value

```java
int simplifiedScore = (int) average;
```

📌 Note:

> Casting removes decimal precision (e.g., 4.8 → 4)

---

### 🔹 6. Exception Handling

* Uses `try-catch` to handle `InputMismatchException`
* Prevents program crashes due to invalid input

📌 Java 17/21 improvement:

> Provides more detailed and readable error diagnostics compared to Java 8

---

### 🔹 7. Ternary Operator

Used to determine employee promotion status:

```java
String status = (average >= 3.5) ? "PROMOTED" : "NOT PROMOTED";
```

---

## 🏗️ Project Structure

```
src/
 └── main/
     └── java/
         └── org/example/
             ├── Main.java
             ├── SwitchLegacy.java
             └── SwitchModern.java
```

---

## ▶️ How to Run

1. Clone the repository:

```bash
git clone <your-repo-url>
```

2. Navigate to the project folder:

```bash
cd corporate-talent-hub
```

3. Compile and run:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 🧠 Key Concepts Learned

* Control flow structures in Java
* Differences between Java 8 and Java 17/21
* Type inference using `var`
* Exception handling best practices
* Matrix (2D array) data processing
* Data validation techniques

---

## ✅ Acceptance Criteria Covered

✔ Functional menu using Java 8 switch
✔ Modern switch expression implemented
✔ Use of `var` for local variables
✔ Matrix processing with nested loops
✔ Casting from double to int
✔ Robust exception handling
✔ Input validation using `if / else`

---

## 📌 Author

Juan Diego Garcia Chavarriaga.

---
