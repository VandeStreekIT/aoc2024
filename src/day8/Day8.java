package day8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static util.Util.readFile;

public class Day8 {
    private final String filePath = "input/day8.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        // Start timing
        long startTime = System.nanoTime();

        Day8 d = new Day8();

        String input = readFile(d.filePath);

        Set<String> uniqueFreq = uniqueFreq(input);
        List<List<String>> map = map(input);

        Map<String, List<List<Integer>>> positions = findPositions(map, uniqueFreq);

        Set<List<Integer>> antiNodes = findNodes(positions, map);
        d.part2 = antiNodes.size();
        System.out.println("Part 2: " + d.part2);

        // Stop timing
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 1: " + elapsedTime / 1_000_000 + " milliseconds");
    }

    public static Set<String> uniqueFreq(String input) {
        return input.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toSet());
    }

    public static List<List<String>> map(String input) {
        return Arrays.stream(input.split("\n"))
                .map(str -> str.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.toList()))
                .toList();
    }

    public static Map<String, List<List<Integer>>> findPositions(List<List<String>> map, Set<String> uniqueFreq) {

        Map<String, List<List<Integer>>> positions = new HashMap<>();

        for (String freq : uniqueFreq) {

            List<List<Integer>> indexes = new ArrayList<>();

            IntStream.range(0, map.size())
                    .forEach(row -> IntStream.range(0, map.get(row).size())
                            .filter(col -> freq.equals(map.get(row).get(col)))
                            .forEach(col -> indexes.add(List.of(row, col)))
                    );

            positions.put(freq, indexes);
        }

        return positions;
    }

    public static Set<List<Integer>> findNodes(
            Map<String, List<List<Integer>>> positions,
            List<List<String>> map) {

        Set<List<Integer>> nodes = new HashSet<>();

        positions.forEach((key, value) -> {
            for (int i = 0; i < value.size(); i++) {
                for (int j = 1; j < value.size(); j++) {
                    if (i >= j) continue;

                    List<Integer> position1 = value.get(i);
                    List<Integer> position2 = value.get(j);

//                    List<List<Integer>> antiNodes = new ArrayList<>();
                    List<Integer> antiNodeFreq = findAntiNodeFreq(position1, position2);
                    nodes.add(position1);
                    for (Integer direction : List.of(-1, 1)) {
                        boolean onMap = true;
                        List<Integer> position = position1;
                        while (onMap) {
                            List<Integer> antiNode = extrapolateFreq(position, antiNodeFreq, direction);
                            if (antiNode.getFirst() >= 0 && antiNode.getLast() >= 0
                                    && antiNode.getFirst() < map.size() && antiNode.getLast() < map.getFirst().size()) {
                                nodes.add(antiNode);
                                position = antiNode;
                            } else {
                                onMap = false;
                            }
                        }
                    }

//                    List<List<Integer>> antiNodes = List.of(
//                            findPositions(position1, position2),
//                            findPositions(position2, position1));
//
//                    for (List<Integer> antiNode : antiNodes) {
//                        if (antiNode.getFirst() >= 0 && antiNode.getLast() >= 0
//                                && antiNode.getFirst() < map.size() && antiNode.getLast() < map.getFirst().size()) {
//                            nodes.add(antiNode);
//                        }
//                    }
                }
            }
        });
        return nodes;
    }

    public static List<Integer> findPositions(List<Integer> position1, List<Integer> position2) {
        return IntStream.range(0, position1.size())
                .mapToObj(j -> (position1.get(j) + (position1.get(j) - position2.get(j))))
                .toList();
    }

    public static List<Integer> findAntiNodeFreq(List<Integer> position1, List<Integer> position2) {
        return IntStream.range(0, position1.size())
                .mapToObj(j -> position1.get(j) - position2.get(j))
                .toList();
    }

    public static List<Integer> extrapolateFreq(List<Integer> position, List<Integer> antiNodeFreq, int direction) {
        return IntStream.range(0, position.size())
                .mapToObj(j -> position.get(j) - (antiNodeFreq.get(j) * direction))
                .toList();
    }
}
