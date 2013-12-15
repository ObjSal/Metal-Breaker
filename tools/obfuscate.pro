-injars       ../release/temp.jar
-outjars      Metal_Breaker.jar
-libraryjars  <java.home>/lib/rt.jar
-printmapping proguard.map
-ignorewarnings

-keep public class Main {
    public static void main(java.lang.String[]);
}
