package aoc.commons;

public class Grid {

    public final int I, J;
    public final char[][] matrix;

    public Grid(char[][] matrix) {
        I = matrix.length;
        J = matrix[0].length;
        this.matrix = matrix.clone();
    }

    public boolean valid(Point p) {
        return p.i() >= 0 && p.i() < I && p.j() >= 0 && p.j() < J;
    }

    public char get(Point p) {
        return matrix[p.i()][p.j()];
    }
}
