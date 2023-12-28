package aoc.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import one.util.streamex.EntryStream;

public class Grid {

    public final int I, J;
    public final char[][] matrix;

    public Grid(char[][] matrix) {
        I = matrix.length;
        J = matrix[0].length;
        this.matrix = matrix.clone();
    }

    public boolean valid(Point p) {
        return p.i() >= 0 && p.i() < I && p.j() >= 0 && p.j() < J;
    }

    public char get(Point p) {
        return matrix[p.i()][p.j()];
    }

    public void set(Point p, char value) {
        matrix[p.i()][p.j()] = value;
    }

    public EntryStream<Point, Character> entries() {
        return EntryStream.of(matrix)
                .flatMapKeyValue((i, row) -> EntryStream.of(String.copyValueOf(row)
                                .chars()
                                .mapToObj(c -> (char) c)
                                .toList())
                        .mapKeyValue((j, cell) -> Map.entry(Point.of(i, j), cell)))
                .mapToEntry(Map.Entry::getKey, Map.Entry::getValue);
    }

    public List<Point> edges() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < I; ++i) {
            points.add(Point.of(i, 0));
            points.add(Point.of(i, J - 1));
        }
        for (int j = 1; j < J - 1; ++j) {
            points.add(Point.of(0, j));
            points.add(Point.of(I - 1, j));
        }
        return points;
    }
}
