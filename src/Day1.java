import util.Util;

import java.util.Arrays;

public class Day1 {
    private String filePath = "input/day1.txt";
    private int part1 = 0;
    private int part2 = 0;


    public static void main(String[] args) {
        Day1 d = new Day1();
        String content = Util.readFile(d.filePath);
        String[] lines = content.split("\n");

        int[] array1 = new int[lines.length];
        int[] array2 = new int[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] values = lines[i].split("   ");
            array1[i] = Integer.parseInt(values[0]);
            array2[i] = Integer.parseInt(values[1]);
        }

        Arrays.sort(array1);
        Arrays.sort(array2);

        d.part1 = 0;
        for (int i = 0; i < array1.length; i++) {
            d.part1 += Math.abs(array1[i] - array2[i]);
        }
        System.out.println("part 1 " + d.part1);

        int[] uniqueValues = Util.getUniqueValues(array1);

        for (int uniqueValue : uniqueValues) {
            int countArray1 = Util.countOccurrencesInSortedArray(array1, uniqueValue);
            int countArray2 = Util.countOccurrencesInSortedArray(array2, uniqueValue);

            d.part2 += uniqueValue * countArray1 * countArray2;
        }
        System.out.println("part 2 " + d.part2);
    }
}