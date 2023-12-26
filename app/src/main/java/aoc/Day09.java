package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import java.util.ArrayList;
import java.util.List;

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
                .mapToLong(numbers -> extrapolate(Utils.reversed(numbers)))
                .sum();
    }

    static long extrapolate(List<Long> numbers) {
        if (numbers.stream().allMatch(num -> num == 0)) {
            return 0;
        }
        return extrapolate(adjDifference(numbers)) + numbers.get(numbers.size() - 1);
    }

    static List<Long> adjDifference(List<Long> nums) {
        List<Long> adjacentDifferences = new ArrayList<>();
        for (int i = 1; i < nums.size(); i++) {
            adjacentDifferences.add(nums.get(i) - nums.get(i - 1));
        }
        return adjacentDifferences;
    }
}
