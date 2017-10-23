package part1.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import part1.example.IntArraySpliterator;


import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class RectangleSpliteratorTest {
    private int[][] arr;

    @BeforeEach
    void setup() {
        arr = new int[][]{
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
        };
    }

    @Test
    void testParallelSeq() {
        assertEquals(50, StreamSupport.intStream(new RectangleSpliterator(arr), false).sum());
    }

    @Test
    void testParallelStream() {
        assertEquals(50, StreamSupport.intStream(new RectangleSpliterator(arr), true).sum());
    }

    @Test
    void testMaxMethodOfSteam() {
        assertEquals(50, StreamSupport.intStream(new IntArraySpliterator(new int[]{1, 50, 10}), true)
                .max()
                .orElseThrow(IllegalStateException::new));
    }

}