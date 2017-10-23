package part1.exercise;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] array;
    private final int endExclusive;
    private int startInclusive;

    public RectangleSpliterator(int[][] array) {
        this(array, 0, checkArrayAndCalcEstimatedSize(array));
    }

    private RectangleSpliterator(int[][] array, int startInclusive, int endExclusive) {
        super(endExclusive - startInclusive,
                Spliterator.IMMUTABLE
                        | Spliterator.ORDERED
                        | Spliterator.SIZED
                        | Spliterator.SUBSIZED
                        | Spliterator.NONNULL);
        this.array = array;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    private static int checkArrayAndCalcEstimatedSize(int[][] array) {
        return array.length * array[0].length;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startInclusive < endExclusive) {
            int value = array[startInclusive / array[0].length][startInclusive % array[0].length];
            action.accept(value);
            startInclusive++;
            return true;
        }
        return false;
    }

    @Override
    public OfInt trySplit() {
        int len = endExclusive - startInclusive;
        if (len < 2) {
            return null;
        }
        int mid = startInclusive + len / 2;
        RectangleSpliterator result = new RectangleSpliterator(array, startInclusive, mid);
        startInclusive = mid;
        return result;
    }

    @Override
    public long estimateSize() {
        return endExclusive - startInclusive;
    }
}

