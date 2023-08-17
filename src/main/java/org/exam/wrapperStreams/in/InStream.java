package org.exam.wrapperStreams.in;

/**
 * интерфейс для создания семейства классов-оберток над потоками чтения из файла
 *         close() закрывает поток чтения
 *         getNext() читает следующий элемент
 *         getCurrentElement() - вернуть текущий элемент
 *         getValue() - вернуть значение предельное для типа данных и в зависимости от сортировки по
 *             возрастанию/убыванию возвращает max/min элемент данного типа
 *         compare() - операция сравнения для элемента данного типа данных (необходимо для сортировки)
 * @param <T> передача типа элемента
 */
public interface InStream<T> {

    public void close();
    T getNext();
    T getCurrentElement();
    public T getValue();
    boolean compare(Integer inputElement);
    boolean compare(String inputElement);
    boolean compare(T inputElement);
    boolean ready();
}
