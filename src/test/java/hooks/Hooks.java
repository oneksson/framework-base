package hooks;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.opentest4j.AssertionFailedError;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static utils.WebDriverUtils.*;

import steps.LobbySteps;
import steps.LoginSteps;

public class Hooks {
    @BeforeEach
    public void setUp() {
        setUpBrowser();
        navegateTo(System.getProperty("appUrl"));
    }

    @AfterEach
    public void tearDown() {
        closeBrowser();
    }

    public void iniciaSesionConReintento() {
        int intentos = parseInt( getProperty("intentosLogin") );
        for (int i = 1; i <= intentos; i++) {
            try {
                iniciaSesion(i);
                break;
            } catch (AssertionFailedError e) { if (i == intentos) throw e; }
        }
    }

    @Step("Intento {nro}")
    public void iniciaSesion(int nro) {
        new LoginSteps()
                .ingresarCredenciales(System.getProperty("username"), System.getProperty("password"))
                .clickeaEnBotonIngresar();

    }
}