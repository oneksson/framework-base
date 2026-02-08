package utils;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.System.getProperty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static utils.WebDriverUtils.driver;
import static utils.WebDriverUtils.explicitWait;

public class WebElementUtils {
    private static final Logger LOGGER = Logger.getLogger("Steps");

    public static WebElement getElementByXpath(String nombre, String xpath) {
        try { return driver.findElement(By.xpath(xpath)); }
        catch (NoSuchElementException e) {
            Assertions.fail("No se encontró el elemento : " + nombre);
            return null;
        }
    }

    public static List<WebElement> getElementsByXpath(String nombre, String xpath) {
        try { return driver.findElements(By.xpath(xpath)); }
        catch (NoSuchElementException e) {
            Assertions.fail("No se encontraron los elementos : " + nombre);
            return null;
        }
    }

    public WebElement getElement(String nombre, String xpath) {
        return getElementByXpath(nombre, xpath);
    }

    public static void waitAndCheckVisibility(String nombre, WebElement element) {
        try { explicitWait.until( visibilityOf(element) ); }
        catch (TimeoutException e) {Assertions.fail("El elemento " + nombre + " no puede ser visible después de " + getProperty("explicitWait") + " segundos");}
        assertTrue(element.isDisplayed(), "No se visualiza el elemento: " + nombre);
    }

    public static void waitAndCheckVisibilityWithTimeParameter(String nombre, WebElement element, Integer timeout) {
        try { new WebDriverWait(driver, Duration.ofSeconds(timeout)).until( visibilityOf(element) ); }
        catch (TimeoutException e) {Assertions.fail("El elemento " + nombre + " no puede ser visible después de " + getProperty("explicitWait") + " segundos");}
        assertTrue(element.isDisplayed(), "No se visualiza el elemento: " + nombre);
    }

    public static void waitAndCheckInvisibilityOfLocatorWithTimeParameter(String nombre, By locator, Integer timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            Assertions.fail("El elemento " + nombre + " no desapareció o sigue siendo visible después de " + timeout + " segundos");
        }
    }

    public static void waitAndCheckVisibilityOfLocatorWithTimeParameter(String nombre, By locator, Integer timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            Assertions.fail("El elemento " + nombre + " no pudo ser visible después de " + timeout + " segundos");
        }
    }

    public static WebElement waitAndClick(String nombre, WebElement element) {
        try { explicitWait.until( elementToBeClickable(element) ); }
        catch (TimeoutException e) { Assertions.fail("Elemento no clickeable: " + nombre); }
        element.click();
        return element;
    }

    public static void waitAndClickAndSwitchWindow(String nombre, WebElement element) {
        LOGGER.info("Título de la ventana origial: " + driver.getTitle());
        LOGGER.info("URL de la ventana original: " + driver.getCurrentUrl());

        String originalWindowHandle = driver.getWindowHandle();
        Set<String> originalWindowHandles = driver.getWindowHandles();
        LOGGER.info("Cantidad de ventanas abiertas: " + originalWindowHandles.size());

        waitAndClick(nombre, element);
        LOGGER.info("Clickeó el botón.");


        Set<String> allWindowHandles = driver.getWindowHandles();
        LOGGER.info("Cantidad de ventanas abiertas actual: " + allWindowHandles.size());

        String newWindowHandle = null;
        for (String handle : allWindowHandles) {
            if (!originalWindowHandles.contains(handle)) {
                newWindowHandle = handle;
                break;
            }
        }

        if (newWindowHandle != null) {
            driver.switchTo().window(newWindowHandle);
            LOGGER.info("Switcheó a la nueva ventana: " + newWindowHandle);
            LOGGER.info("Título de la nueva ventana: " + driver.getTitle());
            LOGGER.info("URL de la nueva ventana: " + driver.getCurrentUrl());

        }
    }

    public static void sleep(int seconds) {
        try { Thread.sleep(seconds * 1000); }
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    public static void hover(WebElement elemento) {
        new Actions(driver).moveToElement(elemento).perform();
    }

    @Step("Cambia a la pestaña {windowName}")
    public static void switchWindow(String windowName) {
        boolean windowFind = false;
        Set<String> windows = driver.getWindowHandles();
        LOGGER.info("Total open windows: " + windows.size());
        for (String window : windows) {
            driver.switchTo().window(window);
            LOGGER.info("Switched to new window handle: " + window);
            LOGGER.info("New Window Title (might be same): " + driver.getTitle());
            LOGGER.info("New Window URL: " + driver.getCurrentUrl());
            if (driver.getTitle().contains(windowName)) {
                windowFind = true;
                break;
            }
        }
        if (!windowFind) throw new AssertionError("No se encontro la pestaña " + windowName);
    }



}