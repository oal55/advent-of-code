package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import java.util.List;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;

public class Day12 implements Day {

    @Override
    public Solutions solve() {
        List<String> lines = Input.readLines(this.getClass().getSimpleName());
        return Solutions.of(part1(lines), part2(lines));
    }

    private static long part1(List<String> lines) {
        return lines.stream()
                .mapToLong(line -> new PatternMatcher(line.split(" ")[0], Utils.readIntsFromStr(line)).calcWays())
                .sum();
    }

    private static long part2(List<String> lines) {
        return lines.stream()
                .mapToLong(line -> {
                    String[] parts = line.split(" ");
                    return new PatternMatcher(
                                    unfoldString(parts[0], "?"), Utils.readIntsFromStr(unfoldString(parts[1], ",")))
                            .calcWays();
                })
                .sum();
    }

    private static String unfoldString(String body, String delimiter) {
        return String.join(delimiter, StreamEx.generate(() -> body).limit(5).toList());
    }

    public static class PatternMatcher {
        public int I, J;
        public long[][] memo;
        public final String pattern;
        public final List<Integer> pieces;

        PatternMatcher(String pattern, List<Integer> pieces) {
            this.pattern = pattern;
            this.pieces = pieces;
            this.I = pattern.length();
            this.J = pieces.size();
            this.memo = LongStreamEx.range(I)
                    .mapToObj(i -> LongStreamEx.constant(-1, J).toArray())
                    .toArray(long[][]::new);
        }

        long calcWays() {
            return calcWays(0, 0);
        }

        private long calcWays(int i, int j) {
            if (j == J) {
                return i == I || pattern.indexOf('#', i) == -1 ? 1 : 0;
            }
            if (i == I) {
                return 0;
            }

            if (memo[i][j] != -1) {
                return memo[i][j];
            }

            char currentCharacter = pattern.charAt(i);
            long res =
                    switch (currentCharacter) {
                        case '#' -> canPlace(i, j) ? calcWays(place(i, j), j + 1) : 0;
                        case '.' -> calcWays(findNextNonEmpty(i), j);
                        case '?' -> canPlace(i, j)
                                ? calcWays(i + 1, j) + calcWays(place(i, j), j + 1)
                                : calcWays(i + 1, j);
                        default -> throw new IllegalStateException("Unrecognized character" + currentCharacter);
                    };
            memo[i][j] = res;
            return memo[i][j];
        }

        // this and canPlace is a little awkward? -- maybe throw if cannot place;
        private int place(int i, int j) {
            int iNext = i + pieces.get(j);
            return iNext == I ? iNext : iNext + 1; // we skip one, unless we've reached the end.
        }

        private int findNextNonEmpty(int fromIndex) {
            int i = fromIndex;
            while (i < I && pattern.charAt(i) == '.') {
                ++i;
            }
            return i;
        }

        private boolean canPlace(int i, int j) {
            int blockSize = pieces.get(j);
            if (i + blockSize > I) {
                return false;
            }

            for (int iBlock = 0; iBlock < blockSize; ++iBlock) {
                if (pattern.charAt(i + iBlock) == '.') {
                    return false;
                }
            }
            return (i + blockSize == I
                    || // we've reached the end of pattern, we're happy.
                    pattern.charAt(i + blockSize) != '#'); // if this is a #, that means we placed more than blockSize
        }
    }
}
