#!/bin/bash

# Check Java
if type -p java 1>/dev/null
then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]
then
    _java="$JAVA_HOME/bin/java"
else
    echo "No java found"
    exit -1
fi

# Compile source code
javac -cp "." *.java

# Run WordCount
echo Executing WordCount program
java -classpath "." WordCount wc_input

# Run RunningMedian
echo Executing RunningMedian program
java -classpath "." RunningMedian wc_input
