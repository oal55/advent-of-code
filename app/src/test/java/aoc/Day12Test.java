package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day12Test {

    @Test
    void test1() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher("???.###", List.of(1, 1, 3));
        assertThat(matcher.calcWays()).isEqualTo(1);
    }

    @Test
    void test2() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher(".??..??...?##.", List.of(1, 1, 3));
        assertThat(matcher.calcWays()).isEqualTo(4);
    }

    @Test
    void test3() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher("?#?#?#?#?#?#?#?", List.of(1, 3, 1, 6));
        assertThat(matcher.calcWays()).isEqualTo(1);
    }

    @Test
    void test4() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher("????.#...#...", List.of(4, 1, 1));
        assertThat(matcher.calcWays()).isEqualTo(1);
    }

    @Test
    void test5() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher("????.######..#####", List.of(1, 6, 5));
        assertThat(matcher.calcWays()).isEqualTo(4);
    }

    @Test
    void test6() {
        Day12.PatternMatcher matcher = new Day12.PatternMatcher("?###????????", List.of(3, 2, 1));
        assertThat(matcher.calcWays()).isEqualTo(10);
    }
}
