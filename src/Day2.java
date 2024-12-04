import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day2 {
    private final String filePath = "input/day2.txt";
    private int part1;
//    private int part2;

    public static void main(String[] args) {

        Day2 d = new Day2();

        List<String> lines = Util.readFileAsList(d.filePath);

        List<List<Integer>> reports = lines.stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .toList();

        for (List<Integer> report : reports) {
            if (d.isSafe(report)) d.part1++;
        }
        System.out.println(d.part1);

    }

    public boolean isSafe(List<Integer> report){
        boolean result = true;
        boolean isIncreasing = false;

        if (Objects.equals(report.get(0), report.get(1))) {
            result = false;
            return result;
        } else if (report.get(0) < report.get(1)) {
            isIncreasing = true;
        }

        for (int i = 0; i < (report.size() - 1); i++) {
            if (Objects.equals(report.get(i), report.get(i + 1))) {
                result = false;
                return result;
            } else if (isIncreasing && !(
                    report.get(i + 1) > report.get(i) &&
                    report.get(i + 1) < report.get(i) + 4)) {
                result = false;
                return result;
            } else if (!isIncreasing && !(
                    report.get(i + 1) < report.get(i) &&
                    report.get(i + 1) > report.get(i) - 4)) {
                result = false;
                return result;
            }
        }
        return result;
    }
}