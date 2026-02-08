package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import pages.LoginPage;
import static utils.WebElementUtils.waitAndCheckVisibility;
import static utils.WebElementUtils.waitAndClick;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();

    @Step("Login: Visualiza elementos de login")
    public LoginSteps visualizaElementosDeLogin() {
        waitAndCheckVisibility("Título",loginPage.getTitulo());
        waitAndCheckVisibility("input Username",loginPage.getUsernameInput());
        waitAndCheckVisibility("input Password",loginPage.getPasswordInput());
        waitAndCheckVisibility("Botón iniciar sesión",loginPage.getLoginBoton());

        return this;
    }

    @Step("Login: Ingresa credenciales")
    public LoginSteps ingresarCredenciales(String username, String password) {
        loginPage.getUsernameInput().sendKeys("");
        loginPage.getUsernameInput().sendKeys(Keys.SHIFT, Keys.ARROW_UP);
        loginPage.getUsernameInput().sendKeys(Keys.BACK_SPACE);
        loginPage.getUsernameInput().sendKeys(username);
        loginPage.getPasswordInput().sendKeys("");
        loginPage.getPasswordInput().sendKeys(Keys.SHIFT, Keys.ARROW_UP);
        loginPage.getPasswordInput().sendKeys(Keys.BACK_SPACE);
        loginPage.getPasswordInput().sendKeys(password);
        return this;
    }

    @Step("Login: Clickea en botón ingresar")
    public LoginSteps clickeaEnBotonIngresar() {
        try{
            waitAndClick("Botón Login", loginPage.getLoginBoton());
        } catch (StaleElementReferenceException e) {
            waitAndClick("Botón Login", loginPage.getLoginBoton());
        }
        return this;

    }

    @Step("Login: Limpia input username")
    public LoginSteps limpiarUsernameInput() {
        loginPage.getUsernameInput().clear();
        return this;
    }


    @Step("Login: Limpia input password")
    public LoginSteps limpiarPasswordInput() {
        loginPage.getPasswordInput().clear();
        return this;
    }


    @Step("Login: Valida mensaje de error")
    public LoginSteps validarMensajeDeError() {
        waitAndCheckVisibility("Mensaje de error al iniciar sesión",loginPage.getErrorMensaje());
        return this;
    }
}