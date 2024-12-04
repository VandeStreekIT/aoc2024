import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    private final String filePath = "input/day3.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {

        Day3 d = new Day3();
        String memory = Util.readFile(d.filePath);

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(memory);
        while (matcher.find()) {
            List<Integer> calculation = Arrays.stream(
                    matcher.group()
                            .substring(4, matcher.group().length() - 1)
                            .split(","))
                    .map(Integer::valueOf).toList();
            d.part1 += calculation.get(0) * calculation.get(1);
        }
        System.out.println(d.part1);
    }
}
