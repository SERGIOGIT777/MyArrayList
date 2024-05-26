package org.example;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Список с возможностью изменения размера с помощью быстрой сортировки на основе массива.
 * Массив необходим для размещения элементов в списке
 * При достижении массива предела его размера он увеличивается на величину multiplayer
 *
 * @param <E> типизированный generic в списке
 * @author Сергей Корниченко
 */

public class MyArrayList <E> {
    private E[] array;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MULTIPLIER = 2;
    private int lastPosition = 0;

    /**
     * Создаем пустой лист с начальным объемом DEFAULT_CAPACITY.
     */
    public MyArrayList() {
        this.array = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Метод добавления элемента в наш arraylist в конец списка.
     *
     * @param element - элементы, для добавления в список.
     */
    public void add(E element) {
        if (lastPosition >= array.length) {
            growArray();
        }
        array[lastPosition] = element;
        lastPosition++;
    }

    /**
     * Метод добавления элемента в список с указанной позиции.
     * При добавлении следующего элемента в список, сдвиг происходит вправо
     * Если вставить элемент в конец списка, то происходит добавление элемента в конец
     * стандартный метод add()
     *
     * @param index   - позиция для добавления
     * @param element - элементы для добавления
     * @throws IndexOutOfBoundsException - исключение для ошибочного индекса при добавлении
     */
    public void addPos(int index, E element) {
        if (index == lastPosition) {
            add(element);
            return;
        }
        checkBounds(index);
        if (lastPosition + 1 >= array.length) {
            growArray();
        }
        System.arraycopy(array, index, array, index + 1, lastPosition - index);
        array[index] = element;
        lastPosition++;
    }

    /**
     * Метод получения элемента из списка.
     *
     * @param index - индекс по которому получаем элемент
     * @throws IndexOutOfBoundsException - исключение для ошибочного индекса
     */
    public E get(int index) {
        checkBounds(index);
        return array[index];
    }

    /**
     * Метод удаления конкретного элемента из списка по индексу.
     * При удалении элемента происходит сдвиг в коллекции на один шаг влево.
     *
     * @param index - индекс элемента для удаления
     * @throws IndexOutOfBoundsException - исключение для ошибочного индекса
     */
    public E removeElement(int index) {
        checkBounds(index);
        E element = array[index];
        System.arraycopy(array, index + 1, array, index, lastPosition - index - 1);
        lastPosition--;
        array[lastPosition] = null;
        return element;
    }

    /**
     * Метод очистки нашей коллекции.
     */
    public void clear() {
        Arrays.fill(array, null);
        lastPosition = 0;
    }


    /**
     * Метод замены элемента по индексу.
     *
     * @param index   - искомый индекс для замены
     * @param element - элемент
     * @throws IndexOutOfBoundsException - исключение для ошибочного индекса
     */
    public E set(int index, E element) {
        checkBounds(index);
        E oldElement = array[index];
        array[index] = element;
        return oldElement;
    }


    /**
     * Метод получения размера нашей коллекции.
     */
    public int size() {
        return lastPosition;
    }

    /**
     * Метод сортировки коллекции при помощи Comparator.
     *
     * @param comparator - сравнение элементов в коллекции
     * @throws NullPointerException - исключение для null элемента
     */
    public void sort(Comparator<? super E> comparator) {
        quickSort(0, lastPosition - 1, comparator);
    }

    /**
     * Метод быстрой сортировки.
     *
     * @param low        - нижняя граница массива
     * @param high       - верхняя граница массива
     * @param comparator - сравнение элементов в коллекции
     * @throws NullPointerException - исключение для null элемента
     */
    private void quickSort(int low, int high, Comparator comparator) {
        if (low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        E mid = array[middle];

        int leftBound = low;
        int rightBound = high;
        while (leftBound <= rightBound) {
            while (comparator.compare(array[leftBound], mid) < 0) {
                leftBound++;
            }
            while (comparator.compare(array[rightBound], mid) > 0) {
                rightBound--;
            }
            if (leftBound <= rightBound) {
                E temp = array[leftBound];
                array[leftBound] = array[rightBound];
                array[rightBound] = temp;
                leftBound++;
                rightBound--;
            }
        }
        if (low < rightBound) {
            quickSort(low, rightBound, comparator);
        }
        if (high > leftBound) {
            quickSort(leftBound, high, comparator);
        }
    }

    /**
     * Метод расширения массива при достижении заданного объема.
     * Создается новый лист с новым объемом и в него копируются элементы.
     */
    private void growArray() {
        long newCapacity = array.length * MULTIPLIER;

        if (newCapacity > Integer.MAX_VALUE) {
            newCapacity = Integer.MAX_VALUE;
        }

        E[] newArray = (E[]) new Object[(int) newCapacity];
        System.arraycopy(this.array, 0, newArray, 0, array.length);
        this.array = newArray;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= lastPosition) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, lastPosition));
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, 0, lastPosition));
    }
}
