import java.util.ArrayList;
import java.util.List;

public class Day03 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Commons.readStdInLines();

        System.out.println(partOne(new MatrixContext(makeMatrix(lines))));
        System.out.println(partTwo(new MatrixContext(makeMatrix(lines))));
    }

    private static int partOne(MatrixContext context) {
        List<Integer> allPartNumbers = new ArrayList<>();
        context.analyze((i, j) -> {
            if (context.isSymbol(i, j)) {
                allPartNumbers.addAll(context.searchNearByPartsAndMark(i, j));
            }
        });
        return allPartNumbers.stream().mapToInt(q -> q).sum();
    }

    private static int partTwo(MatrixContext context) {
        List<Integer> allPartNumbers = new ArrayList<>();
        context.analyze((i, j) -> {
            if (context.isEngine(i, j)) {
                List<Integer> localParts = context.searchNearByPartsAndMark(i, j);
                if (localParts.size() == 2) {
                    allPartNumbers.add(localParts.get(0) * localParts.get(1));
                }
            }
        });
        return allPartNumbers.stream().mapToInt(q -> q).sum();
    }

    private static char[][] makeMatrix(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    interface Analyzer { void analyze(int i, int j); }

    record MatrixContext(char[][] matrix, int I, int J) {
        private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};

        public MatrixContext(char[][] matrix) {
            this(matrix, matrix.length, matrix[0].length);
        }

        // I am not a psychpath, just experimenting with unorthodox design decisions
        public int analyze(Analyzer analyzer) {
            List<Integer> allPartNumbers = new ArrayList<>();
            for (int i = 0; i < I; ++i) {
                for (int j = 0; j < J; ++j) {
                    analyzer.analyze(i, j);
                }
            }
            return allPartNumbers.stream().mapToInt(q -> q).sum();
        }

        private boolean validCoordinate(int i, int j) { return i >=0 && i < I && j >= 0 && j < J; }

        public boolean isEngine(int i, int j) { return validCoordinate(i, j) && matrix[i][j] == '*'; }

        public boolean isSymbol(int i, int j) {
            return (
                    validCoordinate(i, j) &&
                    !(matrix[i][j] == '.' || Character.isDigit(matrix[i][j])));
        }

        public boolean isDigit(int i, int j) {
            return validCoordinate(i, j) && Character.isDigit(matrix[i][j]);
        }

        public List<Integer> searchNearByPartsAndMark(int iStart, int jStart) {
            List<Integer> partNumbers = new ArrayList<>();
            for (int[] dir : DIRS) {
                int i = iStart + dir[0];
                int j = jStart + dir[1];
                if (isDigit(i, j)) {
                    partNumbers.add(readPartNumberAndMark(i, j));
                }
            }
            return partNumbers;
        }
    
        private int readPartNumberAndMark(int i, int j) {
            int partNumber = 0;
            while (isDigit(i, j - 1)) { --j; } // j is now at the first digit of the part number
            while (isDigit(i, j)) {
                partNumber = partNumber * 10 + Character.getNumericValue(matrix[i][j]);
                matrix[i][j] = '.'; // this is how we mark.
                ++j;
            }
            return partNumber;
        }
    }
}
