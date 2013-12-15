echo Compilador generado por salvador.icc@gmail.com
rmdir /S/Q release
mkdir release
echo copiando archivos...
copy data\*.png release\
echo compilando...
javac src\*.java -d release\
if errorlevel 1 goto fin
rem javac src\*.java -d release\
cd release
echo Main-Class: Main>> manifest.mani
rem type manifest.mani
jar cmf manifest.mani temp.jar *
rem jar cmf manifest.mani temp.jar *
cd ..\tools
java -jar proguard.jar @obfuscate.pro
move Metal_Breaker.jar ..\release\
del ..\release\*.class
del ..\release\manifest.mani
rem del ..\release\temp.jar
del ..\release\*.png
cd..
cd release
java -jar Metal_Breaker.jar
cd..
:fin
pause