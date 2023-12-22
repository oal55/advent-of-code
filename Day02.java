import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Day02 {
    private static final Pattern GAME = Pattern.compile("Game (\\d+)");
    
    record RedGreenBlue(int red, int green, int blue) {
        private static final Pattern RED = Pattern.compile("(\\d+) red");
        private static final Pattern GREEN = Pattern.compile("(\\d+) green");
        private static final Pattern BLUE = Pattern.compile("(\\d+) blue");

        public static RedGreenBlue fromLine(String line) {
            return new RedGreenBlue(
                    biggestRollOf(RED, line),
                    biggestRollOf(GREEN, line),
                    biggestRollOf(BLUE, line));
        }

        public boolean isAbove(RedGreenBlue that) { return red >= that.red && green >= that.green && blue >= that.blue; }

        public int power() { return red * green * blue; }

        private static int biggestRollOf(Pattern coloPattern, String line) {
            return coloPattern.matcher(line)
                    .results()
                    .mapToInt(result -> Integer.valueOf(result.group(1)))
                    .max().orElse(0);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Commons.readStdInLines();
        System.out.println(partOne(lines));
        System.out.println(partTwo(lines));
    }

    private static int partOne(List<String> lines) {
        final RedGreenBlue rgbTreshold = new RedGreenBlue(12, 13, 14);
        return lines.stream()
                .map(line -> Map.entry(getGameId(line), RedGreenBlue.fromLine(line)))
                .filter(entry -> rgbTreshold.isAbove(entry.getValue()))
                .mapToInt(Map.Entry::getKey)
                .sum();
    }

    private static int partTwo(List<String> lines) {
        return lines.stream()
                .map(RedGreenBlue::fromLine)
                .mapToInt(RedGreenBlue::power)
                .sum();
    }

    private static int getGameId(String line) {
        Matcher gameMatcher = GAME.matcher(line);
        if (gameMatcher.find()) {
            return Integer.valueOf(gameMatcher.group(1));
        }
        throw new RuntimeException("fml");
    }
}
