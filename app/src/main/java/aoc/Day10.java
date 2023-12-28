package aoc;

import aoc.commons.Direction;
import aoc.commons.Grid;
import aoc.commons.Input;
import aoc.commons.Point;
import aoc.commons.Solutions;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
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

        return new Solutions(String.valueOf(part1(grid)), String.valueOf(part2(grid)));
    }

    private static int part1(Grid grid) {
        return getLoopThatContainsStart(grid).size() / 2;
    }

    private static int part2(Grid grid) {
        List<Point> loop = getLoopThatContainsStart(grid);
        grid.set(loop.get(loop.size() - 1), getStartPipeFromLoop(loop)); // set start's pipe to actual pipe from 'S'
        normalizeMatrix(grid, loop);

        Grid enlargedGrid = new Grid(enlarge(grid.matrix));
        Set<Point> visited =
                floodFillFromEdges(enlargedGrid); // visited now contains all dots that are outside the loop.
        Set<Point> setLoop = new HashSet<>(loop);
        return (int) enlargedGrid
                .entries()
                .filterKeyValue((p, c) -> c == '.' && !visited.contains(p))
                .map(Map.Entry::getKey)
                .map(p -> Point.of(p.i() / 2, p.j() / 2))
                .filter(p -> !setLoop.contains(p))
                .distinct()
                .count();
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

    private static char getStartPipeFromLoop(List<Point> loop) {
        Point start = loop.get(loop.size() - 1);
        Point prevOfStart = loop.get(loop.size() - 2);
        Point nextOfStart = loop.get(0);
        List<Point> startConnections = List.of(prevOfStart.add(start.reverse()), nextOfStart.add(start.reverse()));
        for (char c : "|-LJ7F".toCharArray()) {
            if (connections(c).containsAll(startConnections)) {
                return c;
            }
        }
        throw new IllegalStateException("fml");
    }

    private static Set<Point> floodFillFromEdges(Grid grid) {
        Queue<Point> queue = new ArrayDeque<>(
                grid.edges().stream().filter(p -> grid.get(p) == '.').toList());
        Set<Point> visited = new HashSet<>(queue);
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            for (Point neighbor : cur.fourNeighbors()) {
                if (grid.valid(neighbor) && visited.add(neighbor) && grid.get(neighbor) == '.') {
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }

    // 2x the grid and add pipes and dashes to not break the loop.
    static char[][] enlarge(char[][] matrix) {
        int I = matrix.length, J = matrix[0].length;
        char[][] enlarged = new char[I * 2][J * 2];
        for (int i = 0; i < I; ++i) {
            Arrays.fill(enlarged[i * 2], '.');
            Arrays.fill(enlarged[i * 2 + 1], '.');
            for (int j = 0; j < J; ++j) {
                enlarged[i * 2 + 1][j * 2 + 1] = matrix[i][j];
                List<Point> cellConnections = connections(matrix[i][j]);
                if (cellConnections.contains(Direction.LEFT)) {
                    enlarged[i * 2 + 1][j * 2] = '-';
                }
                if (cellConnections.contains(Direction.UP)) {
                    enlarged[i * 2][j * 2 + 1] = '|';
                }
            }
        }
        return enlarged;
    }

    // everything that's not on the loop gets turned into '.'
    static void normalizeMatrix(Grid grid, List<Point> loop) {
        Set<Point> loopSet = new HashSet<>(loop);
        grid.entries()
                .filterKeys(p -> !loopSet.contains(p))
                .map(Map.Entry::getKey)
                .forEach(p -> grid.set(p, '.'));
    }
}
