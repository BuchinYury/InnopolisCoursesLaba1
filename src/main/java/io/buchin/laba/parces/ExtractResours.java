package main.java.io.buchin.laba.parces;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс отвечающий за работу с ресурсами
 * Created 11.02.17.
 * @author Бучин Юрий
 */
public class ExtractResours implements Runnable {
    /**
     *  Буфер для накопления слов, отвечает за поиск дубликотов
     *  @see WordDublicatDetected
     */
    private WordDublicatDetected buffer;

    /**
     *  Строка содержащая путь до ресурса
     */
    private String roadToResours;

    /**
     *  Конструктор класса инициализирующий необходимые переменные
     *
     */
    public ExtractResours(WordDublicatDetected buffer, String roadToResours) {
        this.buffer = buffer;
        this.roadToResours = roadToResours;
    }

    /**
     *  Точка входа потока
     *  @see ThreadStoped - static клас, отвечающий за остановку потоков
     *  @see ExtractResours#trueIsURLOrFalseIsFile(String) - метод
     *  @see ExtractResours#extractResoursFromFile(String)
     *  @see ExtractResours#extractResoursFromURL(String)
     */
    @Override
    public void run() {
        if (ThreadStoped.executeThread()) {
            if (trueIsURLOrFalseIsFile(roadToResours)) {
                if (ThreadStoped.executeThread()) extractResoursFromURL(roadToResours);
            } else if (ThreadStoped.executeThread()) extractResoursFromFile(roadToResours);
        }
    }

    /**
     *  Метод отвечающий за парчсинг ресурсов из файла
     *  @see ThreadStoped
     *  @see ExtractResours#extractLine(BufferedReader, WordDublicatDetected, String)
     *  @param roadToResours - путь до файла
     */
    private void extractResoursFromFile(String roadToResours) {
        if (ThreadStoped.executeThread()) {
            System.out.println("Run Extract File");

            try (BufferedReader in = new BufferedReader(new FileReader(roadToResours))) {
                if (ThreadStoped.executeThread()) {
                    extractLine(in, buffer, "File");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Некоректный путь к ресурсу");
                ThreadStoped.stopedThreads();
            } catch (IOException e) {
                System.out.println("Некоректный путь к ресурсу");
                ThreadStoped.stopedThreads();
            }
        }

    }

    /**
     *  Метод отвечающий за парчсинг ресурсов из URL
     *  @see ThreadStoped
     *  @see ExtractResours#extractLine(BufferedReader, WordDublicatDetected, String)
     *  @param roadToResours - путь до URL
     */
    private void extractResoursFromURL(String roadToResours) {
        if (ThreadStoped.executeThread()) {
            System.out.println("Run Extract URL");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(roadToResours).openStream()))) {
                if (ThreadStoped.executeThread()) {
                    extractLine(br, buffer, "URL");
                }
            } catch (MalformedURLException e1) {
                System.out.println("Некоректный путь к ресурсу");
                ThreadStoped.stopedThreads();
            } catch (IOException e1) {
                System.out.println("Некоректный путь к ресурсу");
                ThreadStoped.stopedThreads();
            }
        }
    }

    /**
     *  Метод отвечающий за парчсинг ресурсов из файла
     *  @see ThreadStoped
     *  @see ExtractResours#inspectResours(String)
     *  @see ExtractResours#parseLine(String)
     *  @see WordDublicatDetected#putWord(String)
     *  @param in - буфер с извлеченными ресурсами
     *  @param buffer - буфер отвечающий за поиск дубликатов
     *  @param resoursAlias - срока хранящая вид ресурса
     *
     */
    private void extractLine(BufferedReader in, WordDublicatDetected buffer, String resoursAlias) throws IOException {
        String[] words;
        String line;

        while ((line = in.readLine()) != null) {
            if (ThreadStoped.executeThread()) {
                if (inspectResours(line)) {
                    System.out.println("В " + resoursAlias + " найдены недопустимые символы");
                    ThreadStoped.stopedThreads();
                    break;
                } else {
                    words = parseLine(line);

                    for (String word : words) {
                        if (ThreadStoped.executeThread()) buffer.putWord(word);
                        else break;
                    }

                    if (!ThreadStoped.executeThread()) break;

                    System.out.println("Парсинг ресурса произведен успешно. Дубликатов не найдено.");
                }
            }

        }

    }

    /**
     *  Метод отвечающий за определния вида пути с помощью регулярного выражения
     *  @param roadToResours - путь до ресурса
     *  @return Возвращает значение true, если переданный путь - это путь до файла
     *                              false, если переданный путь - это путь до URL
     */
    private boolean trueIsURLOrFalseIsFile(String roadToResours) {
        String regexp = "^http:\\/\\/.{1,}";

        boolean result = regExp(regexp, roadToResours);

        return result;

    }

    /**
     *  Метод отвечающий за поиск недопустимых символов в строке с помощью регулярного выражения
     *  @param resours - строка, которую необходимо проверить на содержание недопустимых символов
     *  @return Возвращает значение true, если переданая строка содержит недопустимые символы
     *                              false, если переданная строка не содержит недопустимые символы
     */
    private boolean inspectResours(String resours) {
        System.out.println("Run inspect");

        String regexp = "([^А-я \\-!\"',.:;?])";

        boolean result = regExp(regexp, resours);

        return result;

    }

    /**
     *  Метод отвечающий за проверку строки с помощью регулярноговыражения
     *  @param regexp - регулярное выражение, которым нужно проверить строку
     *  @param regString - строка, которую необходимо проверить
     *  @return Возвращает значение true, если переданая строка содержить, подстроку определенную регулярным выражением
     *                              false, если переданная строка не содержит, подстроку определенную регулярным выражением
     */
    private boolean regExp(String regexp, String regString) {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(regString);

        boolean result = matcher.find();

        return result;
    }

    /**
     *  Метод отвечающий за разбиение переданной строки на слова
     *  @param resours - строка, которую необходимо разбить на слова
     *  @return Возвращает массив слов содержащихся в строке
     */
    private String[] parseLine(String resours) {
        System.out.println("Run Parse");

        String[] parseResours;

        parseResours = resours.toLowerCase().split("[^А-я]+");

        return parseResours;

    }
}
