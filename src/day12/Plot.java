package day12;

import java.util.Arrays;
import java.util.List;

public class Plot {
    final private List<Integer> location;
    final private String plantType;
    private Region region;

    public Plot(List<Integer> location, String plantType) {
        this.location = location;
        this.plantType = plantType;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public List<Integer> getLocation() {
        return location;
    }

    public String toString() {
        return "Plot [location=" + location + ", plantType=" + plantType + "]";
    }
}
