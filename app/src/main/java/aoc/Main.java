/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    record DayAndTitle(String title, Day day) {
        @Override
        public String toString() {
            List<String> tabbedSolution =
                    day.solve().asLines().stream().map(line -> "    " + line).toList();
            return String.join(
                            "\n",
                            Stream.concat(Stream.of(title), tabbedSolution.stream())
                                    .toList()) + "\n";
        }
    }

    // maybe read args && filter the list so that we don't run everything all the time..............
    public static void main(String[] args) {
        days().forEach(System.out::println);
    }

    private static List<DayAndTitle> days() {
        return List.of(
                new DayAndTitle("Day 1: Trebuchet?!", new Day01()),
                new DayAndTitle("Day 2: Cube Conundrum", new Day02()),
                new DayAndTitle("Day 3: Gear Ratios", new Day03()),
                new DayAndTitle("Day 4: Scratchcards", new Day04()),
                new DayAndTitle("Day 5: If You Give A Seed A Fertilizer", new Day05()),
                new DayAndTitle("Day 6: Wait For It", new Day06()),
                new DayAndTitle("Day 7: Camel Cards", new Day07()),
                new DayAndTitle("Day 8: Haunted Wasteland", new Day08()));
    }
}
