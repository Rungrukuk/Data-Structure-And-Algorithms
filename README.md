# Data Structures & Algorithms — Problem & Practice Runner
A Java-based console application to **solve, practice, and verify algorithm problems** through an interactive menu. Problems and practice implementations are automatically discovered using reflection, enabling efficient learning and tracking of your mastery.

---

## 🚀 Overview
This project provides a clean and extensible framework for algorithm learning:

- **Organized problem categories**
- **Automatic discovery** of problems and practices via reflection
- **Interactive console runner**
- **Practice system** for memory-based reimplementation
- **Automatic comparison** between your practice solution and the reference solution

---

## 📁 Project Structure
```
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java        # Base class for problems
├── Base_Practice.java       # Base class for practice implementations
├── Problem.java             # Annotation for problem auto-discovery
├── Practice.java            # Annotation for practice auto-discovery
├── Problem_Manager.java     # Handles scanning and execution
├── Run_Problems.java        # Main interactive entry point
│
├── Problems/                # Reference solutions
│   ├── Binary_Search/
│   │   ├── Binary_Search.java
│   │   ├── First_Bad_Version.java
│   │   └── Search_And_Insert.java
│   └── Other_Categories/
│       └── ...
│
└── Practices/               # Practice implementations
    ├── Binary_Search/
    │   └── Binary_Search_Practice.java
    └── Other_Categories/
        └── ...
```

---

## ⚡ Quick Start

### 1. Creating a New Problem
Add a class under `Problems/<Category>` and annotate it with `@Problem`:
```java
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

### 2. Creating a Practice Implementation
Practice classes allow you to **re-implement the solution from memory** and automatically compare your output to the reference. Annotate with `@Practice`:
```java
@Practice(
    problemName = "BinarySearch",
    category = "Binary Search",
    description = "Practice implementation for binary search algorithm"
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

**Key Annotation Fields:**
- `problemName`: Must match the corresponding `@Problem` name
- `category`: Organizes practices in the menu
- `description`: Optional details

### 3. Running the Application
```bash
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
```
Or run `Run_Problems` directly from your IDE.

---

## 🧭 Interactive Menu Overview
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

### Problems vs Practices
**Problems:**
- Reference solution implementations
- Annotated with `@Problem`
- Learn correct algorithmic approaches

**Practices:**
- Re-implement from memory
- Annotated with `@Practice` to link to the corresponding problem
- Automatically compared with the reference solution
- Reinforces retention and mastery

---

### Problem Management (Options 1–3)
**List All Problems:**
```
=== ALL PROBLEMS ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
 3. SearchInsert              [Binary Search]
 4. TwoSum                    [Arrays]
```

**List by Category:**
```
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion
 3. SearchInsert
```

**Run Specific Problem:**
Supports flexible input:
```
binarysearch | binary search | BinarySearch | binary-search | binary_search
```

Example:
```
=== Running Binary Search ===
Solution: 3
```

---

### Practice Management (Options 4–6)
**List All Practices:**
```
=== ALL PRACTICES ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
```

**List Practices by Category:**
```
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion

--- Arrays ---
 3. TwoSum
```

**Run a Practice:**
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

## 🏷️ Annotation Reference
**Problem:**
```java
@Problem(
    name = "MeaningfulName",
    category = "Algorithm Category",
    description = "Optional details"
)
```
**Practice:**
```java
@Practice(
    problemName = "BinarySearch",
    category = "Binary Search",
    description = "Optional details"
)
```

---

## ➕ Adding New Content
- Create a new package under `Problems/`
- Add problem classes annotated with `@Problem`
- Create corresponding practice classes in `Practices/` annotated with `@Practice`
- Ensure `problemName` matches exactly
- The system auto-discovers everything at runtime

---

## 🌟 Features
- Automatic discovery via reflection
- Annotation-based problem-practice linking
- Flexible input matching
- Clean category organization
- Interactive console interface
- Knowledge verification in Practice mode
- Instant feedback (✅ / ❌)
- Extensible architecture

---

## 🧩 Dependencies
- Java 8+
- Reflections library (Maven managed)

---

## ❗ Troubleshooting
**Problems not found?**
- Ensure they exist in `Problems/`
- Verify `@Problem` annotation
- Confirm the class extends `Base_Problem`

**Practices not found?**
- Ensure they exist in `Practices/`
- Verify `@Practice` annotation
- Check `problemName` matches the problem
- Confirm class extends `Base_Practice`

**Compilation errors?**
- Check package structure
- Verify dependencies and classpath

---

## 🤝 Contributing
1. Add a new problem in `Problems/<Category>/`
2. Annotate with `@Problem` and implement `solve()`
3. Add a corresponding practice class in `Practices/<Category>/`
4. Annotate with `@Practice` linking to the problem
5. Implement `practice()` method
6. Test both implementations

---

## 🎯 Learning Strategy
1. **Study:** Review the reference solution in `Problems/`
2. **Practice:** Implement your own solution in `Practices/`
3. **Verify:** Use Practice mode to compare your result
4. **Repeat:** Reinforce long-term mastery
5. **Track:** Monitor progress via practice results

Happy coding! 🚀
