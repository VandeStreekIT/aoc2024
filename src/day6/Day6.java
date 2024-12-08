package day6;

import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 {
    private final String filePath = "input/day6_example.txt";
    private int part1;
    private int part2;
    private List<String> visitedPositions;
    private int rowCount;
    private int columnCount;
    private List<List<String>> map;
    private final String OBSTACLE = "#";
    private Position position;
    private boolean part2Active = false;
    private boolean isLoop = false;
    private Position startPosition;

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

        d.startPosition = d.findStart(input, "^");
        d.position = new Position(d.startPosition);

        d.startMoving();

        Set<String> set = d.visitedPositions.stream().collect(Collectors.toSet());
        d.part1 = set.size();
        System.out.println(d.part1);

        d.part2Active = true;
        set.remove(d.startPosition.toString() + "|up");
        set.remove(d.startPosition.toString() + "|down");
        set.remove(d.startPosition.toString() + "|left");
        set.remove(d.startPosition.toString() + "|right");

        for (String position : set) {
            String coordinate = position.split("\\|")[0].replace("(", "").replace(")", "");

            int row = Integer.parseInt(coordinate.split(", ")[0]);
            int column = Integer.parseInt(coordinate.split(", ")[1]);

            d.map.get(row).set(column, "#");

            d.startMoving();
            if (d.isLoop) d.part2++;

            // Restore
            d.map.get(row).set(column, ".");
            d.isLoop = false;
            d.position = d.startPosition;

        }

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
//        this.visitedPositions.add(this.position.toString());
        this.moveUp();
    }

    private void moveUp() {
        if (isLoop) {return;}
        String nextPosition = this.map.get(this.position.getRow()-1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() > 0) {
            this.position.setRow(position.getRow()-1);
            if (this.visitedPositions.contains(this.position.toString() + "|up") && this.part2Active) {
                isLoop = true;
                return;
            }
            this.visitedPositions.add(this.position.toString() + "|up");
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
        if (isLoop) {return;}
        String nextPosition = this.map.get(this.position.getRow()+1).get(this.position.getColumn());

        while (!nextPosition.equals(OBSTACLE) && position.getRow() < this.rowCount - 1) {
            this.position.setRow(position.getRow()+1);
            if (this.visitedPositions.contains(this.position.toString() + "|down") && this.part2Active) {
                isLoop = true;
                return;
            }
            this.visitedPositions.add(this.position.toString() + "|down");
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
        if (isLoop) {return;}
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()-1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() > 0) {
            this.position.setColumn(position.getColumn()-1);
            if (this.visitedPositions.contains(this.position.toString() + "|left") && this.part2Active) {
                isLoop = true;
                return;
            }
            this.visitedPositions.add(this.position.toString() + "|left");
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
        if (isLoop) {return;}
        String nextPosition = this.map.get(this.position.getRow()).get(this.position.getColumn()+1);

        while (!nextPosition.equals(OBSTACLE) && position.getColumn() < this.columnCount - 1) {
            this.position.setColumn(position.getColumn()+1);
            if (this.visitedPositions.contains(this.position.toString() + "|right") && this.part2Active) {
                isLoop = true;
                return;
            }
            this.visitedPositions.add(this.position.toString() + "|right");
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