package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import java.util.List;
import one.util.streamex.IntStreamEx;

public class Day12 implements Day {

    @Override
    public Solutions solve() {
        List<String> lines = Input.readLines(this.getClass().getSimpleName());
        return new Solutions(String.valueOf(part1(lines)), "fml");
    }

    private static int part1(List<String> lines) {
        return lines.stream()
                .mapToInt(line -> new PatternMatcher(line.split(" ")[0], Utils.readIntsFromStr(line)).calcWays())
                .sum();
    }

    public static class PatternMatcher {
        public int I, J;
        public int[][] memo;
        public final String pattern;
        public final List<Integer> pieces;

        PatternMatcher(String pattern, List<Integer> pieces) {
            this.pattern = pattern;
            this.pieces = pieces;
            this.I = pattern.length();
            this.J = pieces.size();
            this.memo = IntStreamEx.range(I)
                    .mapToObj(i -> IntStreamEx.constant(-1, J).toArray())
                    .toArray(int[][]::new);
        }

        int calcWays() {
            return calcWays(0, 0);
        }

        private int calcWays(int i, int j) {
            //            System.out.println("i and j: " + i + " " + j);
            if (j == J) {
                return i == I || pattern.indexOf('#', i) == -1 ? 1 : 0;
            }
            if (i == I) {
                return 0;
            }

            if (memo[i][j] != -1) {
                return memo[i][j];
            }

            int res =
                    switch (pattern.charAt(i)) {
                        case '#' -> canPlace(i, j) ? calcWays(place(i, j), j + 1) : 0;
                        case '.' -> calcWays(findNextNonEmpty(i), j);
                        case '?' -> {
                            int miniRes = calcWays(i + 1, j);
                            if (canPlace(i, j)) {
                                //                       System.out.println("getting in now");
                                miniRes += calcWays(place(i, j), j + 1);
                                //                       System.out.println("miniRes: " + miniRes);
                            }
                            yield miniRes;
                        }
                        default -> throw new IllegalStateException("Unrecognized character" + pattern.charAt(i));
                    };

            memo[i][j] = res;
            return memo[i][j];
        }

        // this and canPlace is a little awkward?
        private int place(int i, int j) {
            // throw if cannot place;
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
