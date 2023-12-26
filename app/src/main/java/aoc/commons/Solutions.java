package aoc.commons;

import java.util.List;

public record Solutions(String part1, String part2) {
    @Override
    public String toString() {
        return String.join("\n", asLines());
    }

    public List<String> asLines() {
        return List.of("Part 1: " + part1, "Part 2: " + part2);
    }
}
