#!/bin/bash
# Script to run individual TestNG test classes from command line
# Usage: ./run-test-class.sh [class-name] [options]

# Default values
CLASS_NAME=""
PARALLEL_THREADS=3
OUTPUT_DIR="reports"
VERBOSE_LEVEL=1
GROUPS=""

# Function to show help
show_help() {
    echo "TestNG Class Runner"
    echo ""
    echo "Usage: $0 [class-name] [options]"
    echo ""
    echo "Options:"
    echo "  -c <class>    TestNG class name (e.g., ee.testng.JuiceTest)"
    echo "  -t <number>   Parallel thread count (default: 3)"
    echo "  -o <dir>      Output directory (default: reports)"
    echo "  -v <level>    Verbose level 0-10 (default: 1)"
    echo "  -g <groups>   Test groups to include (comma-separated)"
    echo "  -h            Show this help"
    echo ""
    echo "Examples:"
    echo "  $0 ee.testng.JuiceTest"
    echo "  $0 -c ee.testng.ApiTests -t 5"
    echo "  $0 ee.testng.JuiceTest -g load"
    echo "  $0 -c ee.testng.ApiTests -g load -o custom-reports"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -c)
            CLASS_NAME="$2"
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
        -g)
            GROUPS="$2"
            shift 2
            ;;
        -h)
            show_help
            exit 0
            ;;
        *)
            if [ -z "$CLASS_NAME" ]; then
                CLASS_NAME="$1"
            else
                echo "Unknown option: $1"
                show_help
                exit 1
            fi
            shift
            ;;
    esac
done

# Check if class name is provided
if [ -z "$CLASS_NAME" ]; then
    echo "Error: Class name is required"
    show_help
    exit 1
fi

echo "Running TestNG Class: $CLASS_NAME"
echo "Parallel Threads: $PARALLEL_THREADS"
echo "Output Directory: $OUTPUT_DIR"
echo "Verbose Level: $VERBOSE_LEVEL"
if [ -n "$GROUPS" ]; then
    echo "Groups: $GROUPS"
fi
echo ""

# Clean previous reports
if [ -d "$OUTPUT_DIR" ]; then
    rm -rf "$OUTPUT_DIR"
fi

# Create temporary TestNG XML for single class
cat > temp-class-test.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="SingleClassTest" parallel="methods" thread-count="$PARALLEL_THREADS">
    <test name="${CLASS_NAME}Test">
EOF

if [ -n "$GROUPS" ]; then
    cat >> temp-class-test.xml << EOF
        <groups>
            <run>
                <include name="$GROUPS"/>
            </run>
        </groups>
EOF
fi

cat >> temp-class-test.xml << EOF
        <classes>
            <class name="$CLASS_NAME"/>
        </classes>
    </test>
</suite>
EOF

# Run the tests using Gradle
./gradlew test -Dtest.single=temp-class-test -Dtestng.verbose="$VERBOSE_LEVEL"

# Clean up temporary file
rm -f temp-class-test.xml

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Tests completed successfully!"
    echo "📊 Reports available in: $OUTPUT_DIR"
else
    echo ""
    echo "❌ Tests failed with exit code: $?"
fi
