package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import aoc.commons.Grid;
import aoc.commons.Point;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day10Test {

    @Test
    void testEnlarge1() {
        char[][] matrix = {
            ".....".toCharArray(),
            ".S-7.".toCharArray(),
            ".|.|.".toCharArray(),
            ".L-J.".toCharArray(),
            ".....".toCharArray(),
        };
        char[][] enlarged = {
            "..........".toCharArray(),
            "..........".toCharArray(),
            "...|......".toCharArray(),
            "..-S---7..".toCharArray(),
            "...|...|..".toCharArray(),
            "...|...|..".toCharArray(),
            "...|...|..".toCharArray(),
            "...L---J..".toCharArray(),
            "..........".toCharArray(),
            "..........".toCharArray()
        };
        assertThat(Day10.enlarge(matrix)).isEqualTo(enlarged);
    }

    @Test
    void testNormalize() {
        Grid grid = makeGrid(List.of("|||", "|||", "LLL"));
        List<Point> loop = List.of(Point.of(1, 1));
        Day10.normalizeMatrix(grid, loop);
        assertThat(grid.matrix).isEqualTo(new char[][] {"...".toCharArray(), ".|.".toCharArray(), "...".toCharArray()});
    }

    private Grid makeGrid(List<String> lines) {
        char[][] matrix = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        return new Grid(matrix);
    }
}
