package ru.netology.iqa116.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.iqa116.data.DataHelper;
import ru.netology.iqa116.page.DashboardPage;
import ru.netology.iqa116.page.LoginPage1;
import ru.netology.iqa116.page.TransferPage;
import ru.netology.iqa116.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        LoginPage1 loginPage = new LoginPage1();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(DataHelper.getAuthInfo()));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        int transferAmount = 5000;

        int firstCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        TransferPage transferPage = dashboardPage.selectCard(DataHelper.getSecondCardInfo());
        dashboardPage = transferPage.transfer(DataHelper.getFirstCardInfo(), transferAmount);

        int firstCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        if (firstCardBalanceAfter != firstCardBalanceBefore - transferAmount ||
                secondCardBalanceAfter != secondCardBalanceBefore + transferAmount) {
            throw new AssertionError("Баланс после перевода неправильный! Сборка падает.");
        }
    }

    @Test
    void shouldNotTransferMoreThanAvailableBalance() {
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int transferAmount = firstCardBalance + 10000;

        TransferPage transferPage = dashboardPage.selectCard(DataHelper.getSecondCardInfo());
        transferPage.enterAmount(transferAmount);
        transferPage.enterFromCard(DataHelper.getFirstCardInfo().getNumber());
        transferPage.clickTransferButton();

        if (!transferPage.getErrorNotification().isDisplayed()) {
            throw new AssertionError("Отсутствует вывод ошибки при переводе суммы большей чем баланс карты");
        }
    }
}
