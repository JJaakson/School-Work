
package ee.taltech.iti0200.datastructures;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;


public class DataStructures {

    private static final int FIRSTCHAR = 0;
    private static final int FIRSTINLIST = 0;
    private static final int SECONDINLIST = 1;
    private static final int ADDCOUNT = 1;
    private static final int MODULUSBYTWO = 2;
    private static final int MODULUSZERO = 0;

    /**
     * Given String is a sentence with some words.
     * There are only single whitespace between every word and no punctuation marks.
     * Also there are no capital letters in input string.
     * <p>
     * Return the longest word from the input sentence.
     * <p>
     * If there are more than one word with the same length then return the word which comes alphabetically first.
     * <p>
     * Hints:
     * You can split words into an array using "str.split()"
     * Sorting the list the longest words can definitely help you to find the word which comes alphabetically first.
     *
     * @param sentence input String to find the longest words
     * @return the longest String from input
     */
    public static String findLongestWord(String sentence) {
        List<String> words = new ArrayList<>(Arrays.asList(sentence.split(" ")));
        words.sort(Comparator.comparingInt(String::length));
        Collections.reverse(words);
        List<String> correctLengthList = new ArrayList<String>();
        for (String str: words) {
            String longest = words.get(FIRSTINLIST);
            if (str.length() == longest.length()) {
                correctLengthList.add(str);
            }
        }
        Collections.sort(correctLengthList);
        return correctLengthList.get(FIRSTINLIST);
    }


    /**
     * Imagine yourself as a librarian who needs to rearrange a bookshelf in the library.
     * <p>
     * You have an input List with book name and author.(in format [Name-Author])
     * If the first letter of the book name and the author name are the same (case-insensitive)
     * then you need to put the book (name) on left part of the shelf (before every other book),
     * * if not then on the right part (after every other book).
     * The list will contain book names (and a separator).
     * <p>
     * <p>
     * Shelf separator is "][". It will be a separate element in your list.
     * There is always exactly one separator in your result list.
     * So if the bookshelf is empty it should look like [][] when printing out.
     * If there is one book on the left side then: [Raamat, ][]
     * If there are books on both sides then: [LeftBook, ][, RightBook]
     * <p>
     * To add smth to the beginning of the list or to the end take a look at LinkedList data structure.
     * For example list.addFirst() can help you.
     * <p>
     * There are some examples below.
     * <p>
     * PS. BE CAREFUL WITH CAPITAL AND NON-CAPITAL LETTERS!
     *
     * @param books input List to locate correctly.
     * @return List of book names located correctly
     */
    public static List<String> rearrangeTheShelf(List<String> books) {
        LinkedList<String> shelf = new LinkedList<String>();
        shelf.add("][");
        for (String book : books) {
            String[] x = book.split("-");
            char name = x[FIRSTINLIST].charAt(FIRSTCHAR);
            char author = x[SECONDINLIST].charAt(FIRSTCHAR);
            if (Character.toLowerCase(name) == Character.toLowerCase(author)) {
                shelf.addFirst(x[FIRSTINLIST]);
            } else {
                shelf.addLast(x[FIRSTINLIST]);
            }
        }
        return shelf;
    }

    /**
     * Loop over the given list of strings to build a resulting list of string like this:
     * when a string appears the 2nd, 4th, 6th, etc. time in the list, append the string to the result.
     * <p>
     * Return the empty list if no string appears a 2nd time.
     * <p>
     * Use map to count times that the string has appeared.
     *
     * @param words input list to filter
     * @return list of strings matching criteria
     */
    public static List<String> onlyEvenWords(List<String> words) {
        ArrayList<String> result = new ArrayList<>();
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            if (wordCounts.containsKey(word)) {
                int newCount = wordCounts.get(word) + ADDCOUNT;
                wordCounts.replace(word, newCount);
            } else {
                wordCounts.put(word, ADDCOUNT);
            }
            if (wordCounts.get(word) % MODULUSBYTWO == MODULUSZERO) {
                result.add(word);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(findLongestWord("nimi on salastatud"));  // "salastatud"
        System.out.println(findLongestWord("aaa bbbbb"));  // "bbbbb"
        System.out.println(findLongestWord("hello ahllo")); // "ahllo"
    }
}
