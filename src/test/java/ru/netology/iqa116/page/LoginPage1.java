package ru.netology.iqa116.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage1 {

    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public LoginPage1() {
        loginInput.shouldBe(Condition.visible);
    }

    private void fillLoginForm(DataHelper.AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        fillLoginForm(authInfo);
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        fillLoginForm(authInfo);
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
    }
}