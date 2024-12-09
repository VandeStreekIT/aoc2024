package day9;

import java.util.*;
import java.util.stream.IntStream;

import static util.Util.readFile;

public class Day9 {
    private final String filePath = "input/day9.txt";
    private long part1;
    private long part2;
    private List<Integer> memory = new ArrayList<>();
    private Map<Integer, Integer> freeSpaces = new HashMap<>();
    private Map<Integer, List<Integer>> fileBlocks = new HashMap<>();

    public static void main(String[] args) {
        // Start timer
        long startTime = System.currentTimeMillis();
        Day9 d = new Day9();

        String diskSpace = readFile(d.filePath);

        boolean isFile = true;
        int fileId = 0;
        int i = 0;

        for (char c : diskSpace.toCharArray()) {
            int size = Character.getNumericValue(c);
            d.memory.addAll(Collections.nCopies(size, (isFile) ? fileId : null));
            if (isFile) {
                d.fileBlocks.putIfAbsent(i, new ArrayList<>(List.of(fileId, size)));
                fileId++;
            } else {
                d.freeSpaces.putIfAbsent(i, size);
            }
            isFile = !isFile;
            i += size;
        }

//        d.doPart1(diskSpace, startTime, memory);

        d.doPart2();

        for (int j = 0; j < d.memory.size(); j++) {
            if (d.memory.get(j) == null) continue;
            d.part2 += d.memory.get(j) * j;
        }
        System.out.println("Part 2: " + d.part2);

        // Stop timer
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration + " ms");
    }

//    private List<Integer> doPart1(String diskSpace, long startTime, List<Integer> memory) {
//
//        int memorySize = memory.size();
//        int nullCount = Collections.frequency(memory, null);
//
//        for (int i = 0; i < memorySize; i++) {
//            if (memory.get(memorySize - i - 1) == null) continue;
//            if (!memory.contains(null)) break;
//            memory.set(memory.indexOf(null), memory.get(memorySize - i - 1));
//        }
//        memory.subList(memory.size() - nullCount, memory.size()).clear();
//
//        return memory;
//    }

    private void doPart2() {

        int maxLocationId = Collections.max(this.fileBlocks.keySet());
        for (int j = 0; j < maxLocationId; j++) {

            int locationId = maxLocationId - j;
            if (!this.fileBlocks.containsKey(locationId)) continue;

            int freeSpot = findFreeSpot(fileBlocks.get(locationId).getLast(), locationId);
            if (freeSpot == -1) continue;

            this.moveFileBlock(fileBlocks.get(locationId).getFirst(), fileBlocks.get(locationId).getLast(), locationId, freeSpot);
        }

    }

    private int findFreeSpot(int blockSize, int maxLocationId) {
        int locationId = -1;
        for (int key = 0; key < maxLocationId; key++) {
            if (!this.freeSpaces.containsKey(key)) continue;
            if (this.freeSpaces.get(key) >= blockSize){
                locationId = key;
                break;
            }
        }
        return locationId;
    }

    private void moveFileBlock(int fileId, int blockSize, int locationIdOld, int locationIdNew) {
        // Pas freeSpace aan
        int freeSpace = this.freeSpaces.get(locationIdNew);
        this.freeSpaces.remove(locationIdNew);
        if (!(freeSpace == blockSize)) {
            this.freeSpaces.put(locationIdNew + blockSize, freeSpace - blockSize);
        }

        // Pas fileBlocks aan
        this.fileBlocks.put(locationIdNew, new ArrayList<>(List.of(fileId, blockSize)));
        this.freeSpaces.remove(locationIdOld);

        // Pas memory aan
        for (int i = locationIdNew; i < (locationIdNew + blockSize); i++) {
            this.memory.set(i, fileId);
        }
        for (int i = locationIdOld; i < (locationIdOld + blockSize); i++) {
            this.memory.set(i, null);
        }
    }
}