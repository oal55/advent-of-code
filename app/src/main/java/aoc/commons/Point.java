package aoc.commons;

import java.util.List;

public record Point(int i, int j) {
    public static Point of(int i, int j) {
        return new Point(i, j);
    }

    public Point add(Point dir) {
        return new Point(i + dir.i, j + dir.j);
    }

    public List<Point> fourNeighbors() {
        return Direction.FOUR_WAYS.stream().map(this::add).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Point other)) return false;
        return i == other.i && j == other.j;
    }

    @Override
    public int hashCode() {
        return 1009 * i + j;
    }
}
