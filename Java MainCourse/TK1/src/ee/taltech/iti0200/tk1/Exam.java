package ee.taltech.iti0200.tk1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Exam {

    /**
        * Return the sum of the numbers in the array,
        * except ignore sections of numbers
        * starting with a 6 and extending to the next 7
        * (every 6 will be followed by at least one 7).
        * Return 0 for no numbers.
        *
        * sum67([1, 2, 2]) => 5
        * sum67([1, 2, 2, 6, 99, 99, 7]) => 5
        * sum67([1, 1, 6, 7, 2]) => 4
        */
    public static int sum67(List<Integer> numbers) {
        if (numbers.size() == 0) {
            return 0;
        }
        boolean hasStarted = false;
        int sum = 0;
        for (Integer number : numbers) {
            if (number == 6) {
                hasStarted = true;
            }
            if (!hasStarted) {
                sum += number;
            }
            if (hasStarted && number == 7) {
                hasStarted = false;
            }
        }
        return sum;
    }

    /**
        * Given a string, compute a new string by moving the first char to come after the next two chars, s "bca".
        * Repeat this process for each subsequent group of 3 chars, so "abcdef" yields  than 3 chars at the end.
        *
        * oneTwo("abc") => "bca"
        * oneTwo("tca") => "cat"
        * oneTwo("tcagdo") => "catdog"
        */
    public static String oneTwo(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i += 3) {
            if (i + 2 < str.length()) {
                result.append(str.charAt(i + 1));
                result.append(str.charAt(i + 2));
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    /**
        * Modify and return the given map as follows:
        * if exactly one of the keys "a" or "b" exists in the map (but not both), set the othalue in the map.
        *
        * mapAXorB({"a": "aaa", "c": "cake"}) => {"a": "aaa", "b": "aaa", "c": "cake"}
        * mapAXorB({"b": "bbb", "c": "cake"}) => {"a": "bbb", "b": "bbb", "c": "cake"}
        * mapAXorB({"a": "aaa", "b": "bbb", "c": "cake"}) => {"a": "aaa", "b": "bbb", "c": "cake"}
        */
    public static Map<String, String> mapAXorB(Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue());
            if (entry.getKey().equals("a") && !map.containsKey("b")) {
                resultMap.put("b", entry.getValue());
            } else if (entry.getKey().equals("b") && !map.containsKey("a")) {
                resultMap.put("a", entry.getValue());
            }
        }
        return resultMap;
    }
}
