package es.upm.grise.profundizacion.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "detect-test-smells")
public class TestSmellDetectorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.testOutputDirectory}")
    private String testOutputDirectory;

    public void execute() throws MojoExecutionException {
        getLog().info("Detecting test smells...");

        // Aquí llamarías a tu lógica de detección de test smells (tsDetect)
        // Podrías ejecutar comandos de consola, llamar a clases Java, etc.

        // Ejemplo:
        // TestSmellDetector.detect(testOutputDirectory);

        getLog().info("Test smells detection completed.");
    }
}