package org.exam.wrapperStreams.out;

/**
 * интерфейс для создания семейства классов-оберток
 *         close() закрывает поток записи
 *         setNext() записывает элемент в файл
 * @param <T> передача типа элемента
 */
public interface OutStream<T> {

    void close();
    void setNext(T out);
}
