package aoc;

import aoc.commons.Input;
import aoc.commons.Solutions;
import aoc.commons.Stanza;
import aoc.commons.Utils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 implements Day {

    @Override
    public Solutions solve() {
        List<Stanza> stanzas = Input.readStanzas(this.getClass().getSimpleName());
        return new Solutions(String.valueOf(part1(stanzas)), String.valueOf(part2(stanzas)));
    }

    private static int part1(List<Stanza> stanzas) {
        return stanzas.stream()
                .map(Stanza::getLines)
                .map(list -> list.stream().map(String::toCharArray).toArray(char[][]::new))
                .mapToInt(matrix -> summarizeReflections(matrix, ReflectionStatus.REFLECTS_PERFECTLY))
                .sum();
    }

    private static int part2(List<Stanza> stanzas) {
        return stanzas.stream()
                .map(Stanza::getLines)
                .map(list -> list.stream().map(String::toCharArray).toArray(char[][]::new))
                .mapToInt(matrix -> summarizeReflections(matrix, ReflectionStatus.REFLECTS_WITH_SMUDGE))
                .sum();
    }

    static int summarizeReflections(char[][] matrix, ReflectionStatus desiredStatus) {
        return 100 * summarize(matrix, desiredStatus) + summarize(Utils.transposed(matrix), desiredStatus);
    }

    static int summarize(char[][] matrix, ReflectionStatus desiredStatus) {
        List<String> lines = Arrays.stream(matrix).map(String::copyValueOf).toList();
        Map<ReflectionStatus, Integer> reflections = new HashMap<>();
        for (int i = 1; i < lines.size(); ++i) {
            ReflectionStatus status = getReflectionStatus(lines, i - 1, i);
            if (!status.equals(ReflectionStatus.DOES_NOT_REFLECT)) {
                reflections.put(status, reflections.getOrDefault(status, 0) + i);
            }
        }
        return reflections.getOrDefault(desiredStatus, 0);
    }

    static ReflectionStatus getReflectionStatus(List<String> s, int lo, int hi) {
        boolean foundSmudge = false;
        while (lo >= 0 && hi < s.size()) {
            int numDifferences = countDifferences(s.get(lo), s.get(hi)); // 0 means they're the same string
            if (numDifferences > 1) {
                return ReflectionStatus.DOES_NOT_REFLECT;
            }
            if (numDifferences == 1) {
                if (foundSmudge) {
                    return ReflectionStatus.DOES_NOT_REFLECT;
                }
                foundSmudge = true;
            }
            --lo;
            ++hi;
        }
        return foundSmudge ? ReflectionStatus.REFLECTS_WITH_SMUDGE : ReflectionStatus.REFLECTS_PERFECTLY;
    }

    static int countDifferences(String p, String q) {
        int res = 0, size = p.length();
        for (int i = 0; i < size; ++i) {
            if (p.charAt(i) != q.charAt(i)) {
                res++;
            }
        }
        return res;
    }

    public enum ReflectionStatus {
        REFLECTS_PERFECTLY,
        REFLECTS_WITH_SMUDGE,
        DOES_NOT_REFLECT;
    }
}
