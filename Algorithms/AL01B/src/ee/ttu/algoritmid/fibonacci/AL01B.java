package ee.ttu.algoritmid.fibonacci;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class AL01B {
    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        BigDecimal anwser;
        int firstMeasure = 40;
        BigDecimal timeToCompleteFirstTime = timeToComputeFirstTimeFibonacci(firstMeasure);
        BigDecimal firstTenFibonacciLines = fibonacciLinesCalculator(firstMeasure);
        BigDecimal lineCount = fibonacciLinesCalculator(n);
        anwser = timeToCompleteFirstTime
                .multiply(lineCount);
        anwser = anwser.divide(firstTenFibonacciLines, 15, RoundingMode.CEILING);
        BigDecimal yearDivider = BigDecimal.valueOf(31556926);
        BigDecimal timeInYears = anwser
                .divide(yearDivider, 20, RoundingMode.CEILING);
        return timeInYears.toString();
    }

    public BigDecimal fibonacciLinesCalculator(int n) {
        return BigDecimal.valueOf(3).multiply(iterativeF(n)).subtract(BigDecimal.valueOf(2));
    }

    public BigDecimal timeToComputeFirstTimeFibonacci(int n) {
        long startTime = System.nanoTime();
        recursiveF(n);
        long endTime = System.nanoTime();
        return BigDecimal.valueOf(endTime - startTime).divide(BigDecimal.valueOf(1000000000), RoundingMode.CEILING);
    }

    public BigDecimal iterativeF(int n) {
        if (n == 0) {
            return BigDecimal.valueOf(0);
        } else if (n == 1) {
            return BigDecimal.valueOf(1);
        }
        BigDecimal firstNumber = BigDecimal.valueOf(0);
        BigDecimal secondNumber = BigDecimal.valueOf(1);
        BigDecimal anwser = BigDecimal.valueOf(0);
        for (int i = 0; i < n - 1; i++) {
            anwser = firstNumber.add(secondNumber);
            firstNumber = secondNumber;
            secondNumber = anwser;
        }
        return anwser;
    }

    /**
     * Compute the Fibonacci sequence number recursivelys.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }

    public static void main(String[] args) {
        AL01B test = new AL01B();
        System.out.println(test.timeToComputeRecursiveFibonacci(15));
    }
}
