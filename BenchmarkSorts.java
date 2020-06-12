public class BenchmarkSorts {

  public static void main(String[] args){
    MySort test = new MySort();
    long[] testData = new long[]{1, 5, 6, 8, 10, 40, 65, 88};
    int[] t2 = new int[]{4, 9, 5, 12, 3, 2, 15, 18, 22, 10, 2, 1};

    test.runSort();
    test.generateReport(1);
    test.generateReport(2);

  }//end main
}//end class
