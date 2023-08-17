package org.exam.excep;

/**
 * выкидывание оповещений пользователю в консоль
 */
public class WrongException {
    public WrongException(String s) {

        System.out.println(s);
        //возможно добавить логирование в файл, но следует учесть и логи с прерываниями
    }
}
