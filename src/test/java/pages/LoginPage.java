package pages;

import org.openqa.selenium.WebElement;

import static utils.WebElementUtils.getElementByXpath;

public class LoginPage {
    private final String tituloXpath = "//h2[text()='Ingresa a tu cuenta']";
    private final String usernameInputXpath = "//label[text()='Username']/following-sibling::input";
    private final String passwordInputXpath = "//input[@id='password']";
    private final String loginBotonXpath = "//button[text()='Ingresar']";
    private final String errorMensajeXpath = "//div[text()='Check the details you provided']";

    public WebElement getTitulo() {
        return getElementByXpath("Titulo", tituloXpath);
    }

    public WebElement getUsernameInput() {
        return getElementByXpath("Username Input", usernameInputXpath);
    }

    public WebElement getPasswordInput() {
        return getElementByXpath("Password Input", passwordInputXpath);
    }

    public WebElement getLoginBoton() {
        return getElementByXpath("Botón Login", loginBotonXpath);
    }

    public WebElement getErrorMensaje() {return getElementByXpath("Error", errorMensajeXpath);}

}