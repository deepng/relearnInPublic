# TestNG Command Line Test Runners

This directory contains scripts to run TestNG tests from the command line with different levels of granularity.

## Available Scripts

### 1. Test Suite Runner
- **Windows**: `run-test-suite.bat`
- **Unix/Linux**: `run-test-suite.sh`

Runs complete TestNG test suites defined in XML files.

### 2. Test Class Runner
- **Windows**: `run-test-class.bat`
- **Unix/Linux**: `run-test-class.sh`

Runs individual test classes.

### 3. Test Method Runner
- **Windows**: `run-test-method.bat`
- **Unix/Linux**: `run-test-method.sh`

Runs individual test methods within a class.

### 4. Comprehensive Runner
- **Windows**: `run-tests.bat`
- **Unix/Linux**: `run-tests.sh`

Universal script that can run suites, classes, or methods based on parameters.

## Usage Examples

### Running Test Suites

```bash
# Windows
run-test-suite.bat
run-test-suite.bat -s TestSuite.xml -t 5
run-test-suite.bat -o custom-reports -v 2

# Unix/Linux
./run-test-suite.sh
./run-test-suite.sh -s TestSuite.xml -t 5
./run-test-suite.sh -o custom-reports -v 2
```

### Running Test Classes

```bash
# Windows
run-test-class.bat ee.testng.JuiceTest
run-test-class.bat -c ee.testng.ApiTests -t 5
run-test-class.bat ee.testng.JuiceTest -g load

# Unix/Linux
./run-test-class.sh ee.testng.JuiceTest
./run-test-class.sh -c ee.testng.ApiTests -t 5
./run-test-class.sh ee.testng.JuiceTest -g load
```

### Running Test Methods

```bash
# Windows
run-test-method.bat ee.testng.JuiceTest loginAndPostProductReviewViaUiTest
run-test-method.bat -c ee.testng.ApiTests -m loadTestProductList
run-test-method.bat ee.testng.JuiceTest loginAndPostProductReviewViaApiTest -g load

# Unix/Linux
./run-test-method.sh ee.testng.JuiceTest loginAndPostProductReviewViaUiTest
./run-test-method.sh -c ee.testng.ApiTests -m loadTestProductList
./run-test-method.sh ee.testng.JuiceTest loginAndPostProductReviewViaApiTest -g load
```

### Using the Comprehensive Runner

```bash
# Windows
run-tests.bat suite TestSuite.xml
run-tests.bat class ee.testng.JuiceTest
run-tests.bat method ee.testng.JuiceTest.loginAndPostProductReviewViaUiTest
run-tests.bat class ee.testng.ApiTests -g load -t 5

# Unix/Linux
./run-tests.sh suite TestSuite.xml
./run-tests.sh class ee.testng.JuiceTest
./run-tests.sh method ee.testng.JuiceTest.loginAndPostProductReviewViaUiTest
./run-tests.sh class ee.testng.ApiTests -g load -t 5
```

## Command Line Options

All scripts support the following options:

| Option | Description | Default |
|--------|-------------|---------|
| `-s <file>` | TestNG suite file (suite runner only) | `TestSuite.xml` |
| `-c <class>` | TestNG class name | Required for class/method runners |
| `-m <method>` | Test method name | Required for method runner |
| `-t <number>` | Parallel thread count | `3` (suite/class), `1` (method) |
| `-o <dir>` | Output directory for reports | `reports` |
| `-v <level>` | Verbose level (0-10) | `1` |
| `-g <groups>` | Test groups to include (comma-separated) | None |
| `-h` | Show help | - |

## Available Test Classes

Based on the project structure, the following test classes are available:

- `ee.testng.JuiceTest` - Main test class with UI and API tests
- `ee.testng.ApiTests` - API-specific tests with load testing

## Available Test Methods

### JuiceTest Class
- `loginAndPostProductReviewViaUiTest` - UI-based product review test
- `loginAndPostProductReviewViaApiTest` - API-based product review test

### ApiTests Class
- `loadTestProductList` - Load test for product list API (group: "load")

## Test Groups

The project uses the following test groups:
- `load` - Load testing group (used in ApiTests)

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle build tool
- TestNG dependencies (already configured in build.gradle)

## Output

All scripts generate:
- HTML test reports in the specified output directory
- Console output with test execution status
- Screenshots for failed UI tests (in `screenshots/` directory)

## Troubleshooting

1. **Permission denied (Unix/Linux)**: Make scripts executable with `chmod +x script-name.sh`
2. **Class not found**: Ensure the project is compiled with `./gradlew build`
3. **Test failures**: Check the HTML reports in the output directory for detailed failure information
4. **Parallel execution issues**: Reduce thread count if experiencing resource conflicts

## Integration with CI/CD

These scripts can be easily integrated into CI/CD pipelines:

```yaml
# Example GitHub Actions step
- name: Run TestNG Tests
  run: |
    ./gradlew build
    ./run-tests.sh suite TestSuite.xml -t 5 -o test-reports
```

```bash
# Example Jenkins pipeline step
stage('Test') {
    steps {
        sh './gradlew build'
        sh './run-tests.sh class ee.testng.JuiceTest -o jenkins-reports'
    }
}
```
