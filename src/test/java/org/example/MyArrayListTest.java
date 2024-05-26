package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.Comparator;
import java.util.StringJoiner;

public class MyArrayListTest {
    private MyArrayList<String> testStringList;
    private MyArrayList<Integer> testIntegerList;

    @BeforeEach
    void setUp() {
        testStringList = new MyArrayList<>();
        testIntegerList = new MyArrayList<>();
    }

    @AfterEach
    void tearDown() {
        testStringList = null;
        testIntegerList = null;
    }

    @DisplayName("Добавление элемента в список")
    @ParameterizedTest
    @CsvSource(value = {
            "5; '[1, 2, 3, 4, 5]'; '1,2,3,4,5'",
            "3; '[3, 2, 1]'; '3,2,1'"
    }, delimiter = ';'
    )
    void add(int expectedCount, String expectedToString, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
            testIntegerList.add(Integer.parseInt(value));
        }
        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());

        Assertions.assertEquals(expectedCount, testIntegerList.size());
        Assertions.assertEquals(expectedToString, testIntegerList.toString());
    }

    @DisplayName("Добавление элементов в цикле много")
    @ParameterizedTest
    @CsvSource(value = {
            "1000",
            "1007000"
    })
    void addInLoop(int expectedCount) {
        StringJoiner sjExpected = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < expectedCount; i++) {
            testStringList.add(Integer.toString(i));
            testIntegerList.add(i);
            sjExpected.add(Integer.toString(i));
        }
        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(sjExpected.toString(), testStringList.toString());

        Assertions.assertEquals(expectedCount, testIntegerList.size());
        Assertions.assertEquals(sjExpected.toString(), testIntegerList.toString());
    }


    @DisplayName("Добавление элемента в список по индексу")
    @ParameterizedTest
    @CsvSource(value = {
            "3; '[0, 1, 2]';    0; 0; '1,2'", // at start
            "3; '[1, 2, 0]';    2; 0; '1,2'", // into middle
            "6; '[4, 3, 2, 1, 0, 5]'; 4; 0; '4,3,2,1,5'" // into end
    }, delimiter = ';'
    )
    void addPos(int expectedCount, String expectedToString, int index, String element, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
            testIntegerList.add(Integer.parseInt(value));
        }
        testStringList.addPos(index, element);
        testIntegerList.addPos(index, Integer.parseInt(element));

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());

        Assertions.assertEquals(expectedCount, testIntegerList.size());
        Assertions.assertEquals(expectedToString, testIntegerList.toString());
    }

    @DisplayName("Заменить элемент в списке")
    @ParameterizedTest
    @CsvSource(value = {
            "5; '[1, -1, 4, -5, 8]'; 2; 4; '1,-1,8,-5,8'",
            "3; '[0, 1, -1]'; 0; 0; '2,1,-1'"
    }, delimiter = ';'
    )
    void set(int expectedCount, String expectedToString, int index, String element, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        testStringList.set(index, element);

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());
    }

    @DisplayName("Удалить элемент из списка")
    @ParameterizedTest
    @CsvSource(value = {
            "2; 2; '[1, 3]'; 1; '1,2,3'",
            "3; 3; '[4, 2, 1]'; 1; '4,3,2,1'"
    }, delimiter = ';'
    )
    void removeByIndex(int expectedCount, String expectedElement, String expectedToString, int index, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        String removedElement = testStringList.removeElement(index);

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());
        Assertions.assertEquals(expectedElement, removedElement);
    }

    @DisplayName("Получить элемент по индексу")
    @ParameterizedTest
    @CsvSource(value = {
            "0; 1; '1,2,3'",
            "1; 3; '4,3,2,1'",
            "5; 10; '1,2,3,4,8,10'"
    }, delimiter = ';'
    )
    void get(int index, String expectedValue, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        for (int i = 0; i < 3; i++) {
            String element = testStringList.get(index);
            Assertions.assertEquals(expectedValue, element);
        }
    }

    @DisplayName("Очистить коллекцию")
    @ParameterizedTest
    @CsvSource(value = {
            "0; 0; ''",
            "3; 0; '1,2,3'",
            "4; 0; '4,3,2,1'",
            "5; 0; '1,3,6,3,8'"
    }, delimiter = ';'
    )
    void clear(int expectedSizeBefore, int expectedSizeAfter, String values) {
        for (int i = 0; i < 2; i++) {
            if (StringUtils.isNotBlank(values)) {
                for (String value : values.split(",")) {
                    testStringList.add(value);
                }
            }
            Assertions.assertEquals(expectedSizeBefore, testStringList.size());
            testStringList.clear();
            Assertions.assertEquals(expectedSizeAfter, testStringList.size());
        }
    }

    @DisplayName("Сортировка коллекции")
    @ParameterizedTest
    @CsvSource(value = {
            "'[1, 2, 3, 4, 5]'; '[5, 4, 3, 2, 1]'; '4,1,2,5,3'"
    }, delimiter = ';'
    )
    void sortInteger(String expectedNaturalOrderToString, String expectedReverseOrderToString, String values) {
        for (String value : values.split(",")) {
            testIntegerList.add(Integer.parseInt(value));
        }

        testIntegerList.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expectedNaturalOrderToString, testIntegerList.toString());

        testIntegerList.sort(Comparator.reverseOrder());
        Assertions.assertEquals(expectedReverseOrderToString, testIntegerList.toString());
    }

    @DisplayName("IndexOutOfBoundsException проверка исключения при замене элемента")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenSet(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.set(index, "exception");
                },
                "IndexOutOfBoundsException ожидается");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());

    }

    @DisplayName("IndexOutOfBoundsException проверка исключения при добавлении элемента по индексу")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenInsert(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.addPos(index, "exception");
                },
                "IndexOutOfBoundsException ожидается");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());
    }

    @DisplayName("IndexOutOfBoundsException проверка исключения при удалении элемента")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenRemove(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.removeElement(index);
                },
                "IndexOutOfBoundsException ожидается");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());
    }
}
