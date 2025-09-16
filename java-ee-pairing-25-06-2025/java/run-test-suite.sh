#!/bin/bash
# Script to run TestNG test suites from command line
# Usage: ./run-test-suite.sh [suite-file] [options]

# Default values
SUITE_FILE="TestSuite.xml"
PARALLEL_THREADS=3
OUTPUT_DIR="reports"
VERBOSE_LEVEL=1

# Function to show help
show_help() {
    echo "TestNG Suite Runner"
    echo ""
    echo "Usage: $0 [options]"
    echo ""
    echo "Options:"
    echo "  -s <file>     TestNG suite file (default: TestSuite.xml)"
    echo "  -t <number>   Parallel thread count (default: 3)"
    echo "  -o <dir>      Output directory (default: reports)"
    echo "  -v <level>    Verbose level 0-10 (default: 1)"
    echo "  -h            Show this help"
    echo ""
    echo "Examples:"
    echo "  $0"
    echo "  $0 -s TestSuite.xml -t 5"
    echo "  $0 -o custom-reports -v 2"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -s)
            SUITE_FILE="$2"
            shift 2
            ;;
        -t)
            PARALLEL_THREADS="$2"
            shift 2
            ;;
        -o)
            OUTPUT_DIR="$2"
            shift 2
            ;;
        -v)
            VERBOSE_LEVEL="$2"
            shift 2
            ;;
        -h)
            show_help
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

echo "Running TestNG Suite: $SUITE_FILE"
echo "Parallel Threads: $PARALLEL_THREADS"
echo "Output Directory: $OUTPUT_DIR"
echo "Verbose Level: $VERBOSE_LEVEL"
echo ""

# Clean previous reports
if [ -d "$OUTPUT_DIR" ]; then
    rm -rf "$OUTPUT_DIR"
fi

# Run the tests using Gradle
./gradlew test -Dtest.single=TestSuite -Dtestng.suite.file="$SUITE_FILE" -Dtestng.thread.count="$PARALLEL_THREADS" -Dtestng.verbose="$VERBOSE_LEVEL"

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Tests completed successfully!"
    echo "📊 Reports available in: $OUTPUT_DIR"
else
    echo ""
    echo "❌ Tests failed with exit code: $?"
fi
