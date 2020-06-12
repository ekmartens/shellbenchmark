/*
*File: BenchmarkSorts.java
*Author: Emily McPherson
*Date: 6/10/2020
*Purpose: Program for benchamrking Shell Sort Algorithm
*/

public class BenchmarkSorts {

  public static void main(String[] args){
    //warmup
    ClassLoader warmer = new ClassLoader();
    warmer.load();
    //run sorting
    MySort test = new MySort();

    test.runSort();
    test.generateReport(1);
    test.generateReport(2);

  }//end main
}//end class
