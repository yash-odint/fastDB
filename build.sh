#!/bin/bash
set -e

# Clean old builds
rm -rf out
mkdir out

# Compile all Java files
javac -d out $(find Main.java)

# Run main server
java -cp out Main
