package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
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
        List<Long> colDists = cumulativeColumnDistances(transpose(matrix), expansionFactor);
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

    private static char[][] transpose(char[][] matrix) {
        int I = matrix.length, J = matrix[0].length;
        char[][] transposed = new char[J][I];
        for (int i = 0; i < I; ++i) {
            for (int j = 0; j < J; ++j) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    private static List<Coordinate> getGalaxyCoordinates(char[][] matrix) {
        int I = matrix.length, J = matrix[0].length;
        return IntStreamEx.range(I)
                .flatMapToObj(i ->
                        IntStreamEx.range(J).filter(j -> matrix[i][j] == '#').mapToObj(j -> new Coordinate(i, j)))
                .toList();
    }

    record Coordinate(int i, int j) {}
}
