import java.util.Arrays;

public class Day1 {
    private String filePath = "input/day1.txt";

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

        int sum = 0;
        for (int i = 0; i < array1.length; i++) {
            sum += Math.abs(array1[i]-array2[i]);
        }
        System.out.println(sum);
    }
    }
