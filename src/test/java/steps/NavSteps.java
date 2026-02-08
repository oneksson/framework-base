package steps;

import io.qameta.allure.Step;
import pages.NavPage;

import static utils.WebElementUtils.waitAndCheckVisibility;
import static utils.WebElementUtils.waitAndClick;

public class NavSteps {

    private final NavPage navPage = new NavPage();

    @Step("Nav SCM: Visualiza elementos de la Home TMS")
    public NavSteps visualizaElementosDeScm() {
        waitAndCheckVisibility("Foto de perfil", navPage.getFotoPerfil());
        waitAndCheckVisibility("Nav Home", navPage.getLabelHome());
        waitAndCheckVisibility("Nav Órdenes", navPage.getOrdenes());
        waitAndCheckVisibility("Nav  Logística Inversa", navPage.getLogisticaInversa());
        waitAndCheckVisibility("Nav  Tarifa", navPage.getTarifas());
        waitAndCheckVisibility("Nav  Sucursales", navPage.getSucursales());
        return this;
    }

    @Step("Nav SCM: Clickea en nav Órdenes")
    public NavSteps clickeaEnNavOrdenes() {
        waitAndClick("Nav Órdenes", navPage.getOrdenes());
        return this;
    }

    @Step("Nav SCM: Clickea en nav Logística Inversa")
    public NavSteps clickeaEnNavLogisticaInversa() {
        waitAndClick("Nav Logística Inversa", navPage.getLogisticaInversa());
        return this;
    }

    @Step("Nav SCM: Clickea en nav Tarifas")
    public NavSteps clickeaEnNavTarifas() {
        waitAndClick("Nav Tarifas", navPage.getTarifas());
        return this;
    }

    @Step("Nav SCM: Clickea en Sub nav Sucursales")
    public NavSteps clickeaEnNavSucursales() {
        waitAndClick("Nav Sucursales", navPage.getSucursales());
        return this;
    }


}
