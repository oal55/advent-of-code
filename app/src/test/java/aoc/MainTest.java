package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import aoc.commons.Solutions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testDay01() {
        Solutions solutions = new Day01().solve();
        assertThat(solutions.part1()).isEqualTo("55029");
        assertThat(solutions.part2()).isEqualTo("55686");
    }

    @Test
    void testDay02() {
        Solutions solutions = new Day02().solve();
        assertThat(solutions.part1()).isEqualTo("2447");
        assertThat(solutions.part2()).isEqualTo("56322");
    }

    @Test
    void testDay03() {
        Solutions solutions = new Day03().solve();
        assertThat(solutions.part1()).isEqualTo("546563");
        assertThat(solutions.part2()).isEqualTo("91031374");
    }

    @Test
    void testDay04() {
        Solutions solutions = new Day04().solve();
        assertThat(solutions.part1()).isEqualTo("15268");
        assertThat(solutions.part2()).isEqualTo("6283755");
    }

    @Test
    void testDay05() {
        Solutions solutions = new Day05().solve();
        assertThat(solutions.part1()).isEqualTo("240320250");
        assertThat(solutions.part2()).isEqualTo("28580589");
    }

    @Test
    void testDay06() {
        Solutions solutions = new Day06().solve();
        assertThat(solutions.part1()).isEqualTo("1413720");
        assertThat(solutions.part2()).isEqualTo("30565288");
    }

    @Test
    void testDay07() {
        Solutions solutions = new Day07().solve();
        assertThat(solutions.part1()).isEqualTo("248179786");
        assertThat(solutions.part2()).isEqualTo("247885995");
    }

    @Test
    void testDay08() {
        Solutions solutions = new Day08().solve();
        assertThat(solutions.part1()).isEqualTo("15871");
        assertThat(solutions.part2()).isEqualTo("11283670395017");
    }

    @Test
    void testDay09() {
        Solutions solutions = new Day09().solve();
        assertThat(solutions.part1()).isEqualTo("2038472161");
        assertThat(solutions.part2()).isEqualTo("1091");
    }

    @Test
    void testDay10() {
        Solutions solutions = new Day10().solve();
        assertThat(solutions.part1()).isEqualTo("6725");
        assertThat(solutions.part2()).isEqualTo("fml");
    }

    @Test
    void testDay11() {
        Solutions solutions = new Day11().solve();
        assertThat(solutions.part1()).isEqualTo("9686930");
        assertThat(solutions.part2()).isEqualTo("630728425490");
    }
}
