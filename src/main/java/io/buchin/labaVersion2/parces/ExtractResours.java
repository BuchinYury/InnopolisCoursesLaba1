package main.java.io.buchin.labaVersion2.parces;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuri on 11.02.17.
 */
public class ExtractResours implements Runnable {

    private WordDublicatDetected buffer;
    private String roadToResours;

    public ExtractResours(WordDublicatDetected buffer, String roadToResours) {
        this.buffer = buffer;
        this.roadToResours = roadToResours;
    }

    @Override
    public void run() {
        if (ThreadStoped.executeThread()) {
            if (trueIsURLOrFalseIsFile(roadToResours)) {
                if (ThreadStoped.executeThread()) extractResoursFromURL(roadToResours);
            } else if (ThreadStoped.executeThread()) extractResoursFromFile(roadToResours);
        }
    }

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

    private boolean trueIsURLOrFalseIsFile(String roadToResours) {
        String regexp = "^http:\\/\\/.{1,}";

        boolean result = regExp(regexp, roadToResours);

        return result;

    }

    private boolean inspectResours(String resours) {
        System.out.println("Run inspect");

        String regexp = "([^А-я \\-!\"',.:;?])";

        boolean result = regExp(regexp, resours);

        return result;

    }

    private boolean regExp(String regexp, String regString) {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(regString);

        boolean result = matcher.find();

        return result;
    }

    private String[] parseLine(String resours) {
        System.out.println("Run Parse");

        String[] parseResours;

        parseResours = resours.toLowerCase().split("[^А-я]+");

        return parseResours;

    }
}
