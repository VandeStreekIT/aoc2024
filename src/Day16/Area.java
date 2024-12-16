package Day16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Area {
    final private List<List<String>> map;
    final private String start = "S";
    final private String end = "E";
    final private String wall = "#";
    final private String path = ".";

    Area(String filePath) {
        this.map = createArea(filePath);
    }

    private List<List<String>> createArea(String input) {
        return Arrays.stream(input.split("\n"))
                .map(str -> str.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .toList())
                .toList();
    }

    public List<Integer> getStart() {
        for (int row = 0; row < map.size(); row++) {
            for (int col = 0; col < map.get(row).size(); col++) {
                if (map.get(row).get(col).equals(start)) {
                    return Arrays.asList(row, col);
                }
            }
        }
        return null;
    }

    public List<Integer> getEnd() {
        for (int row = 0; row < map.size(); row++) {
            for (int col = 0; col < map.get(row).size(); col++) {
                if (map.get(row).get(col).equals(end)) {
                    return Arrays.asList(row, col);
                }
            }
        }
        return null;
    }

    public boolean validLocation(List<Integer> location) {
        return (!map.get(location.getFirst()).get(location.getLast()).equals(wall));
    }
}
