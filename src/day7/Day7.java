package day7;

import util.Util;
import java.util.Arrays;
import java.util.List;

public class Day7 {
    private final String filePath = "input/day7.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        Day7 d = new Day7();

        List<String> input = Util.readFileAsList(d.filePath);

        for (String equation: input) if (d.equationValid(equation)) d.part1++;
        System.out.println(d.part1);
    }

    private boolean equationValid(String equation) {
        long target = Long.parseLong(equation.split(": ")[0]);
        boolean targetReached = false;

        List<Integer> values = Arrays.stream(
                equation.split(": ")[1].split(" "))
                .map(Integer::parseInt).toList();

        long answer = values.getFirst();
        if (!values.isEmpty()) {
            targetReached = this.addOrMultiply(values.subList(1,values.size()), answer, target);
        }
        return targetReached;
    }

    private boolean addOrMultiply(List<Integer> values, long answer, long target) {
        if (answer > target) {
            return false;
        }
        if (values.size() == 1) {
            if (answer + values.getFirst() == target) return true;
            if (answer * values.getLast() == target) return true;
            return false;
        }
        if (values.size() > 1) {
            if (this.addOrMultiply(values.subList(1, values.size()), answer + values.getFirst(), target)) return true;
            if (this.addOrMultiply(values.subList(1, values.size()), answer * values.getFirst(), target)) return true;
        }
        return false;
    }
}
