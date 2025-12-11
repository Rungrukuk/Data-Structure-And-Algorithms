# Data Structures & Algorithms — Problem & Practice Framework

A modular, enterprise-grade framework for **learning**, **practicing**, and **mastering** Data Structures & Algorithms using a clean, layered architecture with automatic validation, class discovery, code generation, and advanced practice management.

---

## 🚀 Modern Architecture Overview

```
src/main/java/DataStructureAndAlgorithms/
├── app/                         # Application layer
│   ├── AppLauncher.java         # Entry point
│   ├── DependencyContainer.java # IoC container (dependency injection)
│   └── ApplicationController.java # High-level application coordinator
│
├── core/                        # Core domain models
│   ├── base/
│   │   ├── BasePractice.java
│   │   └── BaseProblem.java
│   ├── annotations/
│   │   ├── Practice.java
│   │   └── Problem.java
│   └── models/                  # DTOs
│       ├── Difficulty.java
│       ├── PracticeInfo.java
│       ├── PracticeResult.java
│       ├── ProblemInfo.java
│       └── ProblemResult.java
│
├── ui/                          # Presentation layer
│   ├── UIManager.java
│   ├── UiFactory.java
│   ├── prompts/
│   │   └── Prompter.java
│   └── navigation/
│       ├── MenuNavigator.java
│       └── SelectionHandler.java
│
├── domain/                      # Business logic
│   ├── problems/
│   │   ├── ProblemRepository.java
│   │   ├── ProblemOrchestrator.java
│   │   └── ProblemExecutor.java
│   ├── practices/
│   │   ├── PracticeRepository.java
│   │   ├── PracticeOrchestrator.java
│   │   └── PracticeExecutor.java
│   ├── flows/
│   │   ├── BaseFlowHandler.java
│   │   ├── PracticeFlowHandler.java
│   │   └── ProblemFlowHandler.java
│   └── creators/
│       ├── ProblemGenerator.java
│       └── PracticeGenerator.java
│
├── infrastructure/              # Technical services
│   ├── discovery/
│   │   └── ClassScanner.java
│   ├── file/
│   │   └── FileManager.java
│   ├── input/
│   │   └── InputHandler.java
│   └── runner/
│       └── CodeRunner.java
│
├── menus/                       # Menu definitions
│   ├── MenuOption.java
│   ├── MenuRegistry.java
│   └── MenuKey.java
│
├── exceptions/                  # Structured exception hierarchy
│   ├── core/
│   ├── domain/
│   └── infrastructure/
│
└── utils/                       # Shared utilities
    ├── ApplicationConstants.java
    ├── NameFormatter.java
    ├── ProblemComparators.java
    ├── ResultFormatter.java
    ├── SimilarityHelper.java
    └── TypeValidator.java
```

---

## 🌟 Key Improvements

### **Architectural Benefits**

* Clean separation of concerns
* Centralized dependency injection
* Highly testable and modular
* Designed for scalability and extensibility
* Clear responsibility boundaries

### **Feature Enhancements**

* Bulk practice management (reset by category/difficulty)
* Advanced filtering and fuzzy name matching
* Automatic class discovery using annotations
* Dynamic menu navigation
* Code generation for problem & practice templates
* Result comparison with similarity scoring

---

## ⚡ Quick Start

### **1. Run the Application**

```bash
javac -d out src/main/java/DataStructureAndAlgorithms/app/AppLauncher.java
java -cp out DataStructureAndAlgorithms.app.AppLauncher
```

### **2. Create a Problem**

```java
@Problem(name = "Binary Search", category = "Search", difficulty = "Easy")
public class BinarySearch extends BaseProblem<Integer> {
    private final int[] nums = {-1, 0, 3, 5, 9, 12};
    private final int target = 9;

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

### **3. Create a Practice Implementation**

```java
@Practice(problemName = "Binary Search", category = "Search")
public class BinarySearchPractice extends BasePractice<Integer> {
    @Override
    protected Integer practice() {
        // Your implementation
        return -1;
    }
}
```

---

## 🎮 Interactive Features

### **Main Menu**

```
=========================================
DATA STRUCTURES & ALGORITHMS FRAMEWORK
=========================================
1. Run Problems & Practices
2. Create New Problem
3. Manage Practices
4. Exit
```

### **Practice Management**

* Generate practice from any problem
* Reset a practice to default
* Bulk reset by category/difficulty/all
* Fuzzy search (e.g., "Binry Serch")

---

## 🧠 Architecture Deep Dive

### **Layer Overview**

* **Application Layer** — Launching, dependency wiring, main coordination
* **Domain Layer** — Business logic, orchestrators, flow controllers
* **UI Layer** — Menu navigation, user input, output formatting
* **Infrastructure Layer** — File operations, class scanning, execution engine

### **Key Components**

* `ClassScanner` → discovers annotated classes
* `ProblemOrchestrator` → manages problem workflow
* `PracticeOrchestrator` → manages practice lifecycle
* `CodeRunner` → executes code and compares results
* `MenuNavigator` → UI menu system

---

## 📊 Result Comparison System

Automatically evaluates practice output vs expected result.

```java
PracticeResult result = new PracticeResult(
    problemName,
    practiceOutput,
    expectedOutput,
    isCorrect
);
```

Similarity-based scoring is used for non-exact matches.

---

## 🔧 Advanced Usage

* Reset all practices in a category
* Reset only HARD difficulties
* Generate new problem templates
* Custom sorting using `ProblemComparators`

---

## 🧪 Testing Strategy

* Unit tests for orchestrators, utilities, and infrastructure
* Integration tests for end-to-end flow
* Architecture compliance tests

---

## 🔮 Future Enhancements

* Progress tracking via database
* Web UI with visualization
* Collaboration features
* Performance benchmarking
* Plugin system for new generators

---

## 🎯 Learning Path

**Beginners:**

* Study problems → Implement practices → Compare → Repeat

**Advanced Users:**

* Add new categories
* Analyze performance
* Implement advanced algorithms

---

## 🎉 Happy Coding!

This framework provides a scalable and professional architecture for mastering DSA through structured, hands-on practice!

