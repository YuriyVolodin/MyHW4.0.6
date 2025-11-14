package ru.netology.iqa116.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.iqa116.data.DataHelper;
import ru.netology.iqa116.page.DashboardPage;
import ru.netology.iqa116.page.LoginPage1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        var loginPage = new LoginPage1();
        var verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(DataHelper.getAuthInfo()));
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int firstCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        int transferAmount = secondCardBalanceBefore / 10;

        var transferPage = dashboardPage.selectCard(DataHelper.getFirstCardInfo());

        dashboardPage = transferPage.transfer(DataHelper.getSecondCardInfo(), transferAmount);

        int firstCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        assertEquals(firstCardBalanceBefore + transferAmount, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore - transferAmount, secondCardBalanceAfter);
    }

    @Test
    void shouldNotTransferMoreThanAvailableBalance() {
        int firstCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceBefore = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        int transferAmount = firstCardBalanceBefore + 50_000;

        var transferPage = dashboardPage.selectCard(DataHelper.getSecondCardInfo());
        dashboardPage = transferPage.transfer(DataHelper.getFirstCardInfo(), transferAmount);

        int firstCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int secondCardBalanceAfter = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());

        assertEquals(firstCardBalanceBefore, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore, secondCardBalanceAfter);
    }
}