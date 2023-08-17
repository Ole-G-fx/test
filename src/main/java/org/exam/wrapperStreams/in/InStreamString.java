package org.exam.wrapperStreams.in;

import org.exam.excep.WrongException;

import java.io.IOException;

/**
 * реализация обертки потока чтения для типа данных String, в currentElement сохраняет текущий
 *    элемент читаемого потока
 * @param <T> передача типа элемента
 */
public class InStreamString<T> extends InStreamAbsImpl {

    private String currentElement;
    public InStreamString(String fileName, boolean ascending){

        super(fileName, ascending);
        this.getNext();
    }
    public InStreamString(String fileName, int lengthBuffer, boolean ascending){

        super(fileName, lengthBuffer, ascending);
        this.getNext();
    }

    /**
     * реализация чтения из файла через BufferedReader и переход на следующий элемент
     *             обработка некоторых исключений читаемых данных
     * @return прочитанный элемент
     */
    @Override
    public String getNext() {

        numberLine++;
        boolean next = true;
        String input = null;
        while (next) {
            next = false;
            String line = null;
            try {

                line = super.reader.readLine();
            } catch (IOException e) {

                new WrongException("Неудача при чтении элемента № '" + numberLine + "', ошибка ввода/вывода, '"
                        + line + "' системное сообщение:\n" + e.getMessage());
                numberLine++;
            }

            if (line == null)
            {
                return null;
            }
            input = line;
            if ((input.equals(""))||input.contains(" ")) {

                new WrongException("Неверный формат в строке № '" + numberLine + "' значение: '" + line + "' в файле: "
                        + super.fileName + "\n" + " невозможно определить значение, возможно повреждены данные.");
                numberLine++;
                next = true;
                continue;
            }
            if (super.ascending) {

                if (currentElement != null && (currentElement.compareTo(input) > 0)) {

                    new WrongException("Исходный файл не упорядочен, элемент № '" + numberLine + "' со " +
                            "значением '" + input + "' в файле: " + super.fileName + "\n" +
                            " возможно повреждены данные.");
                    numberLine++;
                    next = true;
                }
            } else {

                if (currentElement != null && (currentElement.compareTo(input) < 0)) {

                    new WrongException("Исходный файл не упорядочен, элемент № '" + numberLine + "' со значением '"
                            + input + "' в файле: " + super.fileName + "\n" + " возможно повреждены данные.");
                    numberLine++;
                    next = true;
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
    public String getCurrentElement(){
        return currentElement;
    }

    /**
     * вернуть значение предельное для типа данных и в зависимости от сортировки по
     *              возрастанию/убыванию возвращает max/min элемент данного типа
     * @return возвращаемое значение
     */
    public String getValue(){

        if (super.ascending) {

            return String.valueOf(Character.MAX_VALUE);
        } else {

            return String.valueOf(Character.MIN_VALUE);
        }
    };

    /**
     * операция сравнения для элемента данного типа данных (необходимо для сортировки)
     * @param inputElement элемент для сравнения
     * @return результат больше/меньше
     */
    @Override
    public boolean compare(String inputElement) {

        int cmp = (currentElement.compareTo(inputElement));
        if (super.ascending) {

            if (cmp < 0) return true;
            return false;
        } else {

            if (cmp > 0) return true;
            return false;
        }
    }

    @Override
    public boolean compare(Object inputElement) {
        return compare((String) inputElement);
    }

    @Override
    public boolean compare(Integer inputElement) {
        new WrongException("Выполняется неверный сортировщик ");
        return false;
    }
}
