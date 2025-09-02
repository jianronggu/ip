@ECHO OFF
SETLOCAL EnableExtensions

REM --- JDK: use JAVA_HOME if set, else IntelliJ's bundled JBR ---
IF NOT DEFINED JAVA_HOME SET "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2\jbr"
SET "JAVAC=%JAVA_HOME%\bin\javac.exe"
SET "JAVA=%JAVA_HOME%\bin\java.exe"

REM --- Sanity check ---
IF NOT EXIST "%JAVAC%" (
  ECHO ********** BUILD FAILURE **********
  ECHO javac not found at "%JAVAC%". Set JAVA_HOME to a JDK or adjust the path above.
  EXIT /B 1
)

REM --- Prep ---
IF NOT EXIST ..\bin MKDIR ..\bin
IF EXIST ACTUAL.TXT DEL /Q ACTUAL.TXT
IF EXIST ACTUAL.win DEL /Q ACTUAL.win
IF EXIST EXPECTED.win DEL /Q EXPECTED.win

REM --- Compile sources into bin ---
"%JAVAC%" -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
  ECHO ********** BUILD FAILURE **********
  EXIT /B 1
)

REM --- Run program (use FQN if your main class is in a package) ---
"%JAVA%" -classpath ..\src\main\java FQN < input.txt > ACTUAL.TXT
IF ERRORLEVEL 1 (
  ECHO ********** RUN FAILURE **********
  EXIT /B 1
)

REM --- Normalize line endings to CRLF using MORE (no external tools needed) ---
MORE < ACTUAL.TXT   > ACTUAL.win
MORE < EXPECTED.TXT > EXPECTED.win

REM --- Compare; fail on mismatch ---
FC ACTUAL.win EXPECTED.win >NUL
IF ERRORLEVEL 2 (
  ECHO ********** TEST FAILURE: comparison error (missing file?) **********
  EXIT /B 1
)
IF ERRORLEVEL 1 (
  ECHO Expected and actual outputs differ. Diff with line numbers:
  FC /N ACTUAL.win EXPECTED.win
  ECHO ********** TEST FAILURE **********
  EXIT /B 1
)

ECHO ********** TESTS PASSED **********
EXIT /B 0
