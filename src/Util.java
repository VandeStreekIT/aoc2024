import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Util {
    public static String readFile(String filePath) {
        String content = null;
        try {
            // Lees het bestand als één enkele string
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
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
}