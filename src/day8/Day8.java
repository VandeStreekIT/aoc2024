package day8;

import java.util.HashSet;
import java.util.Set;

public class Day8 {
    private final String filePath = "input/day6.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        // Start timing
        long startTime = System.nanoTime();

        String input = util.readFile();



        // Stop timing
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 1: " + elapsedTime / 1_000_000 + " milliseconds");
    }

    public static Set<String> uniqueFreq(String input) {
        Set<String> uniqueFreq = new HashSet<>();
        return uniqueFreq;
    }
}
