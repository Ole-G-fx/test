package org.exam.sortStreaming;

import org.exam.excep.GetHelp;
import org.exam.excep.WrongException;
import org.exam.wrapperStreams.in.*;
import org.exam.wrapperStreams.out.*;

import java.io.File;
import java.util.Collection;

/**
 * ParsArgsAndCreateStreams функция разбора аргументов командной строки, соответствует условиям:
 *     '-i' сортировка чисел типа Integer
 *     '-s' сортировка строк символов в кодировке ASCII
 *     '-a' по умолчанию (необязательный параметр) сортировка в порядке возрастания элементов
 *     '-d' сортировка в порядке убывания элементов
 *     '-h' или '-help' вывод справочной информации
 *     '-l' (необязательный параметр) размер буфера чтения/записи в количестве символов, чем
 *          больше размер тем больше оперативной памяти компьютера можно использовать
 *          стандартный размер для BufferedReader 8192 символа, если указать только '-l',
 *          то размер будет 524228 символов или можно указать свой размер '-l65536',
 *          ограничено размерностью Integer защиты от некорректного ввода пока нет
 *          т.е. отрицательные значения приведут к вылету
 *     далее следует указать имя выходного файла, (обязательное) и через пробел имена входных файлов,
 *     не менее одного.
 *     Примеры запуска из командной строки для Windows:
 *     java -jar program.jar -i -a out.txt in.txt (для целых чисел по возрастанию)
 *     java -jar program.jar -s out.txt in1.txt in2.txt in3.txt (для строк по возрастанию)
 *     java -jar program.jar -d -s out.txt in1.txt in2.txt (для строк по убыванию)
 *     В зависимости от указанных данных создаются потоки чтения/записи. Коллекция объектов
 *     для чтения и один объект для записи. Коллекция возвращается через ссылку входного параметра, а
 *     объект для записи в файл возвращается через return.
 *     Внутрь объектов передается информация об типе данных, порядке сортировки, размер буфера, имя
 *     файлаоткрываемого для чтения/записи.
 *      В свойствах указаны вспомогательные флаги для выявления исключений, передачи некоторых параметров
 *     в конструктор другого класса
 */
public class ParsArgsAndCreateStreams {

    private static OutStream writer;
    private static boolean isInteger;
    private static boolean ascending = true;
    private static boolean existRequiredParameter = false;
    private static boolean lengthBufferUse = false;
    private static int lengthBuffer = 524288;

    /**
     *  parsing() - основной запускающий метод, через него работают остальные, разбивает весь процесс
     *  анализа на несколько этапов
     * @param readers передача коллекции потоков чтения
     * @param args строка параметров из консоли
     * @return передача потока записи
     */
    static OutStream parsing(Collection<InStream> readers, String[] args) {

        if (args.length < 3) {

            throw new RuntimeException("Отсутствуют или неверное количество необходимх параметров в формате: "
                    + "java -jar program.jar -i(-s) -a output.txt input1.txt input2.txt input3.txt");
        } else {

            int amountInputFiles = 0;
            boolean isFirstNameFile = true;
            for (int i = 0; i < args.length; i++) {

                if (args[i].startsWith("-")) {

                    parsingFistArgs(args[i]);
                } else {

                    if (isFirstNameFile) {

                        isFirstNameFile = false;
                        createWriterStream(args[i]);
                    } else {

                        amountInputFiles++;
                        createReaderStreams(args[i], readers);
                    }
                }
            }
            if (isFirstNameFile) {

                throw new RuntimeException("Отсутствует необходимый параметор (output.txt) в формате: "
                        + "java -jar program.jar -i(-s) -a output.txt input1.txt input2.txt input3.txt");
            }
            if (amountInputFiles < 1) {

                throw new RuntimeException("Отсутствует необходимый параметор (input.txt) в формате: "
                        + "java -jar program.jar -i(-s) -a output.txt input1.txt input2.txt input3.txt");
            }
        }
        return writer;
    }

    /**
     * parsingFistArgs() - выполняет анализ первых коротких параметров командной строки, заполняет
     * соответствующие свойства корневого класса
     * @param arg конкретный параметр из консоли
     */
    private static void parsingFistArgs(String arg) {

        if (arg.equals("-d")) {

            ascending = false;
        } else if (arg.equals("-h")||arg.equals("-help")) {

            new GetHelp();
        } else if (arg.equals("-i")) {

            existRequiredParameter = true;
            isInteger = true;
        } else if (arg.equals("-s")) {

            existRequiredParameter = true;
            isInteger = false;
        } else if (arg.startsWith("-l")) {

            if (arg.equals("-l")) {

                lengthBufferUse = true;
            } else if ((arg.startsWith("-l") && arg.length() > 2)) {

                lengthBufferUse = true;
                lengthBuffer = Integer.valueOf(arg.substring(2));
            }
        }
        if (!existRequiredParameter) {

            throw new RuntimeException("Отсутствует необходимый параметор ('i' или 's') в формате: "
                    + "java -jar program.jar -i(-s) -a output.txt input1.txt input2.txt input3.txt");
        }
    }

    /**
     * инициирует создание класса-оболочки для потока записи
     * @param arg конкретный параметр из консоли
     */
    private static void createWriterStream(String arg) {

        OutStream outStream;
        if (lengthBufferUse) {

            if (isInteger) {

                outStream = new <Integer>OutStreamInteger(arg, lengthBuffer);
            } else {

                outStream = new <String>OutStreamString(arg, lengthBuffer);
            }
        } else {

            if (isInteger) {

                outStream = new <Integer>OutStreamInteger(arg);
            } else {

                outStream = new <String>OutStreamString(arg);
            }
        }
        writer = outStream;
    }

    /**
     * инициирует создание коллекции классов-оболочек для потока чтения
     * @param arg конкретный параметр из консоли
     * @param readers коллекция лдя заполнения элементами
     */
    private static void createReaderStreams(String arg, Collection<InStream> readers) {

        if (new File(arg).exists()) {

            InStream inStream = null;
            if (lengthBufferUse) {

                if (isInteger) {

                    inStream = new <Integer>InStreamInteger(arg, lengthBuffer, ascending);
                } else {

                    inStream = new <String>InStreamString(arg, lengthBuffer, ascending);
                }
            } else {

                if (isInteger) {

                    inStream = new <Integer>InStreamInteger(arg, ascending);
                } else {

                    inStream = new <String>InStreamString(arg, ascending);
                }
            }
            if (inStream.getCurrentElement() == null) {

                new WrongException("Файл '" + arg + "' пустой или поврежден \n" +
                        " выполнение будет продолжено без учета проврежденных данных");
            } else {

                readers.add(inStream);
            }
        } else {

            new WrongException("Не найден или недоступен файл '" + arg +
                    "' выполнение программы продолжено \n");
        }
    }
}
