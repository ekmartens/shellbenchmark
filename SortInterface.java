/*
*File: SortInterface.java
*Author: Emily McPherson
*Date: 6/10/2020
*Purpose: Interface with methods for sorting
*/

interface SortInterface {

  int[] recursiveSort(int[] inout, int gap);
  int[] iterativeSort(int[] inout);
  int getCount();
  long getTime();
}//end interface
