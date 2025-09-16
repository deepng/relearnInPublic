#!/bin/bash
# Comprehensive TestNG test runner script
# Usage: ./run-tests.sh [suite|class|method] [target] [options]

# Default values
RUN_TYPE=""
TARGET=""
PARALLEL_THREADS=3
OUTPUT_DIR="reports"
VERBOSE_LEVEL=1
GROUPS=""
SUITE_FILE="TestSuite.xml"

# Function to show help
show_help() {
    echo "Comprehensive TestNG Test Runner"
    echo ""
    echo "Usage: $0 [suite|class|method] [target] [options]"
    echo ""
    echo "Run Types:"
    echo "  suite         Run a TestNG suite file"
    echo "  class         Run a specific test class"
    echo "  method        Run a specific test method"
    echo ""
    echo "Options:"
    echo "  -t <number>   Parallel thread count (default: 3)"
    echo "  -o <dir>      Output directory (default: reports)"
    echo "  -v <level>    Verbose level 0-10 (default: 1)"
    echo "  -g <groups>   Test groups to include (comma-separated)"
    echo "  -s <file>     Suite file for suite runs (default: TestSuite.xml)"
    echo "  -h            Show this help"
    echo ""
    echo "Examples:"
    echo "  $0 suite TestSuite.xml"
    echo "  $0 class ee.testng.JuiceTest"
    echo "  $0 method ee.testng.JuiceTest.loginAndPostProductReviewViaUiTest"
    echo "  $0 class ee.testng.ApiTests -g load -t 5"
    echo "  $0 suite TestSuite.xml -o custom-reports -v 2"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        suite|class|method)
            RUN_TYPE="$1"
            shift
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
        -s)
            SUITE_FILE="$2"
            shift 2
            ;;
        -h)
            show_help
            exit 0
            ;;
        *)
            if [ -z "$TARGET" ]; then
                TARGET="$1"
            else
                echo "Unknown option: $1"
                show_help
                exit 1
            fi
            shift
            ;;
    esac
done

# Check if run type and target are provided
if [ -z "$RUN_TYPE" ] || [ -z "$TARGET" ]; then
    echo "Error: Both run type and target are required"
    show_help
    exit 1
fi

echo "Running TestNG $RUN_TYPE: $TARGET"
echo "Parallel Threads: $PARALLEL_THREADS"
echo "Output Directory: $OUTPUT_DIR"
echo "Verbose Level: $VERBOSE_LEVEL"
if [ -n "$GROUPS" ]; then
    echo "Groups: $GROUPS"
fi
if [ "$RUN_TYPE" = "suite" ]; then
    echo "Suite File: $SUITE_FILE"
fi
echo ""

# Clean previous reports
if [ -d "$OUTPUT_DIR" ]; then
    rm -rf "$OUTPUT_DIR"
fi

# Run tests based on type
case $RUN_TYPE in
    suite)
        ./gradlew test -Dtest.single=TestSuite -Dtestng.suite.file="$SUITE_FILE" -Dtestng.thread.count="$PARALLEL_THREADS" -Dtestng.verbose="$VERBOSE_LEVEL"
        ;;
    class)
        # Create temporary TestNG XML for single class
        cat > temp-class-test.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="SingleClassTest" parallel="methods" thread-count="$PARALLEL_THREADS">
    <test name="${TARGET}Test">
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
            <class name="$TARGET"/>
        </classes>
    </test>
</suite>
EOF
        ./gradlew test -Dtest.single=temp-class-test -Dtestng.verbose="$VERBOSE_LEVEL"
        rm -f temp-class-test.xml
        ;;
    method)
        # Split target into class and method
        IFS='.' read -r CLASS_NAME METHOD_NAME <<< "$TARGET"
        
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
        ./gradlew test -Dtest.single=temp-method-test -Dtestng.verbose="$VERBOSE_LEVEL"
        rm -f temp-method-test.xml
        ;;
esac

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Tests completed successfully!"
    echo "📊 Reports available in: $OUTPUT_DIR"
else
    echo ""
    echo "❌ Tests failed with exit code: $?"
fi
