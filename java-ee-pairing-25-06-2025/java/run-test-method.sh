#!/bin/bash
# Script to run individual TestNG test methods from command line
# Usage: ./run-test-method.sh [class-name] [method-name] [options]

# Default values
CLASS_NAME=""
METHOD_NAME=""
PARALLEL_THREADS=1
OUTPUT_DIR="reports"
VERBOSE_LEVEL=1
GROUPS=""

# Function to show help
show_help() {
    echo "TestNG Method Runner"
    echo ""
    echo "Usage: $0 [class-name] [method-name] [options]"
    echo ""
    echo "Options:"
    echo "  -c <class>    TestNG class name (e.g., ee.testng.JuiceTest)"
    echo "  -m <method>   Test method name (e.g., loginAndPostProductReviewViaUiTest)"
    echo "  -t <number>   Parallel thread count (default: 1)"
    echo "  -o <dir>      Output directory (default: reports)"
    echo "  -v <level>    Verbose level 0-10 (default: 1)"
    echo "  -g <groups>   Test groups to include (comma-separated)"
    echo "  -h            Show this help"
    echo ""
    echo "Examples:"
    echo "  $0 ee.testng.JuiceTest loginAndPostProductReviewViaUiTest"
    echo "  $0 -c ee.testng.ApiTests -m loadTestProductList"
    echo "  $0 ee.testng.JuiceTest loginAndPostProductReviewViaApiTest -g load"
    echo "  $0 -c ee.testng.ApiTests -m loadTestProductList -o custom-reports"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -c)
            CLASS_NAME="$2"
            shift 2
            ;;
        -m)
            METHOD_NAME="$2"
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
            elif [ -z "$METHOD_NAME" ]; then
                METHOD_NAME="$1"
            else
                echo "Unknown option: $1"
                show_help
                exit 1
            fi
            shift
            ;;
    esac
done

# Check if both class name and method name are provided
if [ -z "$CLASS_NAME" ] || [ -z "$METHOD_NAME" ]; then
    echo "Error: Both class name and method name are required"
    show_help
    exit 1
fi

echo "Running TestNG Method: $CLASS_NAME.$METHOD_NAME"
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

# Create temporary TestNG XML for single method
cat > temp-method-test.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="SingleMethodTest" parallel="methods" thread-count="$PARALLEL_THREADS">
    <test name="${METHOD_NAME}Test">
EOF

if [ -n "$GROUPS" ]; then
    cat >> temp-method-test.xml << EOF
        <groups>
            <run>
                <include name="$GROUPS"/>
            </run>
        </groups>
EOF
fi

cat >> temp-method-test.xml << EOF
        <classes>
            <class name="$CLASS_NAME">
                <methods>
                    <include name="$METHOD_NAME"/>
                </methods>
            </class>
        </classes>
    </test>
</suite>
EOF

# Run the tests using Gradle
./gradlew test -Dtest.single=temp-method-test -Dtestng.verbose="$VERBOSE_LEVEL"

# Clean up temporary file
rm -f temp-method-test.xml

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Tests completed successfully!"
    echo "📊 Reports available in: $OUTPUT_DIR"
else
    echo ""
    echo "❌ Tests failed with exit code: $?"
fi
