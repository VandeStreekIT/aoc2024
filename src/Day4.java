import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    private final String filePath = "input/day4.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Day4 d = new Day4();
        List<String> wordPuzzel = Util.readFileAsList(d.filePath);
        String regex = "XMAS";

        d.part1 += countMatches(wordPuzzel, regex);
        d.part1 += countMatches(rotateMatrix45(wordPuzzel), regex);
        d.part1 += countMatches(rotateMatrix90(wordPuzzel), regex);
        d.part1 += countMatches(rotateMatrix45(rotateMatrix90(wordPuzzel)), regex);
        d.part1 += countMatches(rotateMatrix90(rotateMatrix90(wordPuzzel)), regex);
        d.part1 += countMatches(rotateMatrix45(rotateMatrix90(rotateMatrix90(wordPuzzel))), regex);
        d.part1 += countMatches(rotateMatrix90(rotateMatrix90(rotateMatrix90(wordPuzzel))), regex);
        d.part1 += countMatches(rotateMatrix45(rotateMatrix90(rotateMatrix90(rotateMatrix90(wordPuzzel)))), regex);

        System.out.println("Part 1: " + d.part1);

        d.part2 += countX_MAX(wordPuzzel);
        d.part2 += countX_MAX(rotateMatrix90(wordPuzzel));
        d.part2 += countX_MAX(rotateMatrix90(rotateMatrix90(wordPuzzel)));
        d.part2 += countX_MAX(rotateMatrix90(rotateMatrix90(rotateMatrix90(wordPuzzel))));

        System.out.println("Part 2: " + d.part2);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd: " + elapsedTime / 1_000_000 + " milliseconds");
    }

    public static List<String> rotateMatrix90(List<String> wordPuzzel) {
        int n = wordPuzzel.size(); // Aantal rijen
        int m = wordPuzzel.get(0).length(); // Aantal kolommen
        List<String> rotated90 = new ArrayList<>(Collections.nCopies(m, ""));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated90.set(j, rotated90.get(j) + wordPuzzel.get(n - 1 - i).charAt(j));
            }
        }
        return rotated90;
    }

    public static List<String> rotateMatrix45(List<String> wordPuzzel) {
        List<String> wordPuzzelExtended = new ArrayList<>(wordPuzzel);
        int m = wordPuzzel.get(0).length(); // Aantal kolommen

        wordPuzzelExtended.addAll(Collections.nCopies(m - 1,".".repeat(m)));
        int n = wordPuzzelExtended.size(); // Aantal rijen

        List<String> rotated45 = new ArrayList<>(Collections.nCopies(n, ""));

        for (int i = 0; i < n ; i++ ) {
            int k = i;
            int j = 0;
            while (k >= 0 && j < m) {
                rotated45.set(i, rotated45.get(i) + wordPuzzelExtended.get(k).charAt(j));
                k--;
                j++;
            }
        }

        return rotated45;
    }
    public static int countMatches(List<String> wordPuzzel, String regex) {
        int count = 0;
        Pattern pattern = Pattern.compile(regex);
        for (String line : wordPuzzel) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                count++;
            }
        }
        return count;
    }
    public static int countX_MAX(List<String> wordPuzzel) {
        int n = wordPuzzel.size();
        int m = wordPuzzel.get(0).length();
        int count = 0;

        for (int i = 1; i < n - 1; i++) {
            for (int j =  1; j < m - 1; j++) {
                if (wordPuzzel.get(i).charAt(j) == 'A') {
                    if (wordPuzzel.get(i-1).charAt(j-1) == 'M' &&
                            wordPuzzel.get(i-1).charAt(j+1) == 'M' &&
                            wordPuzzel.get(i+1).charAt(j-1) == 'S' &&
                            wordPuzzel.get(i+1).charAt(j+1) == 'S' ){
                        count++;
                    }

                }
            }
        }
        return count;
    }
}
