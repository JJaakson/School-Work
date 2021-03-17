package ee.taltech.iti0200.introduction;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Introduction {


    /**
     * Method gets a string that contains x words.
     * The first character of the string starts a new word, next words always start with a capital letter.
     * Words are not separated by whitespace.
     * Words (including the first character) can contain all kinds of symbols.
     * The function should find and return x .
     *
     * @param string Given string that contains x words.
     * @return The number of words in the given string.
     */
    public int camelCaseWordCounter(String string) {

        int wordcount;

        if (string.equals("")) {
            return 0;
        } else if (Character.isUpperCase(string.charAt(0))) {
            wordcount = 0;
        } else {
            wordcount = 1;
        }

        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                wordcount += 1;
                }
    }
        return wordcount;
    }

    /**
     * Method gets a list of numbers.
     * Return a list containing only even numbers of the given list.
     * If the given list does not contain any even numbers, return an empty list.
     *
     * @param numbers given list that contains numbers.
     * @return list of even numbers.
     */
    public List<Integer> findEvenNumbersList(List<Integer> numbers) {
        ArrayList<Integer> evens = new ArrayList<Integer>();

        for (Integer i : numbers) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }
        return evens;
    }

    /**
     * Method gets an array of numbers.
     * Return an array containing only even numbers of the given array.
     * If the given array does not contain any even numbers, return an empty array.
     *
     * You must not use the previous function in this function!
     *
     * @param numbers given array that contains numbers.
     * @return array of even numbers.
     */
    public int[] findEvenNumbersArray(int[] numbers) {
        int evens = 0;
        for (int i: numbers) {
            if (i % 2 == 0) {
                evens++;
            }
        }
        int[] evensArray = new int[evens];
        int index = 0;
        for (int i : numbers) {
            if (i % 2 == 0) {
                evensArray[index] = i;
                index++;
            }
        }

        return evensArray;
    }

    public static void main(String[] args) {
        Introduction introduction = new Introduction();
        System.out.println(introduction.camelCaseWordCounter("A")); // 3

        List<Integer> nums = new ArrayList<>(Arrays.asList(4, 7, 5, 2, 1, 2, -2, 0));
        System.out.println(introduction.findEvenNumbersList(nums)); // [4, 2, 2, -2, 0]

        int[] array = {9, 0, 24, -6, 3};
        System.out.println(Arrays.toString(introduction.findEvenNumbersArray(array))); // [0, 24, -6]
    }
}
