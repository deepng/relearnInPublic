@echo off
REM Script to run individual TestNG test methods from command line
REM Usage: run-test-method.bat [class-name] [method-name] [options]

setlocal

REM Default values
set CLASS_NAME=
set METHOD_NAME=
set PARALLEL_THREADS=1
set OUTPUT_DIR=reports
set VERBOSE_LEVEL=1
set GROUPS=

REM Parse command line arguments
:parse_args
if "%1"=="" goto :check_params
if "%1"=="-c" (
    set CLASS_NAME=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-m" (
    set METHOD_NAME=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-t" (
    set PARALLEL_THREADS=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-o" (
    set OUTPUT_DIR=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-v" (
    set VERBOSE_LEVEL=%2
    shift
    goto :parse_args
)
if "%1"=="-g" (
    set GROUPS=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-h" goto :help
if "%CLASS_NAME%"=="" (
    set CLASS_NAME=%1
    shift
    goto :parse_args
)
if "%METHOD_NAME%"=="" (
    set METHOD_NAME=%1
    shift
    goto :parse_args
)
shift
goto :parse_args

:check_params
if "%CLASS_NAME%"=="" goto :help
if "%METHOD_NAME%"=="" goto :help

:run_tests
echo Running TestNG Method: %CLASS_NAME%.%METHOD_NAME%
echo Parallel Threads: %PARALLEL_THREADS%
echo Output Directory: %OUTPUT_DIR%
echo Verbose Level: %VERBOSE_LEVEL%
if not "%GROUPS%"=="" echo Groups: %GROUPS%
echo.

REM Clean previous reports
if exist "%OUTPUT_DIR%" rmdir /s /q "%OUTPUT_DIR%"

REM Create temporary TestNG XML for single method
echo ^<?xml version="1.0" encoding="UTF-8"?^> > temp-method-test.xml
echo ^<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd"^> >> temp-method-test.xml
echo ^<suite name="SingleMethodTest" parallel="methods" thread-count="%PARALLEL_THREADS%"^> >> temp-method-test.xml
echo     ^<test name="%METHOD_NAME%Test"^> >> temp-method-test.xml
if not "%GROUPS%"=="" (
    echo         ^<groups^> >> temp-method-test.xml
    echo             ^<run^> >> temp-method-test.xml
    echo                 ^<include name="%GROUPS%"^> >> temp-method-test.xml
    echo             ^</run^> >> temp-method-test.xml
    echo         ^</groups^> >> temp-method-test.xml
)
echo         ^<classes^> >> temp-method-test.xml
echo             ^<class name="%CLASS_NAME%"^> >> temp-method-test.xml
echo                 ^<methods^> >> temp-method-test.xml
echo                     ^<include name="%METHOD_NAME%"^> >> temp-method-test.xml
echo                 ^</methods^> >> temp-method-test.xml
echo             ^</class^> >> temp-method-test.xml
echo         ^</classes^> >> temp-method-test.xml
echo     ^</test^> >> temp-method-test.xml
echo ^</suite^> >> temp-method-test.xml

REM Run the tests using Gradle
gradlew test -Dtest.single=temp-method-test -Dtestng.verbose=%VERBOSE_LEVEL%

REM Clean up temporary file
del temp-method-test.xml

REM Check if tests passed
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Tests completed successfully!
    echo 📊 Reports available in: %OUTPUT_DIR%
) else (
    echo.
    echo ❌ Tests failed with exit code: %ERRORLEVEL%
)

goto :end

:help
echo TestNG Method Runner
echo.
echo Usage: run-test-method.bat [class-name] [method-name] [options]
echo.
echo Options:
echo   -c <class>    TestNG class name (e.g., ee.testng.JuiceTest)
echo   -m <method>   Test method name (e.g., loginAndPostProductReviewViaUiTest)
echo   -t <number>   Parallel thread count (default: 1)
echo   -o <dir>      Output directory (default: reports)
echo   -v <level>    Verbose level 0-10 (default: 1)
echo   -g <groups>   Test groups to include (comma-separated)
echo   -h            Show this help
echo.
echo Examples:
echo   run-test-method.bat ee.testng.JuiceTest loginAndPostProductReviewViaUiTest
echo   run-test-method.bat -c ee.testng.ApiTests -m loadTestProductList
echo   run-test-method.bat ee.testng.JuiceTest loginAndPostProductReviewViaApiTest -g load
echo   run-test-method.bat -c ee.testng.ApiTests -m loadTestProductList -o custom-reports

:end
endlocal
