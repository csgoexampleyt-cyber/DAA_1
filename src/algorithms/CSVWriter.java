package algorithms;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVWriter {
    private PrintWriter writer;

    public CSVWriter(String filename) throws IOException {
        writer = new PrintWriter(new FileWriter(filename));
    }

    public void addRow(String... columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] != null) {
                String value = columns[i].replace("\"", "\"\"");
                if (value.contains(",") || value.contains("\"")) {
                    sb.append("\"").append(value).append("\"");
                } else {
                    sb.append(value);
                }
            }
            if (i < columns.length - 1) {
                sb.append(",");
            }
        }
        writer.println(sb.toString());
    }

    public void writeToFile() {
        writer.close();
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}