package coordinate.domain.figure;

import coordinate.domain.point.PointGroup;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Triangle extends Figure {
    public static final int POINT_COUNT = 3;

    Triangle(PointGroup points) {
        super(points, POINT_COUNT);
    }

    @Override
    void validatePoints(PointGroup points) {
        if (!isLargestSmallerThanRemainingSum(points)) {
            throw new IllegalArgumentException("일직선 상 3개의 점은 허용하지 않습니다.");
        }
    }

    private boolean isLargestSmallerThanRemainingSum(PointGroup points) {
        List<Double> lengths = getAllLength(points);
        Collections.sort(lengths);
        lengths = lengths.stream()
                .map(x -> Math.sqrt(x))
                .collect(Collectors.toList());

        return BigDecimal.valueOf(lengths.get(2))
                .compareTo(BigDecimal.valueOf(lengths.get(1) + lengths.get(0))) < 0;
    }

    private List<Double> getAllLength(PointGroup points) {
        return Arrays.asList(
                points.getSquaredDistanceOf(0, 1),
                points.getSquaredDistanceOf(0, 2),
                points.getSquaredDistanceOf(2, 1)
        );
    }

    @Override
    public double calculate() {
        return area();
    }

    private double area() {
        List<Double> lengths = getAllLength(getPoints());
        return applyHeronFormula(lengths);
    }

    private double applyHeronFormula(List<Double> lengths) {
        return Math.sqrt(4 * lengths.get(0) * lengths.get(1)
                - Math.pow((lengths.get(0) + lengths.get(1) - lengths.get(2)), 2)) / 4;
    }

    @Override
    public String toString() {
        return "삼각형";
    }
}
