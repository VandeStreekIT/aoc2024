import util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 {
    private final String filePath = "input/day5.txt";
    private int part1;
    private int part2;

    public static void main(String[] args) {
        // Start timer
        long startTime = System.nanoTime();

        // Maakt object
        Day5 d = new Day5();

        //Input verwerken van file naar edges en routes
        String input = Util.readFile(d.filePath);

        List<String> edges = List.of(input.split("\n\n")[0].split("\n"));
        List<String> routes = List.of(input.split("\n\n")[1].split("\n"));

        // Graaf als adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (String edge : edges) {
            int start = Integer.parseInt(edge.split("\\|")[0]);
            int end = Integer.parseInt(edge.split("\\|")[1]);

            if (graph.containsKey(start)) {
                graph.get(start).add(end);
                continue;
            }
            graph.put(start, new ArrayList<>(List.of(end)));
        }

        // Routes uitsplitsen
        List<List<Integer>> routesList = routes.stream()
                .map(csv -> Arrays.stream(csv.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .toList();

        List<List<Integer>> invalidManuals = new ArrayList<>();

        for (List<Integer> route : routesList) {
            if (!validManual(graph, route)) {
                invalidManuals.add(route);
                continue;
            }
            d.part1 += route.get((route.size() - 1) / 2);
        }
        System.out.println(d.part1);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 1: " + elapsedTime / 1_000_000 + " milliseconds");

        // Corrigeer de routes
        invalidManuals.forEach(route -> fixRoute(graph, route));
        invalidManuals.forEach(route -> d.part2 += route.get((route.size() - 1) / 2));

        System.out.println(d.part2);

        // Stop timer
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 2: " + elapsedTime / 1_000_000 + " milliseconds");
    }

    public static boolean validManual(Map<Integer, List<Integer>> graph, List<Integer> route) {

        for (int i = 0; i < route.size() - 1; i++) {
            int goal = route.get(route.size() - 1 - i);
            List<Integer> priorNodes = route.subList(0, route.size() - 1 - i);

            if (!graph.containsKey(goal)) {
                return true;
            }

            List<Integer> commonElements = graph.get(goal).stream()
                    .filter(priorNodes::contains)
                    .collect(Collectors.toList());

            if (!commonElements.isEmpty()) {
                return false;
            }
        }
        return true;

    }

    public static void fixRoute(Map<Integer, List<Integer>> graph, List<Integer> route) {
        for (int i = 0; i < route.size() - 1; i++) {
            int goal = route.get(route.size() - 1 - i);
            List<Integer> priorNodes = route.subList(0, route.size() - 1 - i);

            if (!graph.containsKey(goal)) {
                continue;
            }

            List<Integer> commonElements = graph.get(goal).stream()
                    .filter(priorNodes::contains)
                    .collect(Collectors.toList());

            if (!commonElements.isEmpty()) {
                int element = route.remove(route.size() - 1 - i); // Element verwijderen
                route.add(0, element); // Element toevoegen op index 0
                i--;
            }
        }
    }
}