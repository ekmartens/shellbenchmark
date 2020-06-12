/*
*File: ClassLoader.java
*Author: Emily McPherson
*Date: 6/12/2020
*Purpose: Loader class to warm up JVM for benchmarking
*Reference:
baeldung. (2018). How to Warm Up the JVM. Baeldung. Retrieved from https://www.baeldung.com/java-jvm-warmup
 */

public class ClassLoader {
 protected static void load() {
     for (int i = 0; i < 100000; i++) {
         Warmup warm = new Warmup();
         warm.getWarm();
     }
 }
}//end ClassLoader
