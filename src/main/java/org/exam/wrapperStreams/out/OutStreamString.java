package org.exam.wrapperStreams.out;

import org.exam.excep.WrongException;

import java.io.IOException;

/**
 * реализует методы взаимодействия с классом-оберткой
 * @param <T> передача типа элемента
 */
public class OutStreamString <T> extends OutStreamImpl{
    public OutStreamString(String fileName) {

        super(fileName);
    }

    public OutStreamString(String fileName, int bufferedSize) {

        super(fileName, bufferedSize);
    }

    @Override
    public void setNext(Integer out) {
        ;
    }

    /**
     * запись строки в файл и переход на следующую строку
     * @param out записываемый элемент
     */
    @Override
    public void setNext(String out) {

        try {

            writer.write(out);
            writer.newLine();
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    @Override
    public void setNext(Object out) {
        setNext((String) out);
    }
}
