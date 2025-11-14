package ru.netology.iqa116.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromCardInput = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public TransferPage() {
        $("[data-test-id=dashboard]").shouldNotBe();
        transferButton.shouldBe();
    }

    public void enterAmount(int amount) {
        amountInput.setValue(String.valueOf(amount));
    }

    public void enterFromCard(String cardNumber) {
        fromCardInput.setValue(cardNumber);
    }

    public DashboardPage clickTransferButton() {
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage transfer(DataHelper.CardInfo from, int amount) {
        enterAmount(amount);
        enterFromCard(from.getNumber());
        return clickTransferButton();
    }

    public void shouldShowError(String expectedText) {
        errorNotification
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedText));
    }
}