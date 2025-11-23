Data Structures & Algorithms Problem & Practice Runner
A Java-based console application designed to help you practice, organize, and run algorithm problems through an interactive menu-driven system with built-in knowledge verification.

🚀 Overview
This project provides a structured and extensible framework for managing algorithm problems. It includes:

Organized problem categories

Automatic problem discovery via reflection

Interactive console-based problem runner

Practice system to test your knowledge and retention

Separate practice implementations with automatic result comparison

📁 Project Structure
text
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java          # Base class for all problems
├── Base_Practice.java         # Base class for practice implementations
├── Problem.java               # Annotation for auto-discovery
├── Problem_Manager.java       # Handles scanning and execution
├── Run_Problems.java          # Main entry point (interactive menu)
├── Problems/                  # Your algorithm implementations
│   ├── Binary_Search/
│   │   ├── Binary_Search.java
│   │   ├── First_Bad_Version.java
│   │   └── Search_And_Insert.java
│   └── Other_Categories/
│       └── ...
└── Practices/                 # Your practice implementations
    ├── Binary_Search/
    │   └── Binary_Search_Practice.java
    └── Other_Categories/
        └── ...
⚡ Quick Start
1. Creating a New Problem
Create a new Java class inside the appropriate category and annotate it using @Problem:

java
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
2. Creating a Practice Implementation
Create a corresponding practice class to test your knowledge:

java
package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.Base_Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.Binary_Search;

public class Binary_Search_Practice extends Base_Practice<Integer, Binary_Search> {

    public Binary_Search_Practice(Binary_Search problem) {
        super(problem);
    }

    @Override
    protected Integer practice() {
        // Your implementation to test your knowledge
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
3. Running the Application
Compile and run:

bash
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
Or simply run Run_Problems from your IDE.

🧭 Using the Interactive Menu
When started, the program displays:

text
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
Problems vs Practices
Problems: View the original solution implementations

Practices: Test your knowledge by solving problems independently, then compare your results with the original solution

Option 1-3: Problem Management
List All Problems:

text
=== ALL PROBLEMS ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
 3. SearchInsert              [Binary Search]
 4. TwoSum                    [Arrays]
List Problems by Category:

text
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion
 3. SearchInsert

--- Arrays ---
 4. TwoSum
 5. ContainerWithMostWater
Run Specific Problem:
You can enter variations like:

binarysearch

binary search

BinarySearch

binary-search

binary_search

Example:

text
=== RUN SPECIFIC PROBLEM ===
Enter problem name: binary search

=== Running Binary Search ===
Solution: 3
Option 4-6: Practice Management
List All Practices:

text
=== ALL PRACTICES ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
List Practices by Category:

text
--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion

--- Arrays ---
 3. TwoSum
Run Specific Practice:

text
=== RUN SPECIFIC PRACTICE ===
Enter practice name: binary search

=== Practicing Binary Search ===
Your answer: 3
Expected answer: 3
Result: ✅ CORRECT
If your answer differs:

text
=== Practicing Binary Search ===
Your answer: 2
Expected answer: 3
Result: ❌ INCORRECT
Error Handling
If a problem/practice is not found:

text
❌ Problem not found: binariserch

📋 Available problems:
   1. Binary Search
   2. First Bad Version
   3. Search And Insert

🔍 Quick reference: Binary Search, First Bad Version, Search And Insert
🏷️ Problem Annotation Reference
java
@Problem(
    name = "MeaningfulName",         // Required: unique identifier
    category = "Algorithm Category", // Required: used for grouping
    description = "Optional details" // Optional
)
➕ Adding New Categories
Create a new folder under DataStructureAndAlgorithms.Problems/

Add your problem classes inside it

Create corresponding practice classes in DataStructureAndAlgorithms.Practices/

Annotate problems with @Problem

They will be automatically discovered at runtime

🌟 Features
Auto-Discovery — Just annotate and run

Flexible Input Matching — Supports many naming formats

Category Grouping — Clean, organized structure

Interactive UI — Easy console navigation

Knowledge Verification — Practice system tests your understanding

Automatic Comparison — Instantly see if your solution matches

Helpful Error Messages — Fix problems quickly

Extensible Architecture — Add problems and practices easily

🧩 Dependencies
Java 8+

Reflections library (configured in Maven)

❗ Troubleshooting
No problems are found?
Ensure they are inside the Problems package

Check the @Problem annotation

Confirm the class extends Base_Problem

No practices are found?
Ensure they are inside the Practices package

Confirm the class extends Base_Practice

Check that the constructor takes the corresponding problem type

Verify naming convention: ProblemName_Practice.java

Compilation errors?
Check classpath and dependencies

Ensure package declarations match folder structure

🤝 Contributing
To add new problems:

Create a new class in a category under Problems/

Extend Base_Problem

Implement solve()

Add a @Problem annotation

Create a practice class in Practices/

Extend Base_Practice

Implement practice() method

Test both implementations

🎯 Learning Strategy
Study: Review the problem solution in the Problems/ directory

Practice: Implement your own solution in the Practices/ directory

Verify: Use the practice runner to check your understanding

Repeat: Regularly practice to reinforce your knowledge

Happy coding! 🚀