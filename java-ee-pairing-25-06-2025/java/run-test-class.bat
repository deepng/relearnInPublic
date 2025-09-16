@echo off
REM Script to run individual TestNG test classes from command line
REM Usage: run-test-class.bat [class-name] [options]

setlocal

REM Default values
set CLASS_NAME=
set PARALLEL_THREADS=3
set OUTPUT_DIR=reports
set VERBOSE_LEVEL=1
set GROUPS=

REM Parse command line arguments
:parse_args
if "%1"=="" goto :check_class
if "%1"=="-c" (
    set CLASS_NAME=%2
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
shift
goto :parse_args

:check_class
if "%CLASS_NAME%"=="" goto :help

:run_tests
echo Running TestNG Class: %CLASS_NAME%
echo Parallel Threads: %PARALLEL_THREADS%
echo Output Directory: %OUTPUT_DIR%
echo Verbose Level: %VERBOSE_LEVEL%
if not "%GROUPS%"=="" echo Groups: %GROUPS%
echo.

REM Clean previous reports
if exist "%OUTPUT_DIR%" rmdir /s /q "%OUTPUT_DIR%"

REM Create temporary TestNG XML for single class
echo ^<?xml version="1.0" encoding="UTF-8"?^> > temp-class-test.xml
echo ^<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd"^> >> temp-class-test.xml
echo ^<suite name="SingleClassTest" parallel="methods" thread-count="%PARALLEL_THREADS%"^> >> temp-class-test.xml
echo     ^<test name="%CLASS_NAME%Test"^> >> temp-class-test.xml
if not "%GROUPS%"=="" (
    echo         ^<groups^> >> temp-class-test.xml
    echo             ^<run^> >> temp-class-test.xml
    echo                 ^<include name="%GROUPS%"^> >> temp-class-test.xml
    echo             ^</run^> >> temp-class-test.xml
    echo         ^</groups^> >> temp-class-test.xml
)
echo         ^<classes^> >> temp-class-test.xml
echo             ^<class name="%CLASS_NAME%"^> >> temp-class-test.xml
echo         ^</classes^> >> temp-class-test.xml
echo     ^</test^> >> temp-class-test.xml
echo ^</suite^> >> temp-class-test.xml

REM Run the tests using Gradle
gradlew test -Dtest.single=temp-class-test -Dtestng.verbose=%VERBOSE_LEVEL%

REM Clean up temporary file
del temp-class-test.xml

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
echo TestNG Class Runner
echo.
echo Usage: run-test-class.bat [class-name] [options]
echo.
echo Options:
echo   -c <class>    TestNG class name (e.g., ee.testng.JuiceTest)
echo   -t <number>   Parallel thread count (default: 3)
echo   -o <dir>      Output directory (default: reports)
echo   -v <level>    Verbose level 0-10 (default: 1)
echo   -g <groups>   Test groups to include (comma-separated)
echo   -h            Show this help
echo.
echo Examples:
echo   run-test-class.bat ee.testng.JuiceTest
echo   run-test-class.bat -c ee.testng.ApiTests -t 5
echo   run-test-class.bat ee.testng.JuiceTest -g load
echo   run-test-class.bat -c ee.testng.ApiTests -g load -o custom-reports

:end
endlocal
