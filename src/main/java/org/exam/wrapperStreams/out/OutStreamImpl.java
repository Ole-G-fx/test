package org.exam.wrapperStreams.out;

import org.exam.excep.WrongException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * реализует конструкторы и закрытие потоков записи
 * @param <T> передача типа элемента
 */
public abstract class OutStreamImpl <T> implements OutStream{

    BufferedWriter writer;
    public <T>OutStreamImpl(String fileName){

        try {

            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" + fileName +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }
    public <T>OutStreamImpl(String fileName, int bufferedSize){

        try {

            writer = new BufferedWriter(new FileWriter(fileName), bufferedSize);
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" + fileName +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    /**
     * закрывает поток BufferedWriter записи в файл
     */
    @Override
    public void close() {

        try {

            writer.close();
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    public abstract void setNext(Integer out);

    public abstract void setNext(String out);
}
