package main.java.io.buchin.labaVersion2.parces;

/**
 * Created by yuri on 11.02.17.
 */
public class ThreadStoped {
    private static volatile boolean threadWords = true;

    public static boolean executeThread() {
        return threadWords;
    }

    public static void stopedThreads() {
        ThreadStoped.threadWords = false;
    }
}
