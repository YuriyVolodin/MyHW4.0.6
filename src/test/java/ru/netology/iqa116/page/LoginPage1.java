package ru.netology.iqa116.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage1 {

    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public LoginPage1() {
        loginInput.shouldBe();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage(); // возвращаем страницу для ввода кода
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
        loginButton.click();
        $("[data-test-id=error-notification]").shouldBe();
    }
}