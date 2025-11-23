Data Structures & Algorithms Problem Runner
A Java-based application that helps you practice and run various data structure and algorithm problems with an interactive menu system.

Overview
This project provides a flexible framework for:

Organizing algorithm problems by category

Automatically discovering new problems using reflection

Running problems through an interactive console interface

Testing your solutions against expected outputs

Project Structure
text
src/main/java/DataStructureAndAlgorithms/
├── Base_Problem.java          # Base class for all problems
├── Base_Test.java             # Base class for tests (optional)
├── Problem.java               # Annotation for auto-discovery
├── Problem_Manager.java       # Manages problem discovery and execution
├── Run_Problems.java          # Main runner with interactive menu
└── Problems/                  # Problem implementations
    ├── Binary_Search/
    │   ├── Binary_Search.java
    │   ├── First_Bad_Version.java
    │   └── Search_And_Insert.java
    └── Other_Categories/
        └── ...
Quick Start
1. Creating a New Problem
Create a new Java class in the appropriate category package and annotate it with @Problem:

java
package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.Base_Problem;
import DataStructureAndAlgorithms.Problem;

@Problem(name = "BinarySearch", category = "Binary Search")
public class Binary_Search extends Base_Problem<Integer> {
    // Problem data
    public final int nums[] = new int[] { -1, 0, 3, 5, 9, 12 };
    public final int target = 5;

    @Override
    protected Integer solve() {
        // Your solution implementation
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
2. Running the Application
Run the Run_Problems class:

bash
cd src/main/java
javac DataStructureAndAlgorithms/Run_Problems.java
java DataStructureAndAlgorithms.Run_Problems
Or run through your IDE by executing the Run_Problems class.

Using the Interactive Menu
When you run the application, you'll see:

text
=========================================
    DATA STRUCTURES & ALGORITHMS
          PROBLEM RUNNER
=========================================

=== MAIN MENU ===
1. List all problems
2. List problems by category
3. Run a specific problem
4. Exit
Option 1: List All Problems
Shows all available problems in a numbered list with their categories:

text
=== ALL PROBLEMS ===
 1. BinarySearch              [Binary Search]
 2. FirstBadVersion           [Binary Search]
 3. SearchInsert              [Binary Search]
 4. TwoSum                    [Arrays]

Enter problem number to run (0 to go back): 1

=== Running Binary Search ===
Answer: 3

Press Enter to continue...
Option 2: List Problems by Category
Groups problems by their categories for better organization:

text
=== PROBLEMS BY CATEGORY ===

--- Binary Search ---
 1. BinarySearch
 2. FirstBadVersion
 3. SearchInsert

--- Arrays ---
 4. TwoSum
 5. ContainerWithMostWater
Option 3: Run Specific Problem
Run a problem by entering its name (flexible input supported):

Supported Input Formats:

binarysearch

BiNaryseaRCH

binary search

Binary Search

binary-search

binary_search

Example:

text
=== RUN SPECIFIC PROBLEM ===
Enter problem name: binary search

=== Running Binary Search ===
Answer: 3

Press Enter to continue...
If the problem name isn't found, you'll see available options:

text
❌ Problem not found: binariserch

📋 Available problems:
   1. Binary Search
   2. First Bad Version
   3. Search And Insert

🔍 Quick reference: Binary Search, First Bad Version, Search And Insert
Creating Test Cases (Optional)
You can create test classes to verify your solutions:

java
@Problem(name = "BinarySearchTest", category = "Tests")
public class Binary_Search_Test extends Base_Test<Integer, Integer> {
    
    @Override
    protected Integer test() {
        // Your test implementation
        return yourSolution();
    }
    
    @Override
    protected Integer expected() {
        // Expected result
        return 3;
    }
    
    @Override
    protected boolean compare() {
        return test().equals(expected());
    }
}
Problem Annotation Options
The @Problem annotation supports:

java
@Problem(
    name = "MeaningfulName",        // Required: Problem identifier
    category = "Algorithm Category", // Required: Organizes problems
    description = "Optional description" // Optional: Problem description
)
Adding New Problem Categories
Create a new package under DataStructureAndAlgorithms.Problems/

Add your problem classes in that package

Annotate with @Problem and appropriate category

The system will automatically discover it on next run

Features
Auto-Discovery: No manual registration needed - just annotate and run

Flexible Input: Accepts various naming formats (camelCase, spaced, etc.)

Category Organization: Problems are grouped by algorithm type

Interactive Interface: Easy-to-use console menu

Error Handling: Clear error messages with suggestions

Extensible: Easy to add new problems and categories

Dependencies
Java 8 or higher

Reflections library (included in Maven pom.xml)

Troubleshooting
No problems found?

Ensure your class is in DataStructureAndAlgorithms.Problems package or subpackage

Verify the @Problem annotation is present

Check that the class extends Base_Problem

Compilation issues?

Verify all dependencies are in classpath

Check package declarations match directory structure

Contributing
To add new problems:

Create a new class in appropriate category package

Extend Base_Problem and implement solve() method

Annotate with @Problem with unique name and category

Test your implementation

Happy coding! 🚀