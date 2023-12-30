package aoc;

import aoc.commons.Input;
import aoc.commons.Point;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import java.util.List;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class Day11 implements Day {

    @Override
    public Solutions solve() {
        char[][] matrix = Input.readLines(this.getClass().getSimpleName()).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return new Solutions(
                String.valueOf(calculateSumOfDistances(matrix, 2)),
                String.valueOf(calculateSumOfDistances(matrix, 1_000_000)));
    }

    private static long calculateSumOfDistances(char[][] matrix, long expansionFactor) {
        List<Long> rowDists = cumulativeColumnDistances(matrix, expansionFactor);
        List<Long> colDists = cumulativeColumnDistances(Utils.transposed(matrix), expansionFactor);
        return StreamEx.ofPairs(
                        getGalaxyCoordinates(matrix),
                        (p, q) -> getLinearDistance(p.j(), q.j(), colDists) + getLinearDistance(p.i(), q.i(), rowDists))
                .mapToLong(Long::longValue)
                .sum();
    }

    private static List<Long> cumulativeColumnDistances(char[][] matrix, long expansionFactor) {
        return StreamEx.of(matrix)
                .map(row -> IntStreamEx.of(row).has('#') ? 1L : expansionFactor)
                .scanLeft(Long::sum);
    }

    private static long getLinearDistance(int coordinate1, int coordinate2, List<Long> cumulativeDists) {
        return Math.abs(cumulativeDists.get(coordinate1) - cumulativeDists.get(coordinate2));
    }

    private static List<Point> getGalaxyCoordinates(char[][] matrix) {
        int I = matrix.length, J = matrix[0].length;
        return IntStreamEx.range(I)
                .flatMapToObj(i ->
                        IntStreamEx.range(J).filter(j -> matrix[i][j] == '#').mapToObj(j -> Point.of(i, j)))
                .toList();
    }
}
