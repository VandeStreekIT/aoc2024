package day12;

import javax.swing.plaf.metal.MetalLookAndFeel;
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

    public int getBulkPrice(Area area) {
        List<Side> sides = new ArrayList<>();
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (Plot plot : plots.values()) {
            for (int[] direction : directions) {
                Side newSide;
                boolean isBoarderPlot = false;
                List<Integer> neighbourLocation = List.of(plot.getLocation().get(0) + direction[0], plot.getLocation().get(1) + direction[1]);
                if (neighbourLocation.getFirst() < 0 || neighbourLocation.getLast() < 0 ||
                        neighbourLocation.getFirst() >= area.getSize()[0] || neighbourLocation.getLast() >= area.getSize()[1]) {
                    isBoarderPlot = true;
                } else if (plots.containsValue(area.getPlot(neighbourLocation))) {
                    continue;
                }
                List<List<Integer>> fence;
                if (direction[0] == 0 && direction[1] == 1) {
                    fence = List.of(
                            List.of(plot.getLocation().getFirst(), plot.getLocation().getLast() + 1),
                            List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast() + 1));
                } else if (direction[0] == 1 && direction[1] == 0) {
                    fence = List.of(
                            List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast()),
                            List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast() + 1));
                } else if (direction[0] == -1 && direction[1] == 0) {
                    fence = List.of(
                            List.of(plot.getLocation().getFirst(), plot.getLocation().getLast()),
                            List.of(plot.getLocation().getFirst(), plot.getLocation().getLast() + 1));
                } else { //(direction[0] == 0 && direction[1] == -1)
                    fence = List.of(
                            List.of(plot.getLocation().getFirst(), plot.getLocation().getLast()),
                            List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast()));
                }

                Side existingSide = null;
                for (Side side : sides) {
                    if (side.containsFence(fence)) {
                        existingSide = side;
                        break;
                    }
                }
                if (existingSide != null) continue;

                // nieuwe side!
                newSide = new Side();
                sides.add(newSide);
                for (int i : List.of(-1,1)){
                    this.exploreFence(plot, direction, newSide, this, area, i, isBoarderPlot);
                }
            }
        }
        return this.plots.size() * sides.size();
    }

    private void exploreFence(Plot plot, int[] direction, Side side, Region region, Area area, int i, boolean isBoarderPlot) {
        //
        List<List<Integer>> potentialFence = null;

        if (direction[0] == 0 && direction[1] == 1) {
            potentialFence = List.of(
                    List.of(plot.getLocation().getFirst(), plot.getLocation().getLast() + 1),
                    List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast() + 1));
        } else if (direction[0] == 1 && direction[1] == 0) {
            potentialFence = List.of(
                    List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast()),
                    List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast() + 1));
        } else if (direction[0] == -1 && direction[1] == 0) {
            potentialFence = List.of(
                    List.of(plot.getLocation().getFirst(), plot.getLocation().getLast()),
                    List.of(plot.getLocation().getFirst(), plot.getLocation().getLast() + 1));
        } else { //(direction[0] == 0 && direction[1] == -1)
            potentialFence = List.of(
                    List.of(plot.getLocation().getFirst(), plot.getLocation().getLast()),
                    List.of(plot.getLocation().getFirst() + 1, plot.getLocation().getLast()));
        }
        if (!region.plots.containsValue(plot)) return;

        List<Integer> fancePlotLocation;
        Plot fencePlot;
        if (isBoarderPlot){
            if (!side.containsFence(potentialFence)) side.addFence(potentialFence);
        } else {
            fancePlotLocation = List.of(plot.getLocation().get(0) + direction[0], plot.getLocation().get(1) + direction[1]);
            fencePlot = area.getPlot(fancePlotLocation);
            if (region.plots.containsValue(fencePlot)) return;
            if (!side.containsFence(potentialFence)) side.addFence(potentialFence);
        }

        List<Integer> nextPlotLocation;
        if (direction[0] == 0) {
            nextPlotLocation = List.of(plot.getLocation().get(0) + i, plot.getLocation().get(1));
        } else { // direction[1] == 0
            nextPlotLocation = List.of(plot.getLocation().get(0), plot.getLocation().get(1) + i);
        }

        if (nextPlotLocation.getFirst() < 0 ||
                nextPlotLocation.getLast() < 0 ||
                nextPlotLocation.getFirst() >= area.getSize()[0] ||
                nextPlotLocation.getLast() >= area.getSize()[1]) return;

        Plot nextPlot = area.getPlot(nextPlotLocation);
        exploreFence(nextPlot, direction, side, region, area, i, isBoarderPlot);

    }

    public int getSize() {
        return plots.size();
    }

    public String toString() {
        return "plantType: " + plots.values().stream().toList().getFirst().getPlantType();
    }
}
