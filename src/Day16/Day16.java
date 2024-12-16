package Day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Util.readFile;

class Day16 {

    public static void main(String[] args) {
        //Start timer
        long startTime = System.currentTimeMillis();

        // Inport area
        String input = readFile("input/day16.txt");
        Area a = new Area(input);
        List<Integer> start = a.getStart();
        List<Integer> end = a.getEnd();

        // Start solver
        Reindeer r = new Reindeer(a);
        r.solveMaze(a.getStart(), 1, 0, new ArrayList<>(), new ArrayList<>());

        // print output
        int part1 = r.getLowestScore();
        System.out.println("Part 1: " + part1);

        int part2 = r.bestSeats();
        System.out.println("Part 2: " + part2);

        // Stop timer
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + " milliseconds");

    }
}
