import util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private final String filePath = "input/day3.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        Day3 d = new Day3();
        String memory = Util.readFile(d.filePath);
        boolean enable = true;
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < memory.length(); i++) {
            if (memory.substring(i, (Math.min(i + 7, memory.length() - 1))).equals("don't()")) {
                enable = false;
                i += 6;
                continue;
            }
            if (memory.substring(i, (Math.min(i + 4, memory.length() - 1))).equals("do()")) {
                enable = true;
                i += 3;
                continue;
            }
            String subSting = memory.substring(i, (Math.min(i + 12, (memory.length() - 1))));
            if (subSting.matches(regex + ".*")) {
                Matcher matcher = pattern.matcher(subSting);
                while (matcher.find()) {
                    List<Integer> calculation = Arrays.stream(
                                    matcher.group()
                                            .substring(4, matcher.group().length() - 1)
                                            .split(","))
                            .map(Integer::valueOf).toList();

                    d.part1 += calculation.get(0) * calculation.get(1);
                    if (enable) {
                        d.part2 += calculation.get(0) * calculation.get(1);
                    }
                }
            }
        }
        System.out.println(d.part1);
        System.out.println(d.part2);
    }
}
