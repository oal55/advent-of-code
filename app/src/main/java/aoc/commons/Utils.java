package aoc.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern INT_PATTERN = Pattern.compile("-?\\d+");

    public static List<Long> readLongsFromStr(String text) {
        return INT_PATTERN.matcher(text).results()
                .map(r -> r.group())
                .map(Long::parseLong)
                .toList();
    }

    public static <T> List<T> reversed(List<T> list) {
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

}
