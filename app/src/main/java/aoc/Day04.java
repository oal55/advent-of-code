package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Utils;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day04 implements Day {

    @Override
    public Solutions solve() {
        List<Card> cards = Input.readLines(this.getClass().getSimpleName()).stream()
                .map(Card::fromLine)
                .toList();
        return new Solutions(String.valueOf(part1(cards)), String.valueOf(part2(cards)));
    }

    private static int part1(List<Card> cards) {
        return cards.stream()
                .map(Card::numberOfMatches)
                .mapToInt(intersectionSize -> 1 << (intersectionSize - 1))
                .sum();
    }

    private static int part2(List<Card> cards) {
        List<Integer> numMatchesForCards =
                cards.stream().map(Card::numberOfMatches).toList();

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

    record Card(int id, Set<Long> winningNumbers, Set<Long> ourNumbers) {
        private static final Pattern CODE = Pattern.compile("Card\\s+(\\d+):(.*)\\|(.*)");

        public static Card fromLine(String line) {
            MatchResult match = CODE.matcher(line)
                    .results()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Bad line: " + line));
            return new Card(
                    Integer.parseInt(match.group(1)),
                    Utils.readUniqueLongsFromStr(match.group(2)),
                    Utils.readUniqueLongsFromStr(match.group(3)));
        }

        public int numberOfMatches() {
            return Sets.intersection(winningNumbers, ourNumbers).size();
        }
    }
}
