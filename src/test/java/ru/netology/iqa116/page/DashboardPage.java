package ru.netology.iqa116.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private final SelenideElement header = $("[data-test-id=dashboard]");

    public DashboardPage() {
        header.shouldBe(Condition.visible);
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.find(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String text = getCard(cardInfo).getText();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);

        if (start < 0 || finish < 0) {
            throw new IllegalStateException("Не удалось найти баланс в тексте: " + text);
        }

        String value = text.substring(start + balanceStart.length(), finish).replaceAll("\\s", "");
        return Integer.parseInt(value);
    }

    public void shouldHaveBalance(DataHelper.CardInfo cardInfo, int expectedBalance) {
        String expectedText = balanceStart + expectedBalance + balanceFinish;
        getCard(cardInfo).shouldHave(Condition.text(expectedText));
    }
}