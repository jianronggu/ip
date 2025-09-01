@ECHO OFF
SETLOCAL EnableExtensions

REM --- Hardcode JDK (edit if your IntelliJ path differs) ---
SET "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2\jbr"
SET "JAVAC=%JAVA_HOME%\bin\javac.exe"
SET "JAVA=%JAVA_HOME%\bin\java.exe"

REM --- Sanity check ---
IF NOT EXIST "%JAVAC%" GOTO :nojdk

REM --- Prep ---
IF NOT EXIST ..\bin MKDIR ..\bin
IF EXIST ACTUAL.TXT DEL ACTUAL.TXT

REM --- Compile ---
"%JAVAC%" -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF %ERRORLEVEL% NEQ 0 GOTO :buildfail

REM --- Run (use fully-qualified name if you added a package) ---
"%JAVA%" -classpath ..\bin labussy.Labussy < input.txt > ACTUAL.TXT
IF %ERRORLEVEL% NEQ 0 GOTO :runfail

REM --- Compare (fail on mismatch) ---
FC ACTUAL.TXT EXPECTED.TXT >NUL
IF %ERRORLEVEL% EQU 2 GOTO :cmpfail
IF %ERRORLEVEL% EQU 1 GOTO :diff

ECHO ********** TESTS PASSED **********
EXIT /B 0

:nojdk
ECHO ********** BUILD FAILURE **********
ECHO javac not found at "%JAVAC%". Check JAVA_HOME path.
EXIT /B 1

:buildfail
ECHO ********** BUILD FAILURE **********
EXIT /B 1

:runfail
ECHO ********** RUN FAILURE **********
EXIT /B 1

REM --- Normalize EXPECTED.TXT to Windows CRLF as EXPECTED.win ---
powershell -NoProfile -Command "Get-Content -LiteralPath 'EXPECTED.TXT' | Out-File -LiteralPath 'EXPECTED.win' -Encoding ascii"
IF %ERRORLEVEL% NEQ 0 GOTO :normfail

FC ACTUAL.TXT EXPECTED.win >NUL
IF %ERRORLEVEL% EQU 2 GOTO :cmpfail
IF %ERRORLEVEL% EQU 1 GOTO :diff

ECHO ********** TESTS PASSED **********
DEL /Q EXPECTED.win 2>NUL
GOTO :eof

:normfail
ECHO ********** NORMALIZE FAILURE **********
EXIT /B 1

:cmpfail
ECHO ********** TEST FAILURE: comparison error (missing file?) **********
DEL /Q EXPECTED.win 2>NUL
EXIT /B 1

:diff
ECHO Expected and actual outputs differ. Diff:
FC ACTUAL.TXT EXPECTED.TXT
ECHO ********** TEST FAILURE **********
DEL /Q EXPECTED.win 2>NUL
EXIT /B 1
