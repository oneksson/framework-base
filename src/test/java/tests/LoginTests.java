package tests;

import hooks.Hooks;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.LobbySteps;
import steps.LoginSteps;
import steps.NavSteps;

@Tag("login")
@Epic("Login")
@DisplayName("Login")
public class LoginTests extends Hooks {

    @Test @Tag("id-1")
    @DisplayName("El usuario inicia sesión")
    public void elUsuarioIniciaSesion() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales(System.getProperty("username"), System.getProperty("password"))
                .clickeaEnBotonIngresar();

        new NavSteps()
                .visualizaElementosDeScm();
    }

    @Test @Tag("id-2")
    @DisplayName("El usuario intenta iniciar sesión con password invalida")
    public void elUsuarioIniciarSesionConPasswordInvalida() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales(System.getProperty("username"), "password")
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();

    }

    @Test @Tag("id-3")
    @DisplayName("El usuario intenta iniciar sesión con username invalido")
    public void elUsuarioIniciarSesionConUsernameInvalido() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales("1234", System.getProperty("password"))
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();

    }

    @Test @Tag("id-4")
    @DisplayName("El usuario intenta iniciar sesión sin completar campos")
    public void elUsuarioIniciarSesionSinCompletarCampos() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales("","")
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();

    }

    @Test @Tag("id-5")
    @DisplayName("El usuario intenta iniciar sesión escribiendo su username en mayúsculas")
    public void elUsuarioIntentaIniciarSesionEscribiendoSuUsernameEnMayusculas() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales(System.getProperty("username").toUpperCase(),System.getProperty("password"))
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();
    }

    @Test @Tag("id-6")
    @DisplayName("El usuario intenta iniciar sesión sin ingresar username")
    public void elUsuarioIniciaSesionSinCompletarUsername() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales("",System.getProperty("password"))
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();
    }

    @Test @Tag("id-7")
    @DisplayName("El usuario intenta iniciar sesión sin ingresar su password")
    public void elUsuarioIniciaSesionSinCompletarPassword() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales(System.getProperty("username"), "")
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();
    }

    @Test @Tag("id-8")
    @DisplayName("El usuario ingresa credenciales con espacios en campo username")
    public void elUsuarioIngresaEspaciosEnCredenciales() {
        new LoginSteps()
                .visualizaElementosDeLogin()
                .ingresarCredenciales(System.getProperty("username")+ "     ", System.getProperty("password"))
                .clickeaEnBotonIngresar()
                .validarMensajeDeError()
                .visualizaElementosDeLogin();
    }
}