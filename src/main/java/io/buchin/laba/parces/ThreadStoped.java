package main.java.io.buchin.laba.parces;

/**
 * Класс отвечающий за остановку потоков
 * Created 11.02.17.
 * @author Бучин Юрий
 */
public class ThreadStoped {
    /**
     *  Флаг контролирующий работу потоков
     */
    private static volatile boolean threadWorks = true;

    /**
     *  Метод отвечающий за нахождение потоков в работающем состоянии
     *  @return значение переменной threadWorks
     */
    public static boolean executeThread() {
        return threadWorks;
    }

    /**
     *  Метод отвечающий за остановку потоков
     */
    public static void stopedThreads() {
        ThreadStoped.threadWorks = false;
    }
}
