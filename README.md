# Data Structures & Algorithms — Problem & Practice Runner

A Java-based console application designed to help you **solve**, **practice**, and **verify** algorithm problems using an interactive menu-driven interface. It automatically discovers problems and practice implementations, allowing you to learn efficiently and track your mastery.

---

## 🚀 Overview

This project provides a clean, extensible framework for learning algorithms by combining:

* **Organized problem categories**
* **Automatic problem discovery** using reflection
* **Interactive console runner**
* **Built-in practice system** to reinforce knowledge
* **Automatic comparison** between your practice solution and the original implementation

---

## 📁 Project Structure

```
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java              # Base class for problem implementations
├── Base_Practice.java             # Base class for practice attempts
├── Problem.java                   # Annotation used for auto-discovery
├── Problem_Manager.java           # Scans, manages, and executes items
├── Run_Problems.java              # Main interactive entry point
│
├── Problems/                      # Official solutions
│   ├── Binary_Search/
│   │   ├── Binary_Search.java
│   │   ├── First_Bad_Version.java
│   │   └── Search_And_Insert.java
│   └── Other_Categories/
│       └── ...
│
└── Practices/                     # Your practice solutions
    ├── Binary_Search/
    │   └── Binary_Search_Practice.java
    └── Other_Categories/
        └── ...
```

---

## ⚡ Quick Start

### 1. Creating a New Problem

Add a class under `Problems/<Category>` and annotate it using `@Problem`:

```
@Problem(name = "BinarySearch", category = "Binary Search")
public class Binary_Search extends Base_Problem<Integer> {

    public final int[] nums = { -1, 0, 3, 5, 9, 12 };
    public final int target = 5;

    @Override
    protected Integer solve() {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
```

---

### 2. Creating a Practice Implementation

Practice classes allow you to **re-implement the solution from memory** and compare it to the original.

```
public class Binary_Search_Practice extends Base_Practice<Integer, Binary_Search> {

    public Binary_Search_Practice(Binary_Search problem) {
        super(problem);
    }

    @Override
    protected Integer practice() {
        int left = 0, right = problem.nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (problem.nums[mid] == problem.target) return mid;
            if (problem.nums[mid] < problem.target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
```

---

### 3. Running the Application

```
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
```

Or simply run `Run_Problems` from your IDE.

---

## 🧭 Interactive Menu Overview

When launched:

```
=========================================
    DATA STRUCTURES & ALGORITHMS
       PROBLEM & PRACTICE RUNNER
=========================================

=== MAIN MENU ===
1. List all problems
2. List problems by category
3. Run a specific problem
4. List all practices
5. List practices by category
6. Run a specific practice
7. Exit
```

---

## 📝 Problems vs Practices

### **Problems**

* Contain the **main reference solution**.
* Used to learn correct algorithmic approaches.

### **Practices**

* Your **memory-based re-implementation**.
* Automatically compared against the real solution.
* Helps reinforce retention and mastery.

---

## 🔍 Problem Management (Options 1–3)

### List All Problems

```
=== ALL PROBLEMS ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
 3. SearchInsert              [Binary Search]
 4. TwoSum                    [Arrays]
```

### List by Category

```
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion
 3. SearchInsert
```

### Run Specific Problem

Supports flexible input:

* binarysearch
* binary search
* BinarySearch
* binary-search
* binary_search

Example:

```
=== Running Binary Search ===
Solution: 3
```

---

## 💪 Practice Management (Options 4–6)

### List All Practices

```
=== ALL PRACTICES ===
1. BinarySearch              [Binary Search]
2. FirstBadVersion           [Binary Search]
```

### Run a Practice

```
=== Practicing Binary Search ===
Your answer: 3
Expected answer: 3
Result: ✅ CORRECT
```

Incorrect output:

```
Your answer: 2
Expected answer: 3
Result: ❌ INCORRECT
```

---

## ❗ Error Handling

Missing problem or practice:

```
❌ Problem not found: binariserch

📋 Available problems:
   1. Binary Search
   2. First Bad Version
   3. Search And Insert
```

---

## 🏷️ Problem Annotation Reference

```
@Problem(
    name = "MeaningfulName",          // Required
    category = "Algorithm Category",  // Required
    description = "Optional details"  // Optional
)
```

---

## ➕ Adding New Categories

1. Create a new folder inside `Problems/`.
2. Implement your problem classes.
3. Create matching practice classes inside `Practices/`.
4. Annotate problems with `@Problem`.
5. They will be discovered automatically.

---

## 🌟 Features

* Automatic problem & practice discovery
* Flexible input matching
* Clear category grouping
* Interactive console interface
* Knowledge verification via Practice mode
* Instant answer comparison
* Clean, extensible architecture

---

## 🧩 Dependencies

* **Java 8+**
* **Reflections** (Maven managed)

---

## ❗ Troubleshooting

### Problems not found?

* Ensure they are inside `Problems/`
* Verify `@Problem` annotation
* Ensure class extends `Base_Problem`

### Practices not found?

* Ensure they are inside `Practices/`
* Extend `Base_Practice`
* Pass the correct problem instance in the constructor

### Compilation errors?

* Check package names
* Validate directory structure

---

## 🤝 Contributing

1. Add a new problem in `Problems/`.
2. Implement its solution.
3. Add a practice class in `Practices/`.
4. Implement your practice logic.
5. Test both.

---

## 🎯 Learning Strategy

1. **Study:** Examine the official solution.
2. **Practice:** Re-implement from memory.
3. **Verify:** Compare your output using Practice mode.
4. **Repeat:** Build long-term algorithm mastery.

---

Happy coding! 🚀
