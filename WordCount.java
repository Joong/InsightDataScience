/**
 * User: joong
 */

import java.io.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordCount {
    private static final String OUTPUT_DIR = "wc_output";
    private static final String OUTPUT_FILE = "wc_result.txt";
    private SortedMap<String, Integer> wordcounts = new TreeMap<>();

    public void mapper(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    for (String s : line.split(" ")) {
                        s = sanitize(s);
                        if (!wordcounts.containsKey(s)) {
                            wordcounts.put(s, 1);
                        } else {
                            wordcounts.put(s, wordcounts.get(s) + 1);
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String sanitize(String s) {
        return s.replaceAll(",", "").replaceAll("-", "").replaceAll("\\.", "").toLowerCase();
    }

    private void print(Map<String, Integer> wordcounts) {
        try {
            File output = new File(OUTPUT_DIR + File.separator + OUTPUT_FILE);
            if (!output.exists()) {
                output.createNewFile();
            }
            PrintWriter writer = new PrintWriter(output);
            for (Map.Entry<String, Integer> entry : wordcounts.entrySet()) {
                writer.println(entry.getKey() + " " + entry.getValue());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runWordCount(String path) {
        for (String file : new File(path).list()) {
            mapper(path + File.separator + file);
        }
        print(wordcounts);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("An input argument is expected");
            System.exit(1);
        }

        WordCount wc = new WordCount();
        wc.runWordCount(args[0]);
    }
}
