package pl.markitsoft.functutils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FunctionalSwitch<T> {

    List<Case<T>> cases = new ArrayList<>();

    static class Builder<T> {
        private List<Case<T>> builderCases = new ArrayList<>();

        public Builder<T> caseOf(Predicate<T> predicate, Consumer<T> func) {
            builderCases.add(new Case<> (predicate, func));
            return this;
        }

        public Builder<T> ofType(Type type, Consumer<T> func) {
            builderCases.add( new Case<>(o -> o.getClass().equals(type), func));
            return this;
        }

        public FunctionalSwitch<T> build() {
            final FunctionalSwitch<T> functionalSwitch = new FunctionalSwitch<>();
            functionalSwitch.cases.addAll(builderCases);
            return functionalSwitch;
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    void eval(T item) {
        cases.stream()
                .filter(x -> x.predicate.test(item))
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalStateException("no case matching: " + item);
                }).func.accept(item);
    }

    public void process(Collection<T> collection) {
        collection.forEach(this::eval);
    }

    public void process(Stream<T> stream) {
        stream.forEach(this::eval);
    }

    public void process(T[] array) {
        for (T item : array) {
            eval(item);
        }
    }

    public static <T> Case<T> of(Predicate<T> predicate, Consumer<T> func) {
        return new Case<>(predicate, func);
    }

    public static <T> Case<T> ofType(Type type, Consumer<T> func) {
        return new Case<>(o -> o.getClass().equals(type), func);
    }

    static class Case<T> {
        Predicate<T> predicate;
        Consumer<T> func;

        public Case(Predicate<T> predicate, Consumer<T> func) {
            this.predicate = predicate;
            this.func = func;
        }
    }
}
