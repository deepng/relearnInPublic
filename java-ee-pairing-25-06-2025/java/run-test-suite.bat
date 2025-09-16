@echo off
REM Script to run TestNG test suites from command line
REM Usage: run-test-suite.bat [suite-file] [options]

setlocal

REM Default values
set SUITE_FILE=TestSuite.xml
set PARALLEL_THREADS=3
set OUTPUT_DIR=reports
set VERBOSE_LEVEL=1

REM Parse command line arguments
:parse_args
if "%1"=="" goto :run_tests
if "%1"=="-s" (
    set SUITE_FILE=%2
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
if "%1"=="-h" goto :help
shift
goto :parse_args

:run_tests
echo Running TestNG Suite: %SUITE_FILE%
echo Parallel Threads: %PARALLEL_THREADS%
echo Output Directory: %OUTPUT_DIR%
echo Verbose Level: %VERBOSE_LEVEL%
echo.

REM Clean previous reports
if exist "%OUTPUT_DIR%" rmdir /s /q "%OUTPUT_DIR%"

REM Run the tests using Gradle
gradlew test -Dtest.single=TestSuite -Dtestng.suite.file=%SUITE_FILE% -Dtestng.thread.count=%PARALLEL_THREADS% -Dtestng.verbose=%VERBOSE_LEVEL%

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
echo TestNG Suite Runner
echo.
echo Usage: run-test-suite.bat [options]
echo.
echo Options:
echo   -s <file>     TestNG suite file (default: TestSuite.xml)
echo   -t <number>   Parallel thread count (default: 3)
echo   -o <dir>      Output directory (default: reports)
echo   -v <level>    Verbose level 0-10 (default: 1)
echo   -h            Show this help
echo.
echo Examples:
echo   run-test-suite.bat
echo   run-test-suite.bat -s TestSuite.xml -t 5
echo   run-test-suite.bat -o custom-reports -v 2

:end
endlocal
