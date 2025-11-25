# Data Structures & Algorithms — Problem & Practice Framework

A lightweight framework for **learning**, **practicing**, and **mastering** Data Structures & Algorithms through interactive problem execution and automatic practice validation.

---

## 📁 Project Structure

```
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java               # Base class for reference solutions
├── Base_Practice.java              # Base class for practice attempts
├── Problem.java                    # Annotation for problem auto-discovery
├── Practice.java                   # Annotation for practice auto-discovery
├── Problem_Manager.java            # Scans and manages problems/practices
├── Run_Problems.java               # Main interactive console app
├── Problem_Class_Creator.java      # Auto-generates problem templates
│
├── Problems/                       # Reference solutions
│   ├── Binary_Search/
│   │   ├── Binary_Search.java
│   │   ├── First_Bad_Version.java
│   │   └── Search_And_Insert.java
│   └── Other_Categories/...
│
├── Practices/                      # Practice re-implementations
│   ├── Binary_Search/
│   │   └── Binary_Search_Practice.java
│   └── Other_Categories/...
│
└── Utils/
    └── HelperMethods.java
```

---

## ⚡ Quick Start

### 1. Create a New Problem

Place the class under `Problems/<Category>` and annotate with `@Problem`:

```java
@Problem(name = "Binary Search", category = "Binary Search")
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

### 2. Create a Practice Implementation

Practice classes allow you to **re-implement the solution from memory**, then automatically compare your output against the reference:

```java
@Practice(
    problemName = "Binary Search",
    category = "Binary Search",
    description = "Practice implementation for binary search"
)
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

**Key annotation fields:**

* `problemName` — must match the @Problem name **exactly**
* `category` — organizes items in menus
* `description` — optional

---

## ▶️ Running the Application

```bash
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
```

Or run `Run_Problems` directly from your IDE.

---

## 🛠️ Problem Class Creator (Recommended)

Generate problem templates with automatic naming, formatting, and validation:

```bash
cd src/main/java
javac DataStructureAndAlgorithms/Utils/Problem_Class_Creator.java
java DataStructureAndAlgorithms.Utils.Problem_Class_Creator
```

**Example Output:**

```
Enter problem name: splitArrayLargestSum
Enter category: Binary Search
Enter return type: int

✓ Converted to: Integer
✅ Problem class created successfully!
📁 Location: Problems/Dynamic_Programming/Split_Array_Largest_Sum.java
```

**Generated file (simplified):**

```java
@Problem(name = "Split Array Largest Sum", category = "Binary Search")
public class Split_Array_Largest_Sum extends Base_Problem<Integer> {
    @Override
    protected Integer solve() {
        throw new UnsupportedOperationException("Unimplemented method");
    }
}
```

**Creator Highlights:**

* Accepts flexible naming (`split-array`, `splitArray`, etc.)
* Auto-formats class names & display names
* Creates missing category folders
* Converts primitives to wrapper types
* Adds necessary imports automatically

---

## 🧭 Interactive Menu Overview

```
=========================================
    DATA STRUCTURES & ALGORITHMS
       PROBLEM & PRACTICE RUNNER
=========================================

1. List all problems
2. List problems by category
3. Run a specific problem
4. List all practices
5. List practices by category
6. Run a specific practice
7. Exit
```

### Problems

* Reference solutions
* Annotated with `@Problem`
* Used for learning correct algorithms

### Practices

* Your own re-implementation
* Annotated with `@Practice`
* Automatically compared to the problem result

**Example:**

```
=== Running Binary Search ===
Solution: 3
```

**Practice attempt:**

```
Your answer: 3
Expected answer: 3
Result: ✅ CORRECT
```

---

## 🏷️ Annotation Reference

### Problem

```java
@Problem(
    name = "Meaningful Name",
    category = "Algorithm Category",
    description = "Optional details"
)
```

### Practice

```java
@Practice(
    problemName = "Binary Search",
    category = "Binary Search",
    description = "Optional details"
)
```

---

## ➕ Adding New Problems & Practices

### **Method 1 (Recommended):** Problem Class Creator

Automatically generates a ready-to-implement template.

### **Method 2:** Manual Creation

1. Add class in `Problems/<Category>/`
2. Add practice class in `Practices/<Category>/`
3. Ensure:

   * `@Problem` and `@Practice` annotations are correct
   * `problemName` **matches exactly**

The system discovers and loads everything automatically.

---

## 🌟 Features

* 🚀 **Automatic reflection-based discovery**
* 🧠 **Practice mode with correctness verification**
* ✨ **Flexible name matching**
  (`BinarySearch`, `binary-search`, `binary search`, etc.)
* 📂 **Automatic category organization**
* 🔧 **Smart problem class generator**
* 📘 **Type validation & automatic primitive conversion**
* 🧹 **Import management for collection types**
* 💬 **Instant feedback (correct/incorrect)**

---

## 🔧 Supported Return Types

* Primitive wrappers: `Integer`, `Boolean`, `Double`
* Arrays: `Integer[]`, `String[]`, …
* Collections:

  * `List<Integer>`, `Map<String, Integer>`
  * Nested: `List<List<Integer>>`, `Map<String, List<Boolean>>`

---

## ❗ Troubleshooting

### Problems not detected?

* Ensure class is inside `Problems/`
* Must extend `Base_Problem`
* Must use `@Problem`

### Practices not detected?

* Must extend `Base_Practice`
* Must use `@Practice`
* `problemName` **must match** the problem

### Class Creator problems?

* Only valid Java types allowed
* Primitives automatically converted
* Category folders are created automatically

---

## 🤝 Contributing

1. Generate a new problem (via Creator or manually)
2. Implement solution in `solve()`
3. Create a matching practice class
4. Add annotations properly
5. Test with the interactive menu

---

## 🎯 Learning Strategy

1. **Study** the reference implementation
2. **Practice** by writing your own version
3. **Verify** with automatic comparison
4. **Analyze** differences
5. **Repeat** regularly
6. Track your progress through results

---

## 🎉 Happy Coding!

This system is built to make algorithm mastery **systematic**, **repeatable**, and **fun**.
Keep practicing and leveling up! 🚀

