package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import java.util.List;
import java.util.regex.Pattern;

public final class Day02 implements Day {

    @Override
    public Solutions solve() {
        List<Game> games = Input.readLines(this.getClass().getSimpleName()).stream()
                .map(Game::fromLine)
                .toList();
        return new Solutions(String.valueOf(part1(games)), String.valueOf(part2(games)));
    }

    private static int part1(List<Game> games) {
        final Game rgbThreshold = new Game(-1, 12, 13, 14);
        return games.stream().filter(rgbThreshold::isAbove).mapToInt(Game::id).sum();
    }

    private static int part2(List<Game> games) {
        return games.stream().mapToInt(Game::power).sum();
    }

    record Game(int id, int red, int green, int blue) {
        private static final Pattern ID = Pattern.compile("Game (\\d+)");
        private static final Pattern RED = Pattern.compile("(\\d+) red");
        private static final Pattern GREEN = Pattern.compile("(\\d+) green");
        private static final Pattern BLUE = Pattern.compile("(\\d+) blue");

        public static Game fromLine(String line) {
            return new Game(
                    getGameId(line), biggestRollOf(RED, line), biggestRollOf(GREEN, line), biggestRollOf(BLUE, line));
        }

        public boolean isAbove(Game that) {
            return red >= that.red && green >= that.green && blue >= that.blue;
        }

        public int power() {
            return red * green * blue;
        }

        private static int biggestRollOf(Pattern coloPattern, String line) {
            return coloPattern
                    .matcher(line)
                    .results()
                    .mapToInt(result -> Integer.parseInt(result.group(1)))
                    .max()
                    .orElse(0);
        }

        private static int getGameId(String line) {
            return Integer.parseInt(
                    ID.matcher(line).results().findFirst().orElseThrow().group(1));
        }
    }
}
