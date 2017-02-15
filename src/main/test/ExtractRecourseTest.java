package main.test;

import main.java.io.buchin.laba.parces.ExtractResours;
import main.java.io.buchin.laba.parces.WordDublicatDetected;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yuri on 15.02.17.
 */
class ExtractRecourseTest {
    @Test
    void unacceptableSymbolsInFile() {
        LinkedList<String> list = new LinkedList<>();
        WordDublicatDetected buffer = new WordDublicatDetected();
        ExtractResours resours = new ExtractResours(buffer, "src/main/test/resourstotest/1.txt"){
            @Override
            protected void printIn(String mes) { list.add(mes); }
        };
        Thread thread = new Thread(resours);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(list.getLast().equals("В File найдены недопустимые символы"));
    }

    @Test
    void dublicateWordInFile() {
        LinkedList<String> list = new LinkedList<>();
        WordDublicatDetected buffer = new WordDublicatDetected(){
            @Override
            protected void printIn(String mes) { list.add(mes); }
        };
        ExtractResours resours = new ExtractResours(buffer, "src/main/test/resourstotest/2.txt");
        Thread thread = new Thread(resours);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(list.getLast().equals("В ресурсах присутствуют дубликаты слов"));
    }

    @Test
    void goodWorkExtractFile() {
        LinkedList<String> list = new LinkedList<>();
        WordDublicatDetected buffer = new WordDublicatDetected();
        ExtractResours resours = new ExtractResours(buffer, "src/main/test/resourstotest/4.txt");
        Thread thread = new Thread(resours);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(buffer.getSetLength() == 5);
    }

    @Test
    void unacceptableSymbolsInURL() {
        LinkedList<String> list = new LinkedList<>();
        WordDublicatDetected buffer = new WordDublicatDetected();
        ExtractResours resours = new ExtractResours(buffer, "http://joomla.ru/robots.txt"){
            @Override
            protected void printIn(String mes) { list.add(mes); }
        };
        Thread thread = new Thread(resours);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(list.getLast().equals("В URL найдены недопустимые символы"));
    }


}