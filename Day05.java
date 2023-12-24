import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day05 {

    public static void main(String[] args) {
        String[] stanzas = Commons.readStdIn().split("\n\n");

        List<Converter> converters = Arrays.stream(stanzas).skip(1) // skip the seeds one
                .map(stanza -> Converter.fromStanza(Arrays.asList(stanza.split("\n"))))
                .toList();

        System.out.println(partOne(stanzas[0], converters));
        System.out.println(partTwo(stanzas[0], converters));
    }

    private static long partOne(String seedStanza, List<Converter> converters) {
        List<Long> seeds = Arrays.stream(seedStanza.substring("Seeds:".length()).split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(Long::valueOf)
                .toList();

        return converters.stream()
                .reduce(
                    seeds.stream(),
                    (seedStream, converter) -> seedStream.map(converter::convert),
                    (p, q) -> Stream.of())
                .min(Long::compare).orElseThrow();
    }

    private static long partTwo(String seedStanza, List<Converter> converters) {
        List<SeedRange> seeds = SeedRange.fromInputLine(seedStanza.substring("Seeds:".length()));
        return converters.stream()
                .reduce(
                        seeds.stream(),
                        (seedStream, converter) -> seedStream.flatMap(converter::convertRange),
                        (p, q) -> Stream.of())
                .map(SeedRange::start)
                .sorted()
                .findFirst().orElseThrow();
    }

    record SeedRange(long start, long end) {
        public static List<SeedRange> fromInputLine(String line) {
            List<Long> allNums = Arrays.stream(line.split("\\s+")).filter(s -> !s.isBlank()).map(s -> Long.valueOf(s)).toList();
            List<SeedRange> ranges = new ArrayList<>();
            for (int i = 0; i < allNums.size() - 1; i += 2) {
                ranges.add(new SeedRange(allNums.get(i), allNums.get(i) + allNums.get(i + 1) - 1));
            }
            return ranges;
        }

        public SeedRange offset(long offsetAmount) {
            return new SeedRange(start + offsetAmount, end + offsetAmount);
        }
    }

    record Range(long start, long end, long offset) {
        public boolean contains(long num) {
            return num >= start() && num <= end();
        }

        public boolean intersectsWith(long start, long end) {
            return !(start >= end() || end <= start());
        }

        public static Range fromInputLine(String line) {
            List<Long> parts = Arrays.stream(line.split("\\s+")).map(s -> Long.valueOf(s)).toList();
            return new Range(
                    parts.get(1),
                    parts.get(1) + parts.get(2) - 1,
                    parts.get(0) - parts.get(1));
        }
    }

    record Converter(List<Range> ranges) {
        public long convert (long num) {
            return ranges.stream()
                    .filter(range -> range.contains(num))
                    .findFirst()
                    .map(range -> num + range.offset).orElse(num);
        }

        public Stream<SeedRange> convertRange(SeedRange seed) {
            List<SeedRange> newSeeds = new ArrayList<>();
            List<Range> intersectiRanges = ranges.stream()
                    .filter(range -> range.intersectsWith(seed.start(), seed.end()))
                    .toList();

            long start = seed.start();
            for (Range range : intersectiRanges) {
                if (start < range.start()) {
                    newSeeds.add(new SeedRange(start, range.start() - 1));
                    start = range.start();
                }
                long newStart = Math.min(seed.end(), range.end()) + 1;
                newSeeds.add(
                    new SeedRange(start, newStart - 1).offset(range.offset())); // not counting new start
                start = newStart;
            }
            if (start <= seed.end()) {
                newSeeds.add(new SeedRange(start, seed.end()));
            }
            return newSeeds.stream();
        }

        public static Converter fromStanza(List<String> lines) {
            return new Converter(lines.stream()
                    .skip(1) // skip the header
                    .map(Range::fromInputLine)
                    .sorted((p, q) -> Long.compare(p.start(), q.start()))
                    .toList());
        }
    }

}
