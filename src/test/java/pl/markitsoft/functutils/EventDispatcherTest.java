package pl.markitsoft.functutils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDispatcherTest {

    static class EventOne implements DomainEvent {
    }

    static class EventTwo implements DomainEvent {
    }

    static final DomainEvent[] TEST_DATA = {new EventOne(), new EventTwo()};

    private final List<DomainEvent> events1 = new ArrayList<>();
    private final List<DomainEvent> events2 = new ArrayList<>();

    @BeforeEach
    void init() {
        events1.clear();
        events2.clear();
    }

    @Test
    void switchByNumberTypeForArray() {
        // given
        final FunctionalSwitch<DomainEvent> fSwitch = prepareEventDispatcher();
        // when
        fSwitch.process(TEST_DATA);
        // then
        assertions();
    }

    @Test
    void switchByNumberTypeForList() {
        // given
        final FunctionalSwitch<DomainEvent> fSwitch = prepareEventDispatcher();
        final List<DomainEvent> testData = Arrays.asList(TEST_DATA);
        // when
        fSwitch.process(testData);
        // then
        assertions();
    }

    @Test
    void switchByNumberTypeForStream() {
        // given
        final FunctionalSwitch<DomainEvent> fSwitch = prepareEventDispatcher();
        final List<DomainEvent> testData = Arrays.asList(TEST_DATA);
        final Stream<DomainEvent> stream = testData.stream();
        // when
        fSwitch.process(stream);
        // then
        assertions();
    }

    private void assertions() {
        assertEquals(1, events1.size());
        assertEquals(1, events2.size());
        assertEquals(EventOne.class, events1.get(0).getClass());
        assertEquals(EventTwo.class, events2.get(0).getClass());
    }

    private FunctionalSwitch<DomainEvent> prepareEventDispatcher() {

        return DomainEventDispatcher.builder()
                .ofType(EventOne.class, events1::add)
                .ofType(EventTwo.class, events2::add)
                .build();
    }

}