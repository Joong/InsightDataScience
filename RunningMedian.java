/**
 * User: joong
 */

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RunningMedian {
    private static final String OUTPUT_DIR = "wc_output";
    private static final String OUTPUT_FILE = "med_result.txt";
    private final File output = new File(OUTPUT_DIR + File.separator + OUTPUT_FILE);

    public void mapper(String path) {
        List<Integer> wordcounts = new ArrayList<>();
        if (output.exists()) {
            output.delete();
        }

        try {
            PrintWriter writer = new PrintWriter(output);
            List<Integer> counter = new ArrayList<Integer>();
            for (String filename : listAndOrderFiles(path)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                        path + File.separator + filename)));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) {
                        counter.add(0);
                    } else {
                        counter.add(line.split(" ").length);
                    }
                    Collections.sort(counter);
                    writer.println(median(counter));
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] listAndOrderFiles(String path) {
        String[] dirs = new File(path).list();
        Arrays.sort(dirs);
        return dirs;
    }

    private String median(List<Integer> m) {
        NumberFormat formatter = new DecimalFormat("#0.0");
        int middle = m.size() / 2;
        if (m.size() % 2 == 1) {
            return formatter.format(m.get(middle));
        } else {
            return formatter.format((m.get(middle - 1) + m.get(middle)) / 2.0);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("An input argument is expected");
            System.exit(1);
        }

        RunningMedian runningMedian = new RunningMedian();
        runningMedian.mapper(args[0]);
    }
}
