package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day07 implements Day {

    @Override
    public Solutions solve() {
        List<Hand> hands = Input.readLines(this.getClass().getSimpleName()).stream()
                .map(Hand::fromInputLine)
                .toList();

        return new Solutions(String.valueOf(part1(hands)), String.valueOf(part2(hands)));
    }

    private static int part1(List<Hand> hands) {
        List<Hand> sortedHands = hands.stream()
                .sorted((p, q) -> {
                    HandType typeP = HandType.determineHandType(p.cards);
                    HandType typeQ = HandType.determineHandType(q.cards);
                    return typeP.equals(typeQ)
                        ? compareCardsWithPriority(p.cards(), q.cards(), "AKQJT98765432")
                        : typeP.compareTo(typeQ);
                })
                .toList();
        return sumHands(sortedHands);
    }

    private static int part2(List<Hand> hands) {
        final String allCardsOrdered = "AKQT98765432J";
        List<Hand> sortedHands = hands.stream()
                .sorted((p, q) -> {
                    HandType typeP = HandType.determiHandTypeWithReplacement(p.cards, allCardsOrdered);
                    HandType typeQ = HandType.determiHandTypeWithReplacement(q.cards, allCardsOrdered);
                    return typeP.equals(typeQ)
                        ? compareCardsWithPriority(p.cards(), q.cards(), allCardsOrdered)
                        : typeP.compareTo(typeQ);
                })
                .toList();
        return sumHands(sortedHands);
    }

    private static int compareCardsWithPriority(String p, String q, String allCardsOrdered) {
        for (int i = 0; i < p.length(); ++i) {
            if (p.charAt(i) != q.charAt(i)) {
                int indexP = allCardsOrdered.indexOf(p.charAt(i));
                int indexQ = allCardsOrdered.indexOf(q.charAt(i));
                return indexP < indexQ ? 1 : -1;
            }
        }
        return 0;
    }

    private static int sumHands(List<Hand> sortedHands) {
        int sum = 0, coeff = 1;
        for (Hand hand : sortedHands) {
            sum += coeff * hand.bid();
            coeff++;
        }
        return sum;
    }

    enum HandType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind;

        static HandType determineHandType(String cards) {
            Map<Character, Integer> freqs = new HashMap<>();
            for (char c : cards.toCharArray()) {
                freqs.put(c, freqs.getOrDefault(c, 0) + 1);
            }
    
            if (freqs.size() == 1) {
                return HandType.FiveOfAKind;
            }
            if (freqs.size() == 2) {
                if (freqs.containsValue(4)) {
                    return HandType.FourOfAKind;
                }
                return HandType.FullHouse;
            }
            if (freqs.size() == 3) {
                if (freqs.containsValue(3)) {
                    return HandType.ThreeOfAKind;
                }
                return HandType.TwoPair;
            }
            if (freqs.size() == 4) {
                return HandType.OnePair;
            }
            return HandType.HighCard;
        }

        static HandType determiHandTypeWithReplacement(String cards, String replacements) {
            return replacements.chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .map(replacement -> determineHandType(cards.replaceAll("J", replacement)))
                    .sorted((p, q) -> q.compareTo(p))
                    .findFirst().orElseThrow();
        }
    }

    record Hand(String cards, int bid) {
        public static Hand fromInputLine(String line) {
            String cards = line.substring(0, 5);
            int bid = Integer.valueOf(line.substring(6));
            return new Hand(cards, bid);
        }
    }
}
