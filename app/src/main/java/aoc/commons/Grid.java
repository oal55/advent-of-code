package aoc.commons;

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

    public EntryStream<Point, Character> entries() {
        return EntryStream.of(matrix)
                .flatMapKeyValue((i, row) -> EntryStream.of(String.copyValueOf(row)
                                .chars()
                                .mapToObj(c -> (char) c)
                                .toList())
                        .mapKeyValue((j, cell) -> Map.entry(Point.of(i, j), cell)))
                .mapToEntry(Map.Entry::getKey, Map.Entry::getValue);
    }
}
