package day7;

import util.Util;
import java.util.Arrays;
import java.util.List;

public class Day7 {
    private final String filePath = "input/day7.txt";
    private long part1;
    private long part2;

    public static void main(String[] args) {
        // Start timer
        long startTime = System.nanoTime();

        Day7 d = new Day7();

        List<String> input = Util.readFileAsList(d.filePath);

        for (String equation: input) d.part2 += (d.equationValid(equation)) ;
        System.out.println(d.part2);

        // Stop timer
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 1: " + elapsedTime / 1_000_000 + " milliseconds");
    }

    private long equationValid(String equation) {
        long target = Long.parseLong(equation.split(": ")[0]);
        boolean targetReached = false;

        List<Integer> values = Arrays.stream(
                equation.split(": ")[1].split(" "))
                .map(Integer::parseInt).toList();

        long answer = values.getFirst();
        if (!values.isEmpty()) {
            targetReached = this.addOrMultiply(values.subList(1,values.size()), answer, target);
        }
        if (targetReached) return target;
        return 0;
    }

    private boolean addOrMultiply(List<Integer> values, long answer, long target) {
        if (answer > target) {
            return false;
        }
        if (values.size() == 1) {
            if (answer + values.getFirst() == target) return true;
            if (answer * values.getLast() == target) return true;
            if (Long.parseLong(String.valueOf(answer) + String.valueOf(values.getFirst())) == target) return true;
            return false;
        }
        if (values.size() > 1) {
            if (this.addOrMultiply(values.subList(1, values.size()), answer + values.getFirst(), target)) return true;
            if (this.addOrMultiply(values.subList(1, values.size()), answer * values.getFirst(), target)) return true;
            if (this.addOrMultiply(values.subList(1, values.size()),
                    Long.parseLong(String.valueOf(answer) + String.valueOf(values.getFirst())),
                    target)) return true;
        }
        return false;
    }
}
