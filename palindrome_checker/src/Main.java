import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a string to check if it is palindrome: ");
        String input = myObj.nextLine();

        boolean isPalindrome = true;
        int leftPointer = 0, rightPointer = input.length() - 1;
        while (leftPointer < rightPointer) {

            // String is not palindrome
            if (input.charAt(leftPointer) != input.charAt(rightPointer)) {
                isPalindrome = false;
                break;
            }

            // Increment to check next values
            leftPointer++;
            rightPointer--;
        }

        String res = isPalindrome ? "is" : "is not";

        System.out.println(input + " " + res + " palindrome!");
    }
}