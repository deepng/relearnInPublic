@echo off
REM Comprehensive TestNG test runner script
REM Usage: run-tests.bat [suite|class|method] [target] [options]

setlocal

REM Default values
set RUN_TYPE=
set TARGET=
set PARALLEL_THREADS=3
set OUTPUT_DIR=reports
set VERBOSE_LEVEL=1
set GROUPS=
set SUITE_FILE=TestSuite.xml

REM Parse command line arguments
:parse_args
if "%1"=="" goto :check_params
if "%1"=="suite" (
    set RUN_TYPE=suite
    shift
    goto :parse_args
)
if "%1"=="class" (
    set RUN_TYPE=class
    shift
    goto :parse_args
)
if "%1"=="method" (
    set RUN_TYPE=method
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
if "%1"=="-s" (
    set SUITE_FILE=%2
    shift
    shift
    goto :parse_args
)
if "%1"=="-h" goto :help
if "%TARGET%"=="" (
    set TARGET=%1
    shift
    goto :parse_args
)
shift
goto :parse_args

:check_params
if "%RUN_TYPE%"=="" goto :help
if "%TARGET%"=="" goto :help

:run_tests
echo Running TestNG %RUN_TYPE%: %TARGET%
echo Parallel Threads: %PARALLEL_THREADS%
echo Output Directory: %OUTPUT_DIR%
echo Verbose Level: %VERBOSE_LEVEL%
if not "%GROUPS%"=="" echo Groups: %GROUPS%
if "%RUN_TYPE%"=="suite" echo Suite File: %SUITE_FILE%
echo.

REM Clean previous reports
if exist "%OUTPUT_DIR%" rmdir /s /q "%OUTPUT_DIR%"

if "%RUN_TYPE%"=="suite" goto :run_suite
if "%RUN_TYPE%"=="class" goto :run_class
if "%RUN_TYPE%"=="method" goto :run_method

:run_suite
gradlew test -Dtest.single=TestSuite -Dtestng.suite.file=%SUITE_FILE% -Dtestng.thread.count=%PARALLEL_THREADS% -Dtestng.verbose=%VERBOSE_LEVEL%
goto :check_result

:run_class
REM Create temporary TestNG XML for single class
echo ^<?xml version="1.0" encoding="UTF-8"?^> > temp-class-test.xml
echo ^<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd"^> >> temp-class-test.xml
echo ^<suite name="SingleClassTest" parallel="methods" thread-count="%PARALLEL_THREADS%"^> >> temp-class-test.xml
echo     ^<test name="%TARGET%Test"^> >> temp-class-test.xml
if not "%GROUPS%"=="" (
    echo         ^<groups^> >> temp-class-test.xml
    echo             ^<run^> >> temp-class-test.xml
    echo                 ^<include name="%GROUPS%"^> >> temp-class-test.xml
    echo             ^</run^> >> temp-class-test.xml
    echo         ^</groups^> >> temp-class-test.xml
)
echo         ^<classes^> >> temp-class-test.xml
echo             ^<class name="%TARGET%"^> >> temp-class-test.xml
echo         ^</classes^> >> temp-class-test.xml
echo     ^</test^> >> temp-class-test.xml
echo ^</suite^> >> temp-class-test.xml

gradlew test -Dtest.single=temp-class-test -Dtestng.verbose=%VERBOSE_LEVEL%
del temp-class-test.xml
goto :check_result

:run_method
REM Split target into class and method
for /f "tokens=1,2 delims=." %%a in ("%TARGET%") do (
    set CLASS_NAME=%%a
    set METHOD_NAME=%%b
)

REM Create temporary TestNG XML for single method
echo ^<?xml version="1.0" encoding="UTF-8"?^> > temp-method-test.xml
echo ^<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd"^> >> temp-method-test.xml
echo ^<suite name="SingleMethodTest" parallel="methods" thread-count="%PARALLEL_THREADS%"^> >> temp-method-test.xml
echo     ^<test name="%METHOD_NAME%Test"^> >> temp-method-test.xml
if not "%GROUPS%"=="" (
    echo         ^<groups^> >> temp-method-test.xml
    echo             ^<run^> >> temp-class-test.xml
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

gradlew test -Dtest.single=temp-method-test -Dtestng.verbose=%VERBOSE_LEVEL%
del temp-method-test.xml
goto :check_result

:check_result
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
echo Comprehensive TestNG Test Runner
echo.
echo Usage: run-tests.bat [suite^|class^|method] [target] [options]
echo.
echo Run Types:
echo   suite         Run a TestNG suite file
echo   class         Run a specific test class
echo   method        Run a specific test method
echo.
echo Options:
echo   -t <number>   Parallel thread count (default: 3)
echo   -o <dir>      Output directory (default: reports)
echo   -v <level>    Verbose level 0-10 (default: 1)
echo   -g <groups>   Test groups to include (comma-separated)
echo   -s <file>     Suite file for suite runs (default: TestSuite.xml)
echo   -h            Show this help
echo.
echo Examples:
echo   run-tests.bat suite TestSuite.xml
echo   run-tests.bat class ee.testng.JuiceTest
echo   run-tests.bat method ee.testng.JuiceTest.loginAndPostProductReviewViaUiTest
echo   run-tests.bat class ee.testng.ApiTests -g load -t 5
echo   run-tests.bat suite TestSuite.xml -o custom-reports -v 2

:end
endlocal
