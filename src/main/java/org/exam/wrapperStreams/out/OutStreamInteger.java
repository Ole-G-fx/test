package org.exam.wrapperStreams.out;

import org.exam.excep.WrongException;

import java.io.IOException;

/**
 * реализует методы взаимодействия с классом-оберткой
 * @param <T> передача типа элемента
 */
public class OutStreamInteger<T> extends OutStreamImpl{
    public OutStreamInteger(String fileName) {

        super(fileName);
    }

    public OutStreamInteger(String fileName, int bufferedSize) {

        super(fileName, bufferedSize);
    }

    /**
     * запись числа в файл и переход на следующую строку
     * @param out записываемое число
     */
    @Override
    public void setNext(Integer out) {
        try {

            writer.write(String.valueOf(out));
            writer.newLine();
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    @Override
    public void setNext(String out) {}

    @Override
    public void setNext(Object out) {
        setNext((Integer) out);
    }
}
