import com.sun.jdi.IntegerType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

record Tuple<x, y>(x row, y column) {}

public class Day6 {
    private final String filePath = "input/day6_example.txt";
    private int part1;
    private int part2;
    private List<Position> visitedPositions;
    private int rowCount;
    private int columnCount;
    private List<List<String>> map;
    private final String OBSTACLE = "#";
    private Position position;

    public Day6() {
        visitedPositions = new ArrayList<Position>();
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

        d.visitedPositions.add(new Position(d.position));

        d.startMoving();

        System.out.println(d.visitedPositions.size());
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
        this.moveUp();
    }

    private void moveUp() {
        String nextPosition = this.map.get(this.position.getRow()-1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() > 0) {
            this.visitedPositions.add(new Position(this.position));
            this.position.setRow(position.getRow()-1);
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveRight();
        }

    }

    private void moveDown() {
        String nextPosition = this.map.get(this.position.getRow()+1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() < this.rowCount - 1) {
            this.visitedPositions.add(new Position(this.position));
            this.position.setRow(position.getRow()+1);
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveLeft();
        }

    }

    private void moveLeft() {
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()-1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() > 0) {
            this.visitedPositions.add(new Position(this.position));
            this.position.setColumn(position.getColumn()-1);
        }
        if (nextPosition.equals(OBSTACLE)) {
            this.moveUp();
        }
    }

    private void moveRight() {
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()+1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() < this.columnCount - 1) {
            this.visitedPositions.add(new Position(this.position));
            this.position.setColumn(position.getColumn()+1);
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