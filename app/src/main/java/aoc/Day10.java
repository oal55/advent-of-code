package aoc;

import aoc.commons.Direction;
import aoc.commons.Grid;
import aoc.commons.Input;
import aoc.commons.Point;
import aoc.commons.Solutions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import one.util.streamex.StreamEx;

public class Day10 implements Day {

    private static List<Point> connections(char cell) {
        return switch (cell) {
            case '|' -> List.of(Direction.UP, Direction.DOWN);
            case '-' -> List.of(Direction.LEFT, Direction.RIGHT);
            case 'L' -> List.of(Direction.UP, Direction.RIGHT);
            case 'J' -> List.of(Direction.UP, Direction.LEFT);
            case '7' -> List.of(Direction.DOWN, Direction.LEFT);
            case 'F' -> List.of(Direction.DOWN, Direction.RIGHT);
            case '.' -> List.of();
            case 'S' -> Direction.FOUR_WAYS;
            default -> throw new IllegalArgumentException("Unrecognized cell: " + cell); // 何でだ？！
        };
    }

    @Override
    public Solutions solve() {
        Grid grid = new Grid(Input.readLines(this.getClass().getSimpleName()).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new));

        return new Solutions(String.valueOf(part1(grid)), String.valueOf("fml"));
    }

    private static int part1(Grid grid) {
        return getLoopThatContainsStart(grid).size() / 2;
    }

    private static List<Point> getLoopThatContainsStart(Grid grid) {
        Point startPos = findStart(grid);
        List<Point> potentialLoopStarts = startPos.fourNeighbors().stream()
                .filter(grid::valid)
                .filter(p -> StreamEx.of(connections(grid.get(p))).map(p::add).has(startPos))
                .toList();
        return potentialLoopStarts.stream()
                .map(p -> tryGetLoopThatContainsStart(grid, startPos, p))
                .filter(l -> !l.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No loop found?!"));
    }

    private static List<Point> tryGetLoopThatContainsStart(Grid grid, Point start, Point cur) {
        List<Point> pointsInLoop = new ArrayList<>();
        pointsInLoop.add(cur);

        Point prevPoint = start;
        while (!cur.equals(start)) {
            Optional<Point> maybeNext = getNextPoint(grid, cur, prevPoint);
            // if this is empty, it means we've reached a dead end.
            if (maybeNext.isEmpty()) {
                return List.of();
            }
            prevPoint = cur;
            cur = maybeNext.get();
            pointsInLoop.add(cur);
        }
        return pointsInLoop;
    }

    private static Optional<Point> getNextPoint(Grid grid, Point cur, Point prev) {
        return StreamEx.of(connections(grid.get(cur)))
                .map(cur::add)
                .filter(grid::valid)
                .filter(p -> !p.equals(prev))
                .findFirst();
    }

    private static Point findStart(Grid grid) {
        return grid.entries()
                .filterValues(cell -> cell == 'S')
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("No start found?"));
    }
}
