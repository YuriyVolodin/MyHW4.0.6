package ru.netology.iqa116.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeInput = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeInput.shouldBe();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        codeInput.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode code) {
        codeInput.setValue(code.getCode());
        verifyButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public String getErrorText() {
        return errorNotification.getText();
    }
}