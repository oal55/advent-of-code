package aoc.commons;

import java.util.Arrays;
import java.util.List;

public class Stanza {
    private final String value;

    private Stanza(String value) {
        this.value = value;
    }

    public static Stanza of(String value) {
        return new Stanza(value);
    }

    public String get() {
        return value;
    }

    public List<String> getLines() {
        return Arrays.stream(value.split("\n")).toList();
    }
}
