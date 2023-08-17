package org.exam.wrapperStreams.in;

import org.exam.excep.WrongException;
import java.io.IOException;

/**
 * реализация обертки потока чтения для типа данных Integer, в currentElement сохраняет текущий
 *     элемент читаемого потока
 * @param <T> передача типа элемента
 */
public class InStreamInteger<T> extends InStreamAbsImpl {

    private Integer currentElement;

    public InStreamInteger(String fileName, boolean ascending) {

        super(fileName, ascending);
        this.getNext();
    }

    public InStreamInteger(String fileName, int lengthBuffer, boolean ascending) {

        super(fileName, lengthBuffer, ascending);
        this.getNext();
    }

    /**
     * реализация чтения из файла через BufferedReader и переход на следующий элемент
     *             обработка некоторых исключений читаемых данных
     * @return прочитанный элемент
     */
    @Override
    public Integer getNext() {

        boolean next = true;
        Integer input = null;
        while (next) {

            numberLine++;
            next = false;
            String line = null;
            try {

                line = super.reader.readLine();
            } catch (IOException e) {

                new WrongException("Неудача при чтении элемента № '" + numberLine + "', ошибка ввода/вывода, '"
                        + line + "' системное сообщение:\n" + e.getMessage());
                next = true;
                numberLine++;
            }
            if (line == null) return null;
            try {

                input = Integer.parseInt(line);
            } catch (NumberFormatException e) {

                new WrongException("Неверный формат в строке № '" + numberLine + "' значение: '" + line +
                        "' в файле: " + super.fileName + "  \n"
                        + " невозможно определить значение, возможно повреждены данные.");
                next = true;
                numberLine++;
                continue;
            }
            if (super.ascending) {

                if (currentElement != null && (currentElement.compareTo(input) > 0)) {

                    new WrongException("Исходный файл не упорядочен, элемент № '" + numberLine + "' со " +
                            "значением '" + input + "' в файле: " + super.fileName + "\n" +
                            " возможно повреждены данные.");
                    next = true;
                    numberLine++;
                }
            } else {

                if (currentElement != null && (currentElement.compareTo(input) < 0)) {

                    new WrongException("Исходный файл не упорядочен, элемент № '" + numberLine + "' со значением '"
                            + input + "' в файле: " + super.fileName + "\n" + " возможно повреждены данные.");
                    next = true;
                    numberLine++;
                }
            }
        }
        currentElement = input;
        return input;
    }

    /**
     * возврат текущего элемента
     * @return текущий элемент
     */
    @Override
    public Integer getCurrentElement() {

        return currentElement;
    }

    /**
     * вернуть значение предельное для типа данных и в зависимости от сортировки по
     *              возрастанию/убыванию возвращает max/min элемент данного типа
     * @return возвращаемое значение
     */
    @Override
    public Integer getValue() {

        if (super.ascending) {

            return Integer.MAX_VALUE;
        } else {

            return Integer.MIN_VALUE;
        }
    }

    /**
     * операция сравнения для элемента данного типа данных (необходимо для сортировки)
     * @param inputElement элемент для сравнения
     * @return результат больше/меньше
     */
    @Override
    public boolean compare(Integer inputElement) {

        if (super.ascending) {

            return (currentElement < inputElement);
        } else {

            return (currentElement > inputElement);
        }
    }

    @Override
    public boolean compare(Object inputElement) {
        return compare((Integer) inputElement);
    }

    @Override
    public boolean compare(String inputElement) {
        new WrongException("Выполняется неверный сортировщик ");
        return false;
    }
}
