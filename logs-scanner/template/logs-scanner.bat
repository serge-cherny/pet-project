@echo off
java -cp ".;logs-scanner-1.0-SNAPSHOT-shadow.jar" -Dhadoop.home.dir="%cd%\winutils" logs.scanner.Main
pause