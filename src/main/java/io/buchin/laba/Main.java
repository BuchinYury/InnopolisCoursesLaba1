package main.java.io.buchin.laba;

import main.java.io.buchin.laba.parces.ExtractResours;
import main.java.io.buchin.laba.parces.WordDublicatDetected;


/**
 * Created by yuri on 11.02.17.
 */
public class Main {
    public static void main(String[] args) {

        WordDublicatDetected wordDublicatDetected = new WordDublicatDetected();

        Thread[] threads = new Thread[args.length];

        for (int i = 0; i < args.length; i++){
                threads[i] = new Thread(new ExtractResours(wordDublicatDetected, args[i]));
                threads[i].start();
        }

        for (Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(wordDublicatDetected.getSetLength());
        System.out.println("Парсинг ресурсов завершон");

    }
}
