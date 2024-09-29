import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ThreeSum {
    public static void main(String[] args) {
        try {
            // Reading the file "TestThreeSum.txt"
            String fileName;
            if(args.length > 0) {
                fileName = args[0];
                File file = new File(fileName);
                Scanner scanner = new Scanner(file);
    
                // First line contains the number of integers in the list
                int n = scanner.nextInt();
                int[] A = new int[n];
    
                // Read the remaining integers into the array
                for (int i = 0; i < n; i++) {
                    A[i] = scanner.nextInt();
                }
    
                // Call the hasThreeSum method to check for the triplet
                if (hasThreeSum(A)) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
                

            scanner.close();

            }

        

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // Function based on the provided pseudocode
    public static boolean hasTargetSum(int[] A, int j, int k, int t) {
        boolean answer = false;
        int initalVal = j - 1;
        while (j < k && !answer) {
            if (A[initalVal] + A[j] + A[k] < t) {
                j++;  // Move the left pointer up to increase the sum
            } else if (A[initalVal] + A[j] + A[k] > t) {
                k--;  // Move the right pointer down to decrease the sum
            } else {
                answer = true;  // Found two elements that sum to t
            }
        }
        return answer;
    }

    // Function to solve the 3SUM problem, based on the pseudocode
    public static boolean hasThreeSum(int[] A) {
        int n = A.length;
        int i = 0;
        boolean answer = false;

        while (i < n - 2 && !answer) {
            // Check if there are two elements in A[i+1] to A[n-1] that sum to -A[i]
            answer = hasTargetSum(A, i + 1, n - 1, 11);
            i++;
        }

        return answer;
    }

   
}