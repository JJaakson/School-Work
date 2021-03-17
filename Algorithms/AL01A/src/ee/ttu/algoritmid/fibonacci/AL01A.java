package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        if (n == 0) {
            return "0";
        } else if (n == 1) {
            return "1";
        }
        BigInteger firstNumber = BigInteger.valueOf(0);
        BigInteger secondNumber = BigInteger.valueOf(1);
        BigInteger anwser = BigInteger.valueOf(0);
        for (int i = 0; i < n - 1; i++) {
            anwser = firstNumber.add(secondNumber);
            firstNumber = secondNumber;
            secondNumber = anwser;
        }
        return String.valueOf(anwser);
    }
}
