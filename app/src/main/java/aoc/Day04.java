package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 implements Day {

    @Override
    public Solutions solve() {
        List<String> lines = Input.readLines(this.getClass().getSimpleName());
        return new Solutions(String.valueOf(part1(lines)), String.valueOf(part2(lines)));
    }

    private static int part1(List<String> lines) {
        return lines.stream()
                .map(Card::fromLine)
                .map(card -> 
                        card.winningNumbers.stream()
                                .filter(card.ourNumbers::contains)
                                .count()) // intersection size
                .mapToInt(intersectionSize -> 1 << (intersectionSize - 1))
                .sum();
    }

    private static int part2(List<String> lines) {
        List<Integer> numMatchesForCards = lines.stream()
                .map(Card::fromLine)
                .map(card -> 
                        (int) card.winningNumbers.stream()
                                .filter(card.ourNumbers::contains)
                                .count()) // intersection size
                .toList();

        int[] diffs = new int[1000]; // sozzles D:
        int numCopies = 1, res = 0;
        for (int i = 0; i < numMatchesForCards.size(); ++i) {
            numCopies += diffs[i];
            res += numCopies;

            diffs[i + 1] += numCopies;
            diffs[i + numMatchesForCards.get(i) + 1] -= numCopies;
        }
        return res;
    }

    record Card(int id, Set<Integer> winningNumbers, Set<Integer> ourNumbers) {
        private static final Pattern CARD_PATTERN = Pattern.compile("Card\\s+(?<cardId>\\d+):(?<winningNums>.*)\\|(?<ourNums>.*)");
        
        public static Card fromLine(String line) {
            Matcher cardMatcher = CARD_PATTERN.matcher(line);
            if (!cardMatcher.matches()) {
                throw new IllegalArgumentException("Bad line: " + line);
            }
            return new Card(
                    Integer.parseInt(cardMatcher.group("cardId")),
                    parseNumbers(cardMatcher.group("winningNums")),
                    parseNumbers(cardMatcher.group("ourNums")));
        }

        private static Set<Integer> parseNumbers(String listOfNumbers) {
            return Arrays.stream(listOfNumbers.split(" "))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        }
    }
}
