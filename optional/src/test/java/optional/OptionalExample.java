package optional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Convert2MethodRef", "ExcessiveLambdaUsage", "ResultOfMethodCallIgnored", "OptionalIsPresent"})
public class OptionalExample {

    @Test
    public void get() {
        Optional<String> o1 = Optional.empty();

        o1.ifPresent(s -> System.out.println(s));

        o1.orElse("t");
        o1.orElseGet(() -> "t");
        //o1.orElseThrow(() -> new UnsupportedOperationException());
    }

    @Test
    public void ifPresent() {
        Optional<String> o1 = getOptional();

        o1.ifPresent(System.out::println);

        if (o1.isPresent()) {
            System.out.println(o1.get());
        }
    }

    @Test
    public void map() {
        Optional<String> o1 = getOptional();

        Function<String, Integer> getLength = String::length;

        Optional<Integer> expected = o1.map(getLength);

        Optional<Integer> actual;
        if (o1.isPresent()) {
            actual = Optional.ofNullable(getLength.apply(o1.get()));
        } else {
            actual = Optional.empty();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void flatMap() {
        Optional<String> o1 = getOptional();
        Function<String, Optional<List<Character>>> flatMapperFunction = string -> {
            Optional<List<Character>> characterList = Optional.of(new ArrayList<Character>());
            IntStream chars = string.chars();
            chars.forEach(ch -> characterList.get().add((char) ch));
            return characterList;
        };

        Optional<List<Character>> expected = o1.flatMap(flatMapperFunction);
        Optional<List<Character>> actual = (o1.isPresent())? flatMapperFunction.apply(o1.get()) : Optional.empty();

        assertEquals(expected, actual);
    }

    @Test
    public void filter() {
        Optional<String> o1 = getOptional();
        Predicate<String> predicate = str -> str.endsWith("c");
        Optional<String> expected = o1.filter(predicate);

        Optional<String> actual = (o1.isPresent()) ? (predicate.test(o1.get()) ? o1 : Optional.empty()) : Optional.empty();
        assertEquals(expected, actual);
    }

    private Optional<String> getOptional() {
        return ThreadLocalRandom.current().nextBoolean() ? Optional.empty() : Optional.of("abc");
    }
}
