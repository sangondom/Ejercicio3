package testsmell;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

/**
 * This class is utilized to write output to an HTML file
 */
public class ResultsWriter {

    private String outputFile;
    private FileWriter writer;

    /**
     * Creates the file into which output it to be written into. Results from each file will be stored in a new file
     *
     * @throws IOException
     */
    private ResultsWriter() throws IOException {
        String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
        outputFile = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "TestSmellDetection", time, "html");
        writer = new FileWriter(outputFile, false);
        writeHTMLHeader();
    }

    /**
     * Factory method that provides a new instance of the ResultsWriter
     *
     * @return new ResultsWriter instance
     * @throws IOException
     */
    public static ResultsWriter createResultsWriter() throws IOException {
        return new ResultsWriter();
    }

    /**
     * Writes column names into the HTML file
     *
     * @param columnNames the column names
     * @throws IOException
     */
    public void writeColumnName(List<String> columnNames) throws IOException {
        writeHTMLRow(columnNames, "th");
    }

    /**
     * Writes column values into the HTML file
     *
     * @param columnValues the column values
     * @throws IOException
     */
    public void writeLine(List<String> columnValues) throws IOException {
        writeHTMLRow(columnValues, "td");
    }

    /**
     * Appends the input values into the HTML file
     *
     * @param dataValues the data that needs to be written into the file
     * @throws IOException
     */
    private void writeHTMLRow(List<String> dataValues, String cellType) throws IOException {
        writer.append("<tr>");

        for (String value : dataValues) {
            writer.append("<").append(cellType).append(">")
                    .append(String.valueOf(value))
                    .append("</").append(cellType).append(">");
        }

        writer.append("</tr>").append(System.lineSeparator());
        writer.flush();
    }

    /**
     * Writes the HTML header into the file
     *
     * @throws IOException
     */
    private void writeHTMLHeader() throws IOException {
        writer.append("<!DOCTYPE html>").append(System.lineSeparator())
                .append("<html>").append(System.lineSeparator())
                .append("<head>").append(System.lineSeparator())
                .append("<title>Test Smell Detection Results</title>").append(System.lineSeparator())
                .append("</head>").append(System.lineSeparator())
                .append("<body>").append(System.lineSeparator())
                .append("<table border=\"1\">").append(System.lineSeparator());
    }

    /**
     * Closes the HTML tags to complete the file
     *
     * @throws IOException
     */
    public void closeFile() throws IOException {
        writer.append("</table>").append(System.lineSeparator())
                .append("</body>").append(System.lineSeparator())
                .append("</html>").append(System.lineSeparator());
        writer.close();
    }
}