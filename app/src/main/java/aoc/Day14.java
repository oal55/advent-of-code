package aoc;

import static aoc.commons.Utils.transposed;

import aoc.commons.Input;
import aoc.commons.Solutions;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import one.util.streamex.IntStreamEx;

public class Day14 implements Day {
    @Override
    public Solutions solve() {
        char[][] matrix = Input.readLines(this.getClass().getSimpleName()).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        List<String> betterLines =
                Arrays.stream(transposed(matrix)).map(String::copyValueOf).toList();
        return Solutions.of(part1(betterLines), "fml");
    }

    private static int part1(List<String> betterLines) {
        return betterLines.stream().map(Day14::shifted).mapToInt(Day14::score).sum();
    }

    private static int score(String landscape) {
        return IntStreamEx.of(landscape.chars())
                .boxed()
                .zipWith(IntStreamEx.range(1, landscape.length() + 1).reverseSorted())
                .filterKeys(character -> character == 'O')
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    static String shifted(String landscape) {
        char[] chars = landscape.toCharArray();
        int iWrite = 0;
        for (int i = 0; i < chars.length; ++i) {
            switch (chars[i]) {
                case 'O' -> swap(chars, iWrite++, i);
                case '#' -> iWrite = i + 1;
                default -> {}
            }
        }
        return String.valueOf(chars);
    }

    private static void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }
}
