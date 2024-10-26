/*
* COMP 2140 SECTION A02
* INSTRUCTOR: Dr. Lauren Himbeault
* STUDENT NUMBER: 7980132
* ASSIGNMENT: Assignment 1
* QUESTION: Question 4
*
* PURPOSE: 3SUM
*/

import java.io.File;
import java.util.Scanner;


public class AdejareTaiwoA1Q4 {
    public static void main(String[] args) {
        Scanner scanner;
        try {
            String fileName;
            if (args.length > 0) {
                fileName = args[0];
                scanner = new Scanner(new File(fileName));

                // Read lines from file using scanner
                int size = Integer.parseInt(scanner.nextLine());
                String numStr = scanner.nextLine();

                // Convert given number to array<int>
                String[] nums = numStr.split(" ");
                int[] A = new int[size];
                for (int i = 0; i < nums.length; i++) {
                    A[i] = Integer.parseInt(nums[i]);
                }

                if (hasThreeSum(A)) {
                    System.out.println("Yayy!!! We found 3 number that sums up to zero.");
                } else {
                    System.out.println("Opp!!! We didnt find any 3 number that sums up to zero");
                }
                scanner.close();
            } else {
                throw new Exception("File name is required");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * "hasTargetSum" method search for 3 numbers that sums up to zero using 3
     * pointers.
     * 
     * @returns boolean value for answers;
     */
    static boolean hasTargetSum(int[] A, int j, int k, int t) {
        boolean answer = false;
        int i = j - 1;
        // Reduce inded of k if sum is bigger and increase that of j if sum is smaller
        // than target
        while ((j < k) && (!answer)) {
            int tempSum = A[i] + A[j] + A[k];
            if (tempSum < t) {
                j = j + 1;
            } else if (tempSum > t) {
                k = k - 1;
            } else {
                answer = true;
                System.out.printf("The three numbers: %d %d %d \n", A[i], A[j], A[k]);
            }
        }

        return answer;
    }

    /*
     * "hasThreeSum" method ilterates over every
     * 
     * @returns boolean;
     */
    static boolean hasThreeSum(int[] A) {
        int TARGET = 0; // Requested target value.
        boolean answers = false;
        int n = A.length; // Size of array A
        int i = 0;

        while ((i < n - 2) && (!answers)) {
            answers = hasTargetSum(A, i + 1, n - 1, TARGET);
            i = i + 1;
        }

        return answers;
    }
}