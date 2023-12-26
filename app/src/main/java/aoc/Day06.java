package aoc;

import aoc.Day;
import aoc.commons.Input;
import aoc.commons.Solutions;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.regex.Matcher;

public class Day06 implements Day {

    @Override
    public Solutions solve() {
        List<String> lines = Input.readLines(this.getClass().getSimpleName());
        return new Solutions(String.valueOf(part1(lines)), String.valueOf(part2(lines)));
    }

    static long part1(List<String> lines) {
        return multiplyNumWinningWaysForRaces(parseRaces(lines.get(0), lines.get(1)));
    }

    static long part2(List<String> lines) {
        return multiplyNumWinningWaysForRaces(parseRaces(
                lines.get(0).replaceAll("\\s", ""),
                lines.get(1).replaceAll("\\s", "")));
    }

    record Race(long time, long distance) {}

    private static long multiplyNumWinningWaysForRaces(List<Race> races) {
        return races.stream()
                .map(Day06::numWaysToWin)
                .reduce(1L, (a, b) -> a * b);
    }

    private static long numWaysToWin(Race race) {
        long lo = 0, hi = race.time() / 2;
        while (lo <= hi) {
            long mid = (hi + lo) / 2;
            if (mid * (race.time() - mid) > race.distance) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        long minSeconds = hi + 1; // minimum charge required to win
        return race.time() - 2 * minSeconds + 1;
    }

    private static List<Race> parseRaces(String timeString, String distanceString) {
        Pattern pattern = Pattern.compile("\\w+:\\s*(?<numbers>.*)");
        Matcher timeMatcher = pattern.matcher(timeString);
        Matcher disMatcher = pattern.matcher(distanceString);

        if (!timeMatcher.matches() || !disMatcher.matches()) {
            throw new RuntimeException("Bad input");
        }

        List<Long> times = getNumbers(timeMatcher.group("numbers"));
        List<Long> distances = getNumbers(disMatcher.group("numbers"));
        return IntStream.range(0, times.size()).boxed()
            .map(i -> new Race(times.get(i), distances.get(i)))
            .toList();
    }

    private static List<Long> getNumbers(String numbers) {
        return Arrays.stream(numbers.split(" "))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .toList();
    }
}
