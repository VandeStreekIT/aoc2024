package day12;

import java.util.*;

class Area {
    private Map<List<Integer>, Plot> area;
    private int[] size;
    private List<Plot> plots = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();

    public Area(List<String> input) {
        this.area = parseInputToAreaAndPlots(input);
        this.size = new int[]{input.size(), input.getFirst().length()};
        this.findRegions();
    }

    private Map<List<Integer>, Plot> parseInputToAreaAndPlots(List<String> input) {
        Map<List<Integer>, Plot> area = new HashMap<>();
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(0).length(); col++) {
                List<Integer> location = Arrays.asList(row, col);
                String plantType = String.valueOf(input.get(row).charAt(col));
                Plot plot = new Plot(location, plantType);
                area.put(location, plot);
                this.plots.add(plot);
            }
        }
        return area;
    }

    private void findRegions() {
        for (Plot plot : this.plots) {
            if (plot.getRegion() != null) continue;
            Region region = new Region(plot);
            plot.setRegion(region);
            this.regions.add(region);
            exploreRegion(plot, new ArrayList<Plot>(), size);
        }
        return;
    }

    private void exploreRegion(Plot plot, List<Plot> visitedPlots, int[] size) {
        visitedPlots.add(plot);
        // find neighbour
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] direction : directions) {
            Plot neighbourPlot = null;
            Integer[] neighbourLocation = {plot.getLocation().get(0) + direction[0], plot.getLocation().get(1) + direction[1]};
            if (!(neighbourLocation[0] >= 0 && neighbourLocation[1] >= 0 &&
                    neighbourLocation[0] < size[0] && neighbourLocation[1] < size[1])) continue;
            neighbourPlot = this.getPlot(List.of(neighbourLocation));
            if (!neighbourPlot.getPlantType().equals(plot.getPlantType())) continue;
            if (neighbourPlot.getRegion() != null) continue;
            neighbourPlot.setRegion(plot.getRegion());
            plot.getRegion().addPlot(neighbourPlot);
            exploreRegion(neighbourPlot, visitedPlots, size);
        }
        return;
    }

    public int calculatePrice() {
        int price = 0;
        for (Region region : regions) {
            price += region.getPrice(this);
        }
        return price;
    }

    public Plot getPlot(List<Integer> location) {
        return area.get(location);
    }

    public int[] getSize() {
        return size;
    }
}
