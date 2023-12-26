package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import com.google.common.collect.Lists;
import java.util.List;
import one.util.streamex.StreamEx;

public class Day09 implements Day {

    @Override
    public Solutions solve() {
        List<String> lines = Input.readLines(this.getClass().getSimpleName());
        return new Solutions(String.valueOf(part1(lines)), String.valueOf(part2(lines)));
    }

    static long part1(List<String> lines) {
        return lines.stream()
                .map(Utils::readLongsFromStr)
                .mapToLong(Day09::extrapolate)
                .sum();
    }

    static long part2(List<String> lines) {
        return lines.stream()
                .map(Utils::readLongsFromStr)
                .mapToLong(numbers -> extrapolate(Lists.reverse(numbers)))
                .sum();
    }

    static long extrapolate(List<Long> numbers) {
        if (numbers.stream().allMatch(num -> num == 0)) {
            return 0;
        }
        List<Long> differences =
                StreamEx.of(numbers).pairMap((left, right) -> right - left).toList();
        return extrapolate(differences) + numbers.get(numbers.size() - 1);
    }
}
