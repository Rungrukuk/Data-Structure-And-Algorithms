#!/bin/bash

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files in the src directory and its subdirectories, and write the class files to bin
find src/DataStructureAndAlgorithms -name "*.java" -exec javac -d bin {} +

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Run the compiled Java program from bin directory
    java -cp bin DataStructureAndAlgorithms.Run_Solutions
else
    echo "Compilation failed!"
fi
