import com.sun.jdi.IntegerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record Tuple<x, y>(x row, y column) {}

public class Day6 {
    private final String filePath = "input/day6.txt";
    private int part1;
    private int part2;
    private List<String> visitedPositions;
    private int rowCount;
    private int columnCount;
    private List<List<String>> map;
    private final String OBSTACLE = "#";
    private Position position;

    public Day6() {
        visitedPositions = new ArrayList<String>();
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Day6 d = new Day6();

        List<String> input = Util.readFileAsList(d.filePath);

        d.map = input.stream()
                .map(str -> str.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.toList()))
                .toList();

        d.rowCount = d.map.size();
        d.columnCount = d.map.getFirst().size();

        d.position = d.findStart(input, "^");

        d.startMoving();

        Set<String> set = d.visitedPositions.stream().collect(Collectors.toSet());
        d.part1 = set.size();
        System.out.println(d.part1);
    }


    private Position findStart(List<String> input, String startChar) {
        int row = 0;
        int column = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).contains(startChar)) {
                row = i;
                break;
            }
        }
        column = input.get(row).indexOf(startChar);
        return position = new Position(row, column);
    }

    private void startMoving() {
        this.visitedPositions.add(this.position.toString());
        this.moveUp();
    }

    private void moveUp() {
        String nextPosition = this.map.get(this.position.getRow()-1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() > 0) {
            this.position.setRow(position.getRow()-1);
            this.visitedPositions.add(this.position.toString());
            if (this.position.getRow() == 0){
                break;
            }
            nextPosition = this.map.get(this.position.getRow()-1).get(this.position.getColumn());
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveRight();
        }

    }

    private void moveDown() {
        String nextPosition = this.map.get(this.position.getRow()+1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() < this.rowCount - 1) {
            this.position.setRow(position.getRow()+1);
            this.visitedPositions.add(this.position.toString());
            if (this.position.getRow() == this.rowCount - 1) {
                break;
            }
            nextPosition = this.map.get(this.position.getRow()+1).get(this.position.getColumn());
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveLeft();
        }

    }

    private void moveLeft() {
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()-1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() > 0) {
            this.position.setColumn(position.getColumn()-1);
            this.visitedPositions.add(this.position.toString());
            if (this.position.getColumn() == 0) {
                break;
            }
            nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()-1);
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveUp();
        }
    }

    private void moveRight() {
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()+1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() < this.columnCount - 1) {
            this.position.setColumn(position.getColumn()+1);
            this.visitedPositions.add(this.position.toString());
            if (this.position.getColumn() == this.columnCount - 1) {
                break;
            }
            nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()+1);
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveDown();
        }
    }
}

class Position {
    private int row;
    private int column;

    Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(Position position) {
        this.row = position.row;
        this.column = position.column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
}