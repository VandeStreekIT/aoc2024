package day9;

import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.Util.*;

public class Day9 {
    private final String filePath = "input/day9.txt";
    private long part1;
    private long part2;

    public static void main(String[] args) {
        // Start timer
        long startTime = System.currentTimeMillis();

        Day9 d = new Day9();
        String diskSpace = readFile(d.filePath);


        List<Integer> memory = new ArrayList<>();
        boolean isFile = true;
        int fileId = 0;

        for (char c : diskSpace.toCharArray()) {
            memory.addAll(Collections.nCopies(Character.getNumericValue(c), (isFile) ? fileId : null));
            if (isFile) fileId++;
            isFile = !isFile;
        }

        int memorySize = memory.size();
        int nullCount = Collections.frequency(memory, null);

        for (int i = 0; i < memorySize; i++) {
            if (memory.get(memorySize - i - 1) == null) continue;
            if (!memory.contains(null)) break;
            memory.set(memory.indexOf(null), memory.get(memorySize - i - 1));
        }
        memory.subList(memory.size() - nullCount, memory.size()).clear();

        for (int i = 0; i < memory.size(); i++) {
            d.part1 += memory.get(i) * i;
        }
        System.out.println("Part 1: " + d.part1);

        // Stop timer
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println(elapsedTime + " ms elapsed");

    }


}