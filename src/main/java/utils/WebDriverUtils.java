package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.System.getProperty;

public class WebDriverUtils {
    private static final Logger LOGGER = Logger.getLogger("Steps");
    public static WebDriver driver;
    public static WebDriverWait explicitWait;

    public static void setUpBrowser() {
        driver = getNewChromeDriver();
        long segundosDeEspera = Long.parseLong(getProperty("explicitWait", "10"));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(segundosDeEspera));
    }

    public static void closeBrowser() { driver.quit(); }

    public static void navegateTo(String url) { driver.get(url); }

    public static void bajarConTeclado() {
        new Actions(driver)
                .sendKeys(Keys.PAGE_DOWN)
                .perform();
    }

    @Step("Actualiza página")
    public static void refreshBrowser(){ driver.navigate().refresh(); }

    private static WebDriver getNewChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        boolean headless = Boolean.parseBoolean(getProperty("headless"));
        if (headless) options.addArguments("--headless=new");
        
        String rutaDescargas = Paths.get("build/downloads").toAbsolutePath().toString();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", rutaDescargas);
        options.setExperimentalOption("prefs", chromePrefs);

        WebDriverManager.chromedriver().setup();
        WebDriver DRIVER = new ChromeDriver(options);
        DRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getProperty("implicitWait", "10"))));
        DRIVER.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(getProperty("pageLoadWait", "10"))));
        return DRIVER;
    }

    @Step("Cerrar pestaña actual y volver a la principal")
    public static void closeAndSwitchToFirstWindow() {
        // 1. Cerrar la pestaña actual
        driver.close();

        // 2. Obtener todas las pestañas disponibles
        Set<String> handles = driver.getWindowHandles();
        List<String> tabs = new ArrayList<>(handles);

        // 3. Cambiar a la primera (índice 0)
        if (!tabs.isEmpty()) {
            driver.switchTo().window(tabs.get(0));
            LOGGER.info("Se cerró la pestaña y se regresó a: " + driver.getTitle());
        } else {
            Assertions.fail("No quedan pestañas abiertas a las cuales cambiar.");
        }
    }

    @Step("Espera estática")
    public static WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
}