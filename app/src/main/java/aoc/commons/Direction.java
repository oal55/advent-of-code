package aoc.commons;

import java.util.List;

public class Direction {

    public static final Point UP = Point.of(-1, 0);
    public static final Point RIGHT = Point.of(0, 1);
    public static final Point DOWN = Point.of(1, 0);
    public static final Point LEFT = Point.of(0, -1);
    public static final List<Point> FOUR_WAYS = List.of(UP, RIGHT, DOWN, LEFT);
}
