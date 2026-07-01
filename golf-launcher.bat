@echo off
:: Start your Java program in a window named "MyKeyListener"
start "MyKeyListener" java -jar "file path of GolfAim.jar"

:: Start SkyTrac and wait for it to close
start /wait "" "file path of your skytrac.exe file (usually under /Program Files)"

:: Force close ONLY the window titled "MyKeyListener"
taskkill /fi "WINDOWTITLE eq MyKeyListener*" /f