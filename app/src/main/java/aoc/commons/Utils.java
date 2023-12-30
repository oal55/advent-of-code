package aoc.commons;

import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private static final Pattern INT_PATTERN = Pattern.compile("-?\\d+");

    public static List<Long> readLongsFromStr(String text) {
        return streamLongsFromStr(text).toList();
    }

    public static Set<Long> readUniqueLongsFromStr(String text) {
        return streamLongsFromStr(text).collect(Collectors.toSet());
    }

    private static Stream<Long> streamLongsFromStr(String text) {
        return INT_PATTERN.matcher(text).results().map(MatchResult::group).map(Long::parseLong);
    }

    public static char[][] transposed(char[][] matrix) {
        int I = matrix.length, J = matrix[0].length;
        char[][] transposed = new char[J][I];
        for (int i = 0; i < I; ++i) {
            for (int j = 0; j < J; ++j) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
}
