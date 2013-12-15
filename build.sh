echo Compilador generado por salvador.icc@gmail.com
rm -dr release
mkdir release
echo copiando archivos...
cp data/* release/
echo compilando...
javac src/*.java -d release/
cd release
echo Main-Class: Main>> manifest.mani
jar cmf manifest.mani temp.jar *
cd ../tools
java -jar proguard.jar @obfuscate.pro
mv Metal_Breaker.jar ../release/
rm ../release/*.class
rm ../release/manifest.mani
rm ../release/temp.jar
rm ../release/*.png
cd ..
cd release
java -jar Metal_Breaker.jar
