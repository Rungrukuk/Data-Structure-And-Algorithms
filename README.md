# Data Structures & Algorithms Problem Runner

A Java-based console application designed to help you practice, organize, and run algorithm problems through an interactive menu-driven system.

---

## 🚀 Overview

This project provides a structured and extensible framework for managing algorithm problems. It includes:

* Organized problem categories
* Automatic problem discovery via reflection
* Interactive console-based problem runner
* Optional test case support

---

## 📁 Project Structure

```
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java          # Base class for all problems
├── Base_Test.java             # Base class for tests (optional)
├── Problem.java               # Annotation for auto-discovery
├── Problem_Manager.java       # Handles scanning and execution
├── Run_Problems.java          # Main entry point (interactive menu)
└── Problems/                  # Your algorithm implementations
    ├── Binary_Search/
    │   ├── Binary_Search.java
    │   ├── First_Bad_Version.java
    │   └── Search_And_Insert.java
    └── Other_Categories/
        └── ...
```

---

## ⚡ Quick Start

### 1. Creating a New Problem

Create a new Java class inside the appropriate category and annotate it using `@Problem`:

```
package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.Base_Problem;
import DataStructureAndAlgorithms.Problem;

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

### 2. Running the Application

Compile and run:

```
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
```

Or simply run `Run_Problems` from your IDE.

---

## 🧭 Using the Interactive Menu

When started, the program displays:

```
=========================================
    DATA STRUCTURES & ALGORITHMS
          PROBLEM RUNNER
=========================================

=== MAIN MENU ===
1. List all problems
2. List problems by category
3. Run a specific problem
4. Exit
```

### Option 1: List All Problems

Displays every discovered problem:

```
=== ALL PROBLEMS ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
 3. SearchInsert              [Binary Search]
 4. TwoSum                    [Arrays]
```

Selecting a problem runs it immediately.

### Option 2: List Problems by Category

Groups problems for easier browsing:

```
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion
 3. SearchInsert

--- Arrays ---
 4. TwoSum
 5. ContainerWithMostWater
```

### Option 3: Run Specific Problem

You can enter variations like:

* `binarysearch`
* `binary search`
* `BinarySearch`
* `binary-search`
* `binary_search`

Example:

```
=== RUN SPECIFIC PROBLEM ===
Enter problem name: binary search

=== Running Binary Search ===
Answer: 3
```

If not found:

```
❌ Problem not found: binariserch

📋 Available problems:
   1. Binary Search
   2. First Bad Version
   3. Search And Insert
```

---

## 🧪 Creating Tests (Optional)

Test classes help you **reinforce your understanding** of a problem by solving it again later and **checking whether your new solution still matches the correct answer**.

They act as a personal practice tool—allowing you to re‑attempt problems for memorization while automatically comparing your result with the expected output.

```
@Problem(name = "BinarySearchTest", category = "Tests")
public class Binary_Search_Test extends Base_Test<Integer, Integer> {

    @Override
    protected Integer test() {
        return yourSolution();
    }

    @Override
    protected Integer expected() {
        return 3;
    }

    @Override
    protected boolean compare() {
        return test().equals(expected());
    }
}
```
```
@Problem(name = "BinarySearchTest", category = "Tests")
public class Binary_Search_Test extends Base_Test<Integer, Integer> {

@Override
protected Integer test() {
    return yourSolution();
}

@Override
protected Integer expected() {
    return 3;
}

@Override
protected boolean compare() {
    return test().equals(expected());
}

}
```

```
@Problem(name = "BinarySearchTest", category = "Tests")
public class Binary_Search_Test extends Base_Test<Integer, Integer> {

    @Override
    protected Integer test() {
        return yourSolution();
    }

    @Override
    protected Integer expected() {
        return 3;
    }

    @Override
    protected boolean compare() {
        return test().equals(expected());
    }
}
```

---

## 🏷️ Problem Annotation Reference

```
@Problem(
    name = "MeaningfulName",         // Required: unique identifier
    category = "Algorithm Category", // Required: used for grouping
    description = "Optional details" // Optional
)
```

---

## ➕ Adding New Categories

1. Create a new folder under `DataStructureAndAlgorithms.Problems/`
2. Add your problem classes inside it
3. Annotate them with `@Problem`
4. They will be automatically discovered at runtime

---

## 🌟 Features

* **Auto-Discovery** — Just annotate and run
* **Flexible Input Matching** — Supports many naming formats
* **Category Grouping** — Clean, organized structure
* **Interactive UI** — Easy console navigation
* **Helpful Error Messages** — Fix problems quickly
* **Extensible Architecture** — Add problems and tests easily

---

## 🧩 Dependencies

* Java **8+**
* Reflections library (configured in Maven)

---

## ❗ Troubleshooting

### No problems are found?

* Ensure they are inside the `Problems` package
* Check the `@Problem` annotation
* Confirm the class extends `Base_Problem`

### Compilation errors?

* Check classpath and dependencies
* Ensure package declarations match folder structure

---

## 🤝 Contributing

To add new problems:

1. Create a new class in a category
2. Extend `Base_Problem`
3. Implement `solve()`
4. Add a `@Problem` annotation
5. Test your solution

---

Happy coding! 🚀
