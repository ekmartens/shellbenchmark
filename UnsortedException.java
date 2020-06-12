/*
*File: UnsortedException.java
*Author: Emily McPherson
*Date: 6/11/2020
*Purpose: Custom exception for unsorted arrays
*/

public class UnsortedException extends Exception {
    public UnsortedException(String errorMessage) {
        super(errorMessage);
    }
}
