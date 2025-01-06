import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ahmad Shekib Haidari
 * @version 1.0
 * @ in AI support in ExecuteServicse class in ExecuteServices.java
 * @Start date: 2025/01/06
 */

public class Word_Counter {

    private static final int Amount_of_files = 20;
    private static List<String> words_count = Collections.synchronizedList(new ArrayList<>());



    /*
     * This main mehtod to run the program.
     *
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(Amount_of_files);
        for (int i = 1; i <= Amount_of_files; i++) {
            String fileName = "assets/file_" + i + ".txt";
            executor.submit(new FileHandler(fileName));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=============================================");
        System.out.println("All files are processed.");
        System.out.println("\n");
        System.out.println("before to see result, i have a question:");
        System.out.println("why we have GitHub and we dont have GitSwitch?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("write your answer here:");
        String answer = scanner.nextLine();

        countWords();
    }
    /**
     *
     * @FILE HANDLER METHOD
     */
    static class FileHandler implements Runnable {
        private String names_of_file;


        public FileHandler(String fileName) {
            this.names_of_file= fileName;
        }

        /**
         * This method reads the file and adds the words to the list of words_count.
         */
        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(names_of_file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    synchronized (words_count) {
                        words_count.addAll(Arrays.asList(words));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void countWords() {
        Set<String> uniqueWords = new HashSet<>(words_count);
        String long_word = "";
        String short_word = words_count.isEmpty() ? "" : words_count.get(0);
        int totalLength = 0;

        for (String word : words_count) {
            if (word.length() > long_word.length()) {
                long_word = word;
            }
            if (word.length() < short_word.length()) {
                short_word = word;
            }
            totalLength += word.length();
        }

        double averageLength = words_count.isEmpty() ? 0 : (double) totalLength / words_count.size();

        System.out.println("================================================");
        System.out.println("Word Counter Results for Ap_workshop09 ");
        System.out.println("\n");
        System.out.println("sum of all words length: " + words_count.size());
        System.out.println("Total unique words (write one time): " + uniqueWords.size());
        System.out.println("Longest word: " + long_word + " Length: " + long_word.length());
        System.out.println("Shortest word: " + short_word + " Length: " + short_word.length());
        System.out.println("Average word length: " + averageLength);

    }
}







