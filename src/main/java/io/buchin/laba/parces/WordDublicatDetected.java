package main.java.io.buchin.laba.parces;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuri on 11.02.17.
 */
public class WordDublicatDetected {
    private Set<String> wordsList = new HashSet<>();

    public int getListLength(){
        return wordsList.size();
    }

    public synchronized void putWord(String word){

        if (!wordsList.add(word)){
            System.out.println("В ресурсах присутствуют дубликаты слов");
            ThreadStoped.stopedThreads();
        }

    }
}
