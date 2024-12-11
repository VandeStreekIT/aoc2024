package day11;

import java.util.*;
import java.util.stream.Collectors;

import static util.Util.readFile;

public class Day11 {
    final private String filePath = "input/day11.txt";
    private long part1;
    private long part2;
    private Map<String, Integer> cache = new HashMap<>();

    public static void main(String[] args) {
        // Start timer
        long start = System.currentTimeMillis();

        Day11 d = new Day11();
        List<Integer> input = map(readFile(d.filePath));

        for (int value : input) {
            d.part1 += blink(value, 25, d.cache);
        }
        System.out.println("Part 1: " + d.part1);

        // Stop timer
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println(elapsed + "ms");
    }

    public static List<Integer> map(String input) {
        List<Integer> values = Arrays.stream(input.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return values;
    }

    public static int blink(long stoneValue, int blinkValue, Map<String, Integer> cache) {
        if (blinkValue == 0) {
            return 1;
        }
        blinkValue--;
        String stoneValueString = String.valueOf(stoneValue);

        if (stoneValue == 0) {
            return blink(1, blinkValue, cache);
        } else if (stoneValueString.length() % 2 == 0) {
            if (cache.containsKey(stoneValueString)) {
                return cache.get(stoneValueString);
            }
            long[] divmod = divmod(stoneValue, (long) Math.pow(10, stoneValueString.length()/2));;
            int value = blink(divmod[0], blinkValue, cache) + blink(divmod[1], blinkValue, cache);
            cache.put(stoneValueString + " " + blinkValue, value);
            return value;
        } else {
            return blink(stoneValue * 2024, blinkValue, cache);
        }
    }

    public static long[] divmod(long dividend, long divisor) {
        long quotient = dividend / divisor; // Integer division
        long remainder = dividend % divisor; // Modulus operation
        return new long[] { quotient, remainder }; // Return both as an array
    }


}
