import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day08 {
    public static void main(String[] args) {
        List<String> lines = Commons.readStdInLines();
        Map<String, List<String>> graph = makeGraph(lines.stream().skip(2).toList());

        System.out.println(partOne(lines.get(0), graph));
        System.out.println(partTwo(lines.get(0), graph));
    }

    static int partOne(String instructions, Map<String, List<String>> graph) {
        return iterateTillSuffix(instructions, graph, "AAA", "ZZZ");
    }

    static long partTwo(String instructions, Map<String, List<String>> graph) {
        return graph.keySet()
                .stream()
                .filter(node -> node.endsWith("A")) // start nodes
                .map(node -> (long) iterateTillSuffix(instructions, graph, node, "Z")) // num steps to Z
                .reduce(1L, Day08::lcm);
    }

    static int iterateTillSuffix(String instructions, Map<String, List<String>> graph, String node, String targetSuffix) {
        Iterator<Integer> it = circularIterator(instructions);
        int numSteps = 0;
        while (!node.endsWith(targetSuffix)) {
            int direction = it.next();
            node = graph.get(node).get(direction);
            numSteps++;
        }
        return numSteps;
    }

    static Iterator<Integer> circularIterator(String instructions) {
        int size = instructions.length();
        return new Iterator<Integer>() {
            int i = 0;

            @Override
            public boolean hasNext() { return true; }
    
            @Override
            public Integer next() {
                char next = instructions.charAt(i % size);
                i = (i + 1) % size;
                return next == 'L' ? 0 : 1;
            }
        };
    }

    static Map<String, List<String>> makeGraph(List<String> lines) {
        Pattern pattern = Pattern.compile("(?<source>\\w{3}) = \\((?<fi>\\w{3}), (?<se>\\w{3})\\)");
        Map<String, List<String>> graph = new HashMap<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Bad line: " + line);
            }
            String source = matcher.group("source");
            graph.putIfAbsent(source, new ArrayList<>());
            graph.get(source).addAll(List.of(matcher.group("fi"), matcher.group("se")));
        }
        return graph;
    }

    static long gcd(long p, long q) {
        if (q == 0) return p;
        return (gcd (q, p % q));
    }

    static long lcm(long p, long q) { return p / gcd(p, q) * q; }
}