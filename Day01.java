import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class Day01 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Commons.readStdInLines();
        System.out.println(partOne(lines));
        System.out.println(partTwo(lines));
    }

    private static int partOne(List<String> lines) {
        return lines.stream()
                .map(line -> line.replaceAll("\\D", ""))
                .mapToInt(Day01::parseIntFromDigits)
                .sum();
    }

    private static int partTwo(List<String> lines) {
        Map<String, Integer> digitMap = makeDigitMap();
        return lines.stream()
                .mapToInt(line -> {
                     List<Integer> digits = digitMap.entrySet().stream().flatMap(entry -> Stream.of(
                            Map.entry(line.indexOf(entry.getKey()), entry.getValue()),
                            Map.entry(line.lastIndexOf(entry.getKey()), entry.getValue())))
                        .filter(entry -> entry.getKey() != -1)
                        .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                        .map(Map.Entry::getValue)
                        .toList();
                    // make integer from first and last digits
                    return digits.get(0) * 10 + digits.get(digits.size() - 1);
                })
                .sum();
    }

    private static Map<String, Integer> makeDigitMap() {
        Map<String, Integer> digitMap = new HashMap<>();
        for (int i = 1; i < 10; ++i) {
            digitMap.put(String.valueOf(i), i);
        }
        // fml.
        digitMap.put("one", 1);
        digitMap.put("two", 2);
        digitMap.put("three", 3);
        digitMap.put("four", 4);
        digitMap.put("five", 5);
        digitMap.put("six", 6);
        digitMap.put("seven", 7);
        digitMap.put("eight", 8);
        digitMap.put("nine", 9);
        return digitMap;
    }

    private static int parseIntFromDigits(String digits) {
        char first = digits.charAt(0);
        char last = digits.charAt(digits.length() - 1);
        return (
            10 * Character.getNumericValue(first) +
            Character.getNumericValue(last));
    }
}
