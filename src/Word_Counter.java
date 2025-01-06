import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ahmad Shekib Haidari
 * @version 1.0
 * @Start date: 2025/01/06
 */

public class Word_Counter {

    private static final int Amount_of_files = 20;
    private static List<String> words_count = Collections.synchronizedList(new ArrayList<>());

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








}
