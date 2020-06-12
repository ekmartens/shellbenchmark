/*
*File: MySort.java
*Author: Emily McPherson
*Date: 6/11/2020
*Purpose: Class to perform sorting via Shell Sort, count critical operations, test 10 data lengths with 50 runs each, and generate reports on averaged results and coefficients of variance
*/

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MySort implements SortInterface {
  private int[] dataSizes = new int[]{100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
  private double[] iterCounts = new double[10];//stores average critical counts for each val of n iterative
  private double[] recurCounts = new double[10];//stores average critcal counts for each val of n recursive
  private double[] iterTimes = new double[10]; //stores average runtimes of 50 iterative runs per value of n
  private double[] recurTimes = new double[10];//stores average runtimes of 50 recursive runs per value of n
  //stores Coefficient of Variance for both algorithms' Times and Counts
  private double[] iterTimeCoeff = new double[10];
  private double[] recurTimeCoeff = new double[10];
  private double[] iterCountCoeff = new double[10];
  private double[] recurCountCoeff = new double[10];
  //provides global access for counting operations
  private int tempCount = 0;

public void runSort(){
  Random rand = new Random();
  int max = 1000;
  //for each value of n:
  for (int a = 0; a < this.dataSizes.length; a++){
    //track average time for both algorithms over 50 runs
    long[] recTimesForN = new long[50];
    long[] itTimesForN = new long[50];
    long trackTimeRec = 0;
    long trackTimeIt = 0;
    //track average critical operations for both algorthims over 50 runs.
    long[] recCountsForN = new long[50];
    long[] itCountsForN = new long[50];
    int trackCountRec = 0;
    int trackCountIt = 0;

    int n = dataSizes[a];
    //run 50 data sets
    for (int i = 0; i < 50; i++){
      int[] temp = new int[n];
      for (int m = 0; m < n; m++){
        temp[m] = rand.nextInt(max);
      }

      this.tempCount = 0;
      long start = getTime();
      int[] recSorted = recursiveSort(temp, temp.length/2);
      long end = getTime();
      long runTime = end - start;

      //Check to see if array is sorted
      try {
        checkSort(recSorted);
      } catch(UnsortedException ue){
        System.out.println("Recursive Sort Unsuccessful - Data Length: " + n + ", Attempt: " + i);
      }

      recTimesForN[i] = runTime;
      recCountsForN[i] = getCount();
      trackTimeRec += runTime;
      trackCountRec += getCount();
      this.tempCount = 0;
      start = getTime();
      int[] itSorted = iterativeSort(temp);
      end = getTime();
      //Check to see if array is sorted
      try {
        checkSort(itSorted);
      } catch(UnsortedException ue){
        System.out.println("Iterative Sort Unsuccessful - Data Length: " + n + ", Attempt: " + i);
      }
      runTime = end - start;
      itTimesForN[i] = runTime;
      itCountsForN[i] = getCount();
      trackTimeIt += runTime;
      trackCountIt += getCount();
    }// end 50 runs
    //calculate average run times over 50 runs
    double avgTimeRec = trackTimeRec/50.0;
    double avgTimeIt = trackTimeIt/50.0;
    //calculate average critical operations over 50 runs
    double avgCountRec = trackCountRec/50.0;
    double avgCountIt = trackCountIt/50.0;
    //store average times and average crtic. ops in class variables
    this.iterTimes[a] = avgTimeIt;
    this.recurTimes[a] = avgTimeRec;
    this.iterCounts[a] = avgCountIt;
    this.recurCounts[a] = avgCountRec;
    this.recurTimeCoeff[a] = avgCoef(recTimesForN);
    this.iterTimeCoeff[a] = avgCoef(itTimesForN);
    this.iterCountCoeff[a] = avgCoef(itCountsForN);
    this.recurCountCoeff[a] = avgCoef(recCountsForN);
  }//end Data Sizes
}

public double avgCoef(long[] counts){
  //Part 1: find standard deviation:
  //P1 Step 1: find μ
  double mean = 0.0;
  for (int i = 0; i < counts.length; i++){
    mean += Double.valueOf(counts[i]);
  }
  mean /= counts.length;
  //System.out.println(mean);
  //P1 Step 2: find sum of (x_i - μ)^2 / N
  double sdSum = 0.0;
  for (int i = 0; i < counts.length; i++){
    sdSum += Math.pow((Double.valueOf(counts[i]) - mean), 2);
  }
  sdSum /= counts.length;
  //System.out.println(sdSum);
  //P1 Step 3: Find square root of above steps
  sdSum = Math.sqrt(sdSum);
  //System.out.println(sdSum);

  //Part 2: Find Coefficient of Variant to nearest 100th
  sdSum = (sdSum/mean)*100;
  double coeffVar = Math.round(sdSum*100)/100.0;
  //System.out.println(coeffVar);
  return coeffVar;
}//end avgCoef()

//create text files with results
public void generateReport(int option){
  if (option == 1){
    try {
        File iterativeReport = new File("iterativeResults.txt");
        FileWriter wtr = new FileWriter("iterativeResults.txt");
        for (int i = 0; i < this.dataSizes.length; i++){
          wtr.write(this.dataSizes[i] + " ");
          wtr.write(this.iterCounts[i] + " ");
          wtr.write(this.iterCountCoeff[i] + " ");
          wtr.write(this.iterTimes[i] + " ");
          wtr.write(this.iterTimeCoeff[i] + " \n");
        }
        wtr.close();
      } catch (IOException e){
        System.out.println("IOException occurred");
        e.printStackTrace();
      }
  }
  else if (option == 2){
    try {
        File iterativeReport = new File("recursiveResults.txt");
        FileWriter wtr = new FileWriter("recursiveResults.txt");
        for (int i = 0; i < this.dataSizes.length; i++){
          wtr.write(this.dataSizes[i] + " ");
          wtr.write(this.recurCounts[i] + " ");
          wtr.write(this.recurCountCoeff[i] + " ");
          wtr.write(this.recurTimes[i] + " ");
          wtr.write(this.recurTimeCoeff[i] + " \n");
        }
        wtr.close();
      } catch (IOException e){
        System.out.println("IOException occurred");
        e.printStackTrace();
      }
  } else {
    System.out.println("Invalid input");
  }
}//end generateReport

//Check sorting
public void checkSort (int[] arr) throws UnsortedException {
  for (int i = 0; i < arr.length-1; i++){
    if (arr[i] > arr[i+1]){
      throw new UnsortedException("Array is not sorted");
    }
  }
}

//Implements the Shell sort algorithm to sort lists
@Override
public int[] recursiveSort(int[] arr, int g){
  //I adapted this recursive Shell sort algorithm from the iterative Shell sort algorithm cited below (Mishra, n.d.).
  //Critical operations counted will include recursive calls and loops
  int n = arr.length;
  int gap = g;

  if (gap > 0) {
    for (int i = gap; i < n; i += 1)
    {
      this.tempCount+=1;
      int temp = arr[i];
      int j;
      for (j = i; j >= gap && arr[j - gap] > temp; j -= gap){
        arr[j] = arr[j - gap];
        this.tempCount += 1;
      }
      arr[j] = temp;
   }
   this.tempCount += 1;
    recursiveSort(arr, gap/2);
  }
  return arr;
}//end recursiveSort

@Override
public int[] iterativeSort(int[] arr){
  /*The following iterative version of Shell sort uses Shell's original interval of N/2, N/4, N/8 … 1 and is written by Rajat Mishra. I have marked where I inserted incrementing of count variable.
  Full citation:
   Mishra, R. (n.d.) ShellSort. Geeks for Geeks. Retrieved from https://www.geeksforgeeks.org/shellsort/
   */

   //Begin Rajat Mishra
  int n = arr.length;

        // Start with a big gap, then reduce the gap
        for (int gap = n/2; gap > 0; gap /= 2)
        {
            /*Emily McPherson*/ this.tempCount += 1; /*End Emily McPherson */
            // Do a gapped insertion sort for this gap size.
            // The first gap elements a[0..gap-1] are already
            // in gapped order keep adding one more element
            // until the entire array is gap sorted
            for (int i = gap; i < n; i += 1)
            {
              /*Emily McPherson*/  this.tempCount += 1; /*End Emily McPherson */
                // add a[i] to the elements that have been gap
                // sorted save a[i] in temp and make a hole at
                // position i
                int temp = arr[i];

                // shift earlier gap-sorted elements up until
                // the correct location for a[i] is found
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap){
                  arr[j] = arr[j - gap];
                  /*Emily McPherson*/ this.tempCount += 1; /*End Emily McPherson */
                }

                // put temp (the original a[i]) in its correct
                // location
                arr[j] = temp;
            }
        }
        //End Rajat Mishra
        return arr;
}//end iterativeSort

@Override
public int getCount(){
   return this.tempCount;
} //end getCount

@Override
public long getTime(){
  return System.nanoTime();
} //end getTime

}//end class
