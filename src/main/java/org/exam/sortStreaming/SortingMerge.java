package org.exam.sortStreaming;

import org.exam.excep.WrongException;
import org.exam.wrapperStreams.in.InStream;
import org.exam.wrapperStreams.out.OutStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * SortingMerge Основной модуль управления проекта принимает управление от TaskFileMerging
 * Свойства содержат коллекцию потоков чтения (из файла) и поток записи (в файл).
 */
public class SortingMerge {
    private Collection<InStream> readers = new ArrayList<>();
    private OutStream writer;

    /**
     * метод Start() - подготавливает потоки, запускает сортировку, закрывает потоки, запускает
     *        анализатор строки входящих параметров args[]
     * @param args - строка параметров из консоли
     */
    public void start(String[] args){

        writer = ParsArgsAndCreateStreams.parsing(readers, args);
        Collection<InStream> copy_readers = new ArrayList<>();
        for (InStream reader : readers){

            copy_readers.add(reader);
        }
        sortIt();

        writer.close();
        for (InStream reader: copy_readers) {

            reader.close();
        }
        new WrongException("Программа выполнена успешно, данные отсортированы результат записан в файл");
    }

    /**
     *     метод sortIt() - непосредственно сортировка слиянием, оперирует потоками чтения/записи
     *         получает начальные значения из класса-обертки, алгоритм сравнения тоже реализован
     *         в классе-обертке
     */
    private void sortIt(){
        
        InStream element = readers.iterator().next();
        Object minMax = element.getValue();

        while (readers.size() > 0){

            for (InStream reader : readers) {

                if (reader.compare(minMax)) {

                    minMax = reader.getCurrentElement();
                    element = reader;
                }
            }
            writer.setNext(minMax);
            minMax = element.getValue();
            if (element.ready()) {

                if (Objects.isNull(element.getNext())) {

                    readers.remove(element);
                };
            } else {

                readers.remove(element);
            };
        }
    }
}
