package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    public void testShifted1() {
        assertThat(Day14.shifted("OO.O.O..##")).isEqualTo("OOOO....##");
        assertThat(Day14.shifted("...OO....O")).isEqualTo("OOO.......");
        assertThat(Day14.shifted(".O.#......")).isEqualTo("O..#......");
        assertThat(Day14.shifted(".O.#......")).isEqualTo("O..#......");
        assertThat(Day14.shifted(".#.O......")).isEqualTo(".#O.......");
        assertThat(Day14.shifted("#.#..O#.##")).isEqualTo("#.#O..#.##");
        assertThat(Day14.shifted("..#...O.#.")).isEqualTo("..#O....#.");
        assertThat(Day14.shifted("....O#.O#.")).isEqualTo("O....#O.#.");
        assertThat(Day14.shifted("....#.....")).isEqualTo("....#.....");
        assertThat(Day14.shifted(".#.O.#O...")).isEqualTo(".#O..#O...");
    }
}
