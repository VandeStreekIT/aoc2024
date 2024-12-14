package day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Side {
    private List<Integer> start;
    private List<Integer> end;
    private List<List<List<Integer>>> fences = new ArrayList<>();

    public Side(){}

    public Side(List<Integer> start, List<Integer> end) {
        this.start = start;
        this.end = end;
        fences.add(List.of(start, end));
    }

    public Side(List<List<Integer>> fence) {
        this.start = fence.getFirst();
        this.end = fence.getLast();
        this.fences.add(fence);
    }

    public boolean containsFence(List<List<Integer>> fence) {
        return fences.contains(fence);
    }

    public void addFence (List<Integer> start, List<Integer> end) {
        fences.add(List.of(start, end));
    }

    public void addFence(List<List<Integer>> fence) {
        fences.add(fence);
    }

    public List<Integer> getStart() {
        return start;
    }

    public List<Integer> getEnd() {
        return end;
    }

    public void setStart(List<Integer> start) {
        this.start = start;
    }

    public void setEnd(List<Integer> end) {
        this.end = end;
    }
}
