package demo.pieces;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.reverseWords("Let's take LeetCode contest"));
    }

    public String reverseWords(String s) {
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = reverseWord(words[i]);
        }
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word).append(" ");
        }
        return result.toString().trim();
    }

    public String reverseWord(String s) {
        StringBuilder sb = new StringBuilder(s);
        char temp;
        int lastIndex = sb.length() - 1;
        for (int i = 0; i < sb.length() / 2; i++) {
            temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(lastIndex - i));
            sb.setCharAt(lastIndex - i, temp);
        }
        return sb.toString();
    }
    

    public void printArray(int[] array, String arrayName) {
        System.out.println(arrayName + ":");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                System.out.print(array[i] + ".");
            } else {
                System.out.print(array[i] + ", ");
            }
        }
        System.out.println();
    }
}
