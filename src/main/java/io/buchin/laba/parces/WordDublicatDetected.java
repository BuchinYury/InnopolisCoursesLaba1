package main.java.io.buchin.laba.parces;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс отвечающий за остановку потоков
 * Created 11.02.17.
 * @author Бучин Юрий
 */
public class WordDublicatDetected {
    /**
     *  Множество отвечающее за хранение переданных слов
     */
    private Set<String> wordsSet = new HashSet<>();

    /**
     *  Метод возвращающий длину множества
     *  @return размер множества
     */
    public int getSetLength(){
        return wordsSet.size();
    }

    /**
     *  Метод помещающий переданное слово в множество слов
     *  @see ThreadStoped#stopedThreads()
     *  @param word - слово, которое нужно поместить в множество
     */
    public synchronized void putWord(String word){

        if (!wordsSet.add(word)){
            System.out.println("В ресурсах присутствуют дубликаты слов");
            ThreadStoped.stopedThreads();
        }

    }
}
