package day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import static util.Util.readFile;

public class Day10 {
    final private String filePath = "input/day10.txt";
    private long part1;
    private long part2;
    private List<List<Integer>> startPositions;
    private List<List<Integer>> map;
    final private int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    final private int start = 0;
    final private int end = 9;
    private List<List<Integer>> visitedTops;

    public Day10() {
        visitedTops = new ArrayList<>();
        startPositions = new ArrayList<>();
        map = map(readFile(this.filePath));
    }

    public static void main(String[] args) {
        // Start timer
        long startTime = System.currentTimeMillis();

        Day10 d = new Day10();
        d.map = map(readFile(d.filePath));

        d.findBottom();
        for (List<Integer> start : d.startPositions) {
            d.visitedTops.clear();
            d.findTop(start);
            d.part1 += d.visitedTops.size();
        }

        System.out.println(d.part1);

        // Stop timer
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration + " ms");

    }

    public static List<List<Integer>> map(String input) {
        return Arrays.stream(input.split("\n"))
                .map(str -> str.chars()
                        .mapToObj(Character::getNumericValue)
                        .collect(Collectors.toList()))
                .toList();
    }

    private void findBottom() {
        for (int row = 0; row < this.map.size(); row++) {
            for (int col = 0; col < this.map.getFirst().size(); col++) {
                if (this.map.get(row).get(col) == 0) {
                    this.startPositions.add(new ArrayList<>(List.of(row, col)));
                }
            }
        }
    }

    private void findTop(List<Integer> start) {
        int row = start.get(0);
        int col = start.get(1);
        int startHeight = this.map.get(row).get(col);
        if (startHeight == this.end && !this.visitedTops.contains(List.of(row, col))) {
            this.visitedTops.add(new ArrayList<>(List.of(row, col)));
            return;
        }

        List<List<Integer>> nextSteps = new ArrayList<>();
        for (int[] DIRECTION : this.DIRECTIONS) {
            int nextRow = row + DIRECTION[0];
            int nextCol = col + DIRECTION[1];

            if (nextRow < 0 || nextRow >= this.map.size() || nextCol < 0 || nextCol >= this.map.get(nextRow).size()) {
                continue;
            }
            if (this.map.get(nextRow).get(nextCol) == startHeight +1) {
                nextSteps.add(new ArrayList<>(List.of(nextRow, nextCol)));
            }

        }
        if (nextSteps.isEmpty()) return;
        for (List<Integer> next : nextSteps) {
            this.findTop(next);
        }
    }
}
