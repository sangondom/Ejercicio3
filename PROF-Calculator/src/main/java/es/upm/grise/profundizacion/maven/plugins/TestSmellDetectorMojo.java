package es.upm.grise.profundizacion.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import testsmell;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.StringReader;

@Mojo(name = "detect-test-smells")
public class TestSmellDetectorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.testOutputDirectory}")
    private String testOutputDirectory;

    public void execute() throws MojoExecutionException {
        getLog().info("Detecting test smells...");

        String urlTest = "https://github.com/GRISE-UPM/PROF-2023-Ejercicio3-datos/blob/main/src/test/java/es/upm/grise/prof/curso2023/ejercicio3/SUTTest.java";

        TestSmellDetector testSmellDetector = new TestSmellDetector(new DefaultThresholds());
        BufferedReader bufferedReader;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(apiUrl);

            HttpResponse response = httpClient.execute(request);

            // Verifica si la solicitud fue exitosa (código 200)
            if (response.getStatusLine().getStatusCode() == 200) {
                String fileContent = EntityUtils.toString(response.getEntity());

                // Crea un BufferedReader a partir del contenido del archivo
                bufferedReader = new BufferedReader(new StringReader(fileContent));
            }
        } catch (IOException e) {}

        BufferedReader in = bufferedReader;
        String str;

        String[] lineItem;
        TestFile testFile;
        List<TestFile> testFiles = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            // use comma as separator
            lineItem = str.split(",");

            //check if the test file has an associated production file
            if (lineItem.length == 2) {
                testFile = new TestFile(lineItem[0], lineItem[1], "");
            } else {
                testFile = new TestFile(lineItem[0], lineItem[1], lineItem[2]);
            }

            testFiles.add(testFile);
        }

        /*
          Initialize the output file - Create the output file and add the column names
         */
        ResultsWriter resultsWriter = ResultsWriter.createResultsWriter();
        List<String> columnNames;
        List<String> columnValues;

        columnNames = testSmellDetector.getTestSmellNames();
        columnNames.add(0, "App");
        columnNames.add(1, "TestClass");
        columnNames.add(2, "TestFilePath");
        columnNames.add(3, "ProductionFilePath");
        columnNames.add(4, "RelativeTestFilePath");
        columnNames.add(5, "RelativeProductionFilePath");
        columnNames.add(6, "NumberOfMethods");

        resultsWriter.writeColumnName(columnNames);

        /*
          Iterate through all test files to detect smells and then write the output
        */
        TestFile tempFile;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;
        for (TestFile file : testFiles) {
            date = new Date();
            System.out.println(dateFormat.format(date) + " Processing: " + file.getTestFilePath());
            System.out.println("Processing: " + file.getTestFilePath());

            //detect smells
            tempFile = testSmellDetector.detectSmells(file);

            //write output
            columnValues = new ArrayList<>();
            columnValues.add(file.getApp());
            columnValues.add(file.getTestFileName());
            columnValues.add(file.getTestFilePath());
            columnValues.add(file.getProductionFilePath());
            columnValues.add(file.getRelativeTestFilePath());
            columnValues.add(file.getRelativeProductionFilePath());
            columnValues.add(String.valueOf(file.getNumberOfTestMethods()));
            for (AbstractSmell smell : tempFile.getTestSmells()) {
                try {
                    columnValues.add(String.valueOf(smell.getNumberOfSmellyTests()));
                } catch (NullPointerException e) {
                    columnValues.add("");
                }
            }
            resultsWriter.writeLine(columnValues);
        }

        getLog().info("Test smells detection completed.");
    }
}