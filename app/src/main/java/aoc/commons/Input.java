package aoc.commons;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Input {
    private static final String INPUT_FILES_ROOT_DIR = "src/main/resources/";

    private Input() {}

    public static List<String> readLines(final String className) {
        try {
            final String fileName = className + ".txt";
            final Path path = Paths.get(INPUT_FILES_ROOT_DIR + fileName);
            if (!path.toFile().exists()) {
                throw new FileNotFoundException("file not found: %s".formatted(path.toAbsolutePath()));
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(final String className) {
        return String.join("\n", readLines(className));
    }

    public static List<Stanza> readStanzas(final String className) {
        return Arrays.stream(read(className).split("\n\n")).map(Stanza::of).toList();
    }
}
