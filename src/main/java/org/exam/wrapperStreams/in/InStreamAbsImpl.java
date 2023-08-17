package org.exam.wrapperStreams.in;

import org.exam.excep.WrongException;

import java.io.*;

/**
 * реализует конструкторы для потоков чтения в свойствах поток чтения, направление
 *         сортировки, имя читаемого файла
 * @param <T> передача типа элемента
 */
public abstract class InStreamAbsImpl <T> implements InStream{

    protected BufferedReader reader;
    protected boolean ascending;
    protected String fileName;
    protected Long numberLine = 0L;
    public <T>InStreamAbsImpl(String fileName, boolean ascending){

        this.ascending = ascending;
        this.fileName = fileName;
        try {

            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {

            new WrongException("Файл не найден или недоступен '" + fileName +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    public <T>InStreamAbsImpl(String fileName, int lengthBuffer, boolean ascending){

        this.ascending = ascending;
        this.fileName = fileName;
        try {

                FileReader fileReader = new FileReader(fileName);
                reader = new BufferedReader(fileReader, lengthBuffer);
        } catch (FileNotFoundException e) {

            new WrongException("Файл не найден или недоступен '" + fileName +
                    "' системное сообщение:\n" + e.getMessage());
        }
    }

    /**
     * реализация закрытия потока чтения
     */
    public void close() {

        try {

            reader.close();
        } catch (IOException e) {

            new WrongException("Неудача при закрытии потока чтения, ошибка ввода/вывода," +
                    " системное сообщение:\n" + e.getMessage());
        }
    }
    @Override
    public boolean ready(){
        try {
            return reader.ready();
        } catch (IOException e) {
            new WrongException("Этого не должно было произойти, проверка на шотовность привела к краху, " +
                    "однако элемент № '" + numberLine + "', ошибка ввода/вывода, системное сообщение:\n"
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public abstract T getValue();
    public abstract boolean compare(String inputElement);
    public abstract boolean compare(Integer inputElement);
}
