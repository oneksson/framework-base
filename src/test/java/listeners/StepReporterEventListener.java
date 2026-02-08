package listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.WebDriverUtils;

import java.util.logging.Logger;

public class StepReporterEventListener implements StepLifecycleListener {
    private final Logger LOGGER = Logger.getLogger("Steps");

    @Override
    public void beforeStepStart(StepResult step) {
        LOGGER.info(step.getName());
    }

    @Override
    public void afterStepUpdate(StepResult step) {
        if (!step.getName().contains("servicio")) this.takeScreenShot();
    }

    @Attachment("Screenshot")
    public byte[] takeScreenShot() {
        return ((TakesScreenshot) WebDriverUtils.driver).getScreenshotAs(OutputType.BYTES);
    }
}