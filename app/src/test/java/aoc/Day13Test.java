package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day13Test {
    private static final char[][] MATRIX_1 = {
        "#.##..##.".toCharArray(),
        "..#.##.#.".toCharArray(),
        "##......#".toCharArray(),
        "##......#".toCharArray(),
        "..#.##.#.".toCharArray(),
        "..##..##.".toCharArray(),
        "#.#.##.#.".toCharArray(),
    };
    private static final char[][] MATRIX_2 = {
        "#...##..#".toCharArray(),
        "#....#..#".toCharArray(),
        "..##..###".toCharArray(),
        "#####.##.".toCharArray(),
        "#####.##.".toCharArray(),
        "..##..###".toCharArray(),
        "#....#..#".toCharArray(),
    };

    @Test
    void summarizeReflections() {
        assertThat(Day13.summarizeReflections(MATRIX_1, Day13.ReflectionStatus.REFLECTS_PERFECTLY))
                .isEqualTo(5);
        assertThat(Day13.summarizeReflections(MATRIX_2, Day13.ReflectionStatus.REFLECTS_PERFECTLY))
                .isEqualTo(400);
        assertThat(Day13.summarizeReflections(MATRIX_1, Day13.ReflectionStatus.REFLECTS_WITH_SMUDGE))
                .isEqualTo(300);
        assertThat(Day13.summarizeReflections(MATRIX_2, Day13.ReflectionStatus.REFLECTS_WITH_SMUDGE))
                .isEqualTo(100);
    }
}
