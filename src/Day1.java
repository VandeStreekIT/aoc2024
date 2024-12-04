import java.util.Arrays;
import java.util.HashSet;

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

        int[] uniqueValues = getUniqueValues(array1);

        for (int uniqueValue : uniqueValues) {
            int countArray1 = countOccurrencesInSortedArray(array1, uniqueValue);
            int countArray2 = countOccurrencesInSortedArray(array2, uniqueValue);

            d.part2 += uniqueValue * countArray1 * countArray2;
        }
        System.out.println("part 2 " + d.part2);
    }

    public static int[] getUniqueValues(int[] array) {
        HashSet<Integer> uniqueSet = new HashSet<>();
        for (int num : array) {
            uniqueSet.add(num);
        }

        int[] uniqueArray = new int[uniqueSet.size()];
        int index = 0;
        for (int num : uniqueSet) {
            uniqueArray[index++] = num;
        }

        return uniqueArray;
    }

    public static int countOccurrencesInSortedArray(int[] array, int target) {
        int firstIndex = findFirstOccurrence(array, target);
        if (firstIndex == -1) {
            // Het doelgetal komt niet voor
            return 0;
        }
        int lastIndex = findLastOccurrence(array, target);
        return lastIndex - firstIndex + 1;
    }

    public static int findFirstOccurrence(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                result = mid; // Kandidaat voor de eerste voorkoming
                high = mid - 1; // Zoek verder naar links
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    public static int findLastOccurrence(int[] array, int target) {
        int low = 0, high = array.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                result = mid; // Kandidaat voor de laatste voorkoming
                low = mid + 1; // Zoek verder naar rechts
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }
}