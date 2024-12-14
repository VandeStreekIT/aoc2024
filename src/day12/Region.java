package day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Region {
    private Map<List<Integer>, Plot> plots;

    public Region() {
        plots = new HashMap<>();
    }

    public Region(Plot plot) {
        plots = new HashMap<>();
        this.addPlot(plot);
    }

    public void addPlot(Plot plot) {
        this.plots.put(plot.getLocation(), plot);
    }

    public int getPrice(Area area) {
        int fenceCount = 0;
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (Plot plot : plots.values()) {
            for (int[] direction : directions) {
                List<Integer> neighbourLocation = List.of(plot.getLocation().get(0) + direction[0], plot.getLocation().get(1) + direction[1]);
                if (neighbourLocation.getFirst() < 0 || neighbourLocation.getLast() < 0 ||
                        neighbourLocation.getFirst() >= area.getSize()[0] || neighbourLocation.getLast() >= area.getSize()[1]) {
                    fenceCount++;
                } else if (!plots.containsValue(area.getPlot(neighbourLocation))) {
                    fenceCount++;
                }
            }
        }
        return fenceCount * this.plots.size();
    }

    public int getSize() {
        return plots.size();
    }

    public String toString() {
        return "plantType: " + plots.values().stream().toList().getFirst().getPlantType();
    }
}
