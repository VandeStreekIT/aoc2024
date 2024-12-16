package Day16;

import java.util.*;

class Reindeer {
    final private Area area;
    private Map<String, Integer> scoreMap = new HashMap<>();
    private int lowestScore = 0;
    private Set<List<Integer>> bestSeats = new HashSet<>();


    public Reindeer(Area area) {
        this.area = area;
    }

    public void solveMaze(List<Integer> location, int orientation, int score, List<List<Integer>> visited, List<List<Integer>> path) {
        if (score > 75416) {
            return;
        }

        // Track visited locations
        String locationOrientation = List.of(location, List.of(orientation)).toString();
        scoreMap.put(locationOrientation, score);
        ArrayList<List<Integer>> extendedPath = new ArrayList<>(path);
        extendedPath.add(location);

        // Volgende locatie vooruit
        int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        List<List<Integer>> nextLocationForward = List.of(List.of(location.getFirst() + directions[orientation][0],
                location.getLast() + directions[orientation][1]), List.of(orientation));
        List<List<Integer>> nextLocationLeft = List.of(List.of(location.getFirst() + directions[(orientation + 3) % 4][0],
                location.getLast() + directions[(orientation + 3) % 4][1]), List.of(orientation));
        List<List<Integer>> nextLocationRight = List.of(List.of(location.getFirst() + directions[(orientation + 1) % 4][0],
                location.getLast() + directions[(orientation + 1) % 4][1]), List.of(orientation));

        if (location.equals(area.getEnd())) {
            if (scoreMap.get(locationOrientation) < lowestScore || lowestScore == 0) {
                lowestScore = scoreMap.get(locationOrientation);
                bestSeats = new HashSet<>(extendedPath);
            } else if (scoreMap.get(locationOrientation) == lowestScore) {
                for (List<Integer> seat : extendedPath) {
                    bestSeats.add(seat);
                }
            }
            return;
        }

        // Probeer vooruit
        if (area.validLocation(nextLocationForward.getFirst()) && (!scoreMap.containsKey(nextLocationForward.toString()) || scoreMap.get(nextLocationForward.toString()) >= score)) {
            solveMaze(nextLocationForward.getFirst(), orientation, score + 1, visited, extendedPath);
        }

        // Probeer Links
        if (area.validLocation(nextLocationLeft.getFirst()) && (!scoreMap.containsKey(nextLocationLeft.toString()) || scoreMap.get(nextLocationLeft.toString()) >= score)) {
            solveMaze(nextLocationLeft.getFirst(), (orientation + 3) % 4, score + 1001, visited, extendedPath);
        }

        // Probeer Rechts
        if (area.validLocation(nextLocationRight.getFirst()) && (!scoreMap.containsKey(nextLocationRight.toString()) || scoreMap.get(nextLocationRight.toString()) >= score)) {
            solveMaze(nextLocationRight.getFirst(), (orientation + 1) % 4, score + 1001, visited, extendedPath);
        }
        return;
    }

    public int getLowestScore() {
        return this.lowestScore;
    }

    public int bestSeats() {
        return bestSeats.size();
    }
}
