import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Commons {
    public static List<String> readStdInLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner stdIn = new Scanner(System.in)) {
            while (stdIn.hasNextLine()) {
                lines.add(stdIn.nextLine());
            }
        }
        return lines;
    }
}
