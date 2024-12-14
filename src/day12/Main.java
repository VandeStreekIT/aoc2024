package day12;

import util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input = Util.readFileAsList("input/day12.txt");
        Area area = new Area(input);
//        int part1 = area.calculatePrice();
        int part2 = area.calculateBulkPrice();
//        System.out.println(part1);
        System.out.println(part2);
    }
}