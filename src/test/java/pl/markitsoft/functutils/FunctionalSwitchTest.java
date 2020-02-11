package pl.markitsoft.functutils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.markitsoft.functutils.FunctionalSwitch.ofType;

class FunctionalSwitchTest {

    static final Number[] TEST_DATA = {0, 1, 2345L, 0.35436};

    private final List<Number> integers = new ArrayList<>();
    private final List<Number> longs = new ArrayList<>();
    private final List<Number> floats = new ArrayList<>();
    private final List<Number> bigDecimals = new ArrayList<>();

    @BeforeEach
    void init() {
        integers.clear();
        longs.clear();
        floats.clear();
        bigDecimals.clear();
    }

    @Test
    void switchByNumberTypeForArray() {
        // given
        final FunctionalSwitch<Number> fSwitch = prepareFuncSwitch();
        // when
        fSwitch.process(TEST_DATA);
        // then
        assertions();
    }

    @Test
    void switchByNumberTypeForList() {
        // given
        final FunctionalSwitch<Number> fSwitch = prepareFuncSwitch();
        final List<Number> testData = Arrays.asList(TEST_DATA);
        // when
        fSwitch.process(testData);
        // then
        assertions();
    }

    @Test
    void switchByNumberTypeForStream() {
        // given
        final FunctionalSwitch<Number> fSwitch = prepareFuncSwitch();
        final Stream<Number> stream = Arrays.stream(TEST_DATA);
        // when
        fSwitch.process(stream);
        // then
        assertions();
    }

    private void assertions() {
        assertEquals(2, integers.size());
        assertEquals(1, longs.size());
        assertEquals(1, floats.size());
        assertEquals(0, bigDecimals.size());
        assertTrue(integers.contains(Integer.valueOf("0")));
        assertTrue(integers.contains(Integer.valueOf("1")));
        assertTrue(longs.contains(Long.valueOf("2345")));
    }

    private FunctionalSwitch<Number> prepareFuncSwitch() {
        return FunctionalSwitch.<Number>builder()
                .ofType(Integer.class, integers::add)
                .ofType(Double.class, floats::add)
                .ofType(Long.class, longs::add)
                .ofType(BigDecimal.class, bigDecimals::add)
                .build();
    }
}