import util.Util;

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
    private List<String> posibleObstacles;
    private int rowCount;
    private int columnCount;
    private List<List<String>> map;
    private final String OBSTACLE = "#";
    private Position position;
    private boolean isLoop = false;
    private boolean extraObstaclePlaced = false;
    private List<String> tempVisitedPositions;

    public Day6() {
        visitedPositions = new ArrayList<String>();
        posibleObstacles = new ArrayList<>();
        tempVisitedPositions = new ArrayList<>();
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

//        Set<String> set = d.visitedPositions.stream().collect(Collectors.toSet());
//        d.part1 = set.size();
//        System.out.println(d.part1);

        Set<String> set2 = d.posibleObstacles.stream().collect(Collectors.toSet());
        d.part2 = set2.size();
        System.out.println(d.posibleObstacles.size());
        System.out.println(d.part2);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Uitvoeringstijd tot part 1: " + elapsedTime / 1_000_000 + " milliseconds");
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
        this.visitedPositions.add(this.position.toString() + "|up");
        this.moveUp();
    }

    private void moveUp() {
        String nextPosition = this.map.get(this.position.getRow()-1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && !this.isLoop) {
            if (!this.extraObstaclePlaced) {
                this.extraObstaclePlaced = true;
                int row = this.position.getRow();
                int column = this.position.getColumn();
                this.map.get(this.position.getRow()-1).set(this.position.getColumn(), OBSTACLE);
                this.moveRight();
                this.position.setRow(row);
                this.position.setColumn(column);
                this.map.get(this.position.getRow()-1).set(this.position.getColumn(), ".");
                if (isLoop && !this.visitedPositions.contains(this.position.toString() + "|up")) {
                    posibleObstacles.add("(" + (row - 1) + ", " + column + ")");
                }
                this.extraObstaclePlaced = false;
                this.isLoop = false;
                this.tempVisitedPositions.clear();
            }
            this.position.setRow(position.getRow()-1);
            if (visitedPositions.contains(this.position.toString() + "|up") ||
                    tempVisitedPositions.contains(this.position.toString() + "|up")) {
                this.isLoop = true;
                break;
            }
            if (!this.extraObstaclePlaced) {
                this.visitedPositions.add(this.position.toString() + "|up");
            } else {
                this.tempVisitedPositions.add(this.position.toString() + "|up");
            }
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

        while (!nextPosition.equals(OBSTACLE) && !this.isLoop) {
            if (!this.extraObstaclePlaced) {
                this.extraObstaclePlaced = true;
                int row = this.position.getRow();
                int column = this.position.getColumn();
                this.map.get(this.position.getRow()+1).set(this.position.getColumn(), OBSTACLE);
                this.moveLeft();
                this.position.setRow(row);
                this.position.setColumn(column);
                this.map.get(this.position.getRow()+1).set(this.position.getColumn(), ".");
                    if (isLoop && !this.visitedPositions.contains(this.position.toString() + "|down")) {
                        posibleObstacles.add("(" + (row + 1) + ", " + column + ")");
                    }
                this.extraObstaclePlaced = false;
                this.isLoop = false;
                this.tempVisitedPositions.clear();
            }
            this.position.setRow(position.getRow()+1);
            if (visitedPositions.contains(this.position.toString() + "|down") ||
                    tempVisitedPositions.contains(this.position.toString() + "|down")) {
                this.isLoop = true;
                break;
            }
            if (!this.extraObstaclePlaced) {
                this.visitedPositions.add(this.position.toString() + "|down");
            } else {
                this.tempVisitedPositions.add(this.position.toString() + "|down");
            }
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

        while (!nextPosition.equals(OBSTACLE) && !this.isLoop) {
            if (!this.extraObstaclePlaced) {
                this.extraObstaclePlaced = true;
                int row = this.position.getRow();
                int column = this.position.getColumn();
                this.map.get(this.position.getRow()).set(this.position.getColumn()-1, OBSTACLE);
                this.moveUp();
                this.position.setRow(row);
                this.position.setColumn(column);
                this.map.get(this.position.getRow()).set(this.position.getColumn()-1, ".");
                if (isLoop && !this.visitedPositions.contains(this.position.toString() + "|left")) {
                    posibleObstacles.add("(" + row + ", " + (column - 1) + ")");
                }
                this.extraObstaclePlaced = false;
                this.isLoop = false;
                this.tempVisitedPositions.clear();
            }
            this.position.setColumn(position.getColumn()-1);
            if (visitedPositions.contains(this.position.toString() + "|left") ||
                    tempVisitedPositions.contains(this.position.toString() + "|left")) {
                this.isLoop = true;
                break;
            }
            if (!this.extraObstaclePlaced) {
                this.visitedPositions.add(this.position.toString() + "|left");
            } else {
                this.tempVisitedPositions.add(this.position.toString() + "|left");
            }
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

        while (!nextPosition.equals(OBSTACLE) && !this.isLoop) {
            if (!this.extraObstaclePlaced) {
                this.extraObstaclePlaced = true;
                int row = this.position.getRow();
                int column = this.position.getColumn();
                this.map.get(this.position.getRow()).set(this.position.getColumn()+1, OBSTACLE);
                this.moveDown();
                this.position.setRow(row);
                this.position.setColumn(column);
                this.map.get(this.position.getRow()).set(this.position.getColumn()+1, ".");
                if (isLoop && !this.visitedPositions.contains(this.position.toString() + "|right")) {
                    posibleObstacles.add("(" + row + ", " + (column + 1) + ")");
                }
                this.extraObstaclePlaced = false;
                this.isLoop = false;
                this.tempVisitedPositions.clear();
            }
            this.position.setColumn(position.getColumn()+1);
            if (visitedPositions.contains(this.position.toString() + "|right") ||
                    tempVisitedPositions.contains(this.position.toString() + "|right")) {
                this.isLoop = true;
                break;
            }
            if (!this.extraObstaclePlaced) {
                this.visitedPositions.add(this.position.toString() + "|right");
            } else {
                this.tempVisitedPositions.add(this.position.toString() + "|right");
            }
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