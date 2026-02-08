package pages;

import org.openqa.selenium.WebElement;
import static utils.WebElementUtils.getElementByXpath;

public class NavPage {
    private final String logoTms = "//div[@id='border-container']//*[name()='svg']";
    private final String fotoPerfilXpath = "//div[@role='group']//*[contains(@id,'menu-button-')]";
    private final String labelHomeXpath = "//div[@role='group']//p[text()='Home']";
    private final String linkHomeXpath = "//div[@role='group']//a[@href='/app']";
    private final String ordenes = "//div[@role='group']//div//a[@href='/app/orders']";
    private final String logisticaInversa = "//div[@role='group']//div//a[@href='/app/reverse-logistics']";

    private final String tarifas = "//div[@role='group']//div//a[@href='/app/rates']";
    private final String sucursales = "//div[@role='group']//div//a[@href='/app/branches']";



    public WebElement getLogoTms() {return getElementByXpath("Logo TMS", logoTms);}
    public WebElement getFotoPerfil(){return getElementByXpath("Foto perfil", fotoPerfilXpath);}
    public WebElement getLabelHome() {return getElementByXpath("Label home", labelHomeXpath);}
    public WebElement getLinkHome() {return getElementByXpath("Link home", linkHomeXpath);}

    public WebElement getOrdenes() {return getElementByXpath("Órdenes", ordenes);}
    public WebElement getLogisticaInversa() {return getElementByXpath("Logística Inversa", logisticaInversa);}

    public WebElement getTarifas() {return getElementByXpath("Tarifas", tarifas);}
    public WebElement getSucursales() {return getElementByXpath("Sucursales", sucursales);}




}
