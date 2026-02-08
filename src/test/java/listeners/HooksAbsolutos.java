package listeners;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static java.lang.System.getProperty;

public class HooksAbsolutos implements TestExecutionListener {
    private final Logger logger = Logger.getLogger("Hooks Logger");

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        logger.info("Ejecutando precondiciones");

        logger.info("Precondiciones ejecutadas");
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        logger.info("Ejecutando postcondiciones");
        setEnviromentAllure();
        logger.info("Postcondiciones ejecutadas");
    }

    private void setEnviromentAllure() {
        Properties properties = new Properties();
        properties.setProperty("Servidor", getProperty("appUrl"));
        try (FileOutputStream fileOutput = new FileOutputStream("build/allure-results/environment.properties")) {
            properties.store(fileOutput, null);
        } catch (IOException e) { throw new RuntimeException(e.getMessage()); }
        logger.info("Enviroment de Allure seteado");
    }
}