package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

// Importamos la clase principal que contiene la definición estática del driver
import static utils.WebDriverUtils.driver;

public class DownloadHelper {

    // Definimos la carpeta de descargas basándonos en la misma ruta que usa WebDriverUtils
    // Nota: Si la ruta de descargas es compleja, esta variable podría ser estática en WebDriverUtils también.
    private static final String DOWNLOAD_DIR;

    static {
        // Obtenemos la ruta absoluta de las descargas que usa getNewChromeDriver()
        DOWNLOAD_DIR = Paths.get("build/downloads").toAbsolutePath().toString();
    }

    /**
     * Espera de forma explícita hasta que un archivo CSV sea descargado
     * e identifica el archivo por ser el más reciente con extensión .csv.
     * * @param timeoutInSeconds El tiempo máximo de espera antes de lanzar una excepción.
     * @return El objeto File del archivo CSV recién descargado.
     * @throws RuntimeException Si el archivo no es encontrado o la descarga falla después del timeout.
     */
    public File waitForFileDownload(int timeoutInSeconds) {

        // El WebDriverUtils.driver está disponible aquí
        if (driver == null) {
            throw new IllegalStateException("El WebDriver no ha sido inicializado. Ejecuta setUpBrowser() primero.");
        }

        File downloadFolder = new File(DOWNLOAD_DIR);

        // 1. Configurar la espera máxima
        // Usamos el driver estático
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until((WebDriver wd) -> {

            Optional<File> latestFile = getLatestCsvFile(downloadFolder);

            // 2. Condición de éxito: El archivo existe Y su tamaño es > 0 bytes
            return latestFile.isPresent() && latestFile.get().length() > 0;
        });

        // 3. Devolver el archivo más reciente una vez que la espera ha terminado
        return getLatestCsvFile(downloadFolder)
                .orElseThrow(() -> new RuntimeException("Error: No se encontró ningún archivo CSV después del timeout."));
    }

    /**
     * Método helper que encuentra el archivo CSV más reciente en el directorio.
     */
    private Optional<File> getLatestCsvFile(File directory) {
        // Listar archivos, filtrar por extensión .csv y encontrar el más reciente.
        // Manejamos el caso de que la carpeta esté vacía o no exista (listFiles devuelve null)
        File[] files = directory.listFiles();
        if (files == null) {
            return Optional.empty();
        }

        return Arrays.stream(files)
                .filter(f -> f.isFile() && f.getName().toLowerCase().endsWith(".csv"))
                .max(Comparator.comparingLong(File::lastModified));
    }
}