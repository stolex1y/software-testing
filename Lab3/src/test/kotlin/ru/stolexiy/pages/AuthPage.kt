package ru.stolexiy.pages

import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import ru.stolexiy.tests.WebDriverFactory
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.TextInput
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

internal class AuthPage(
	private val webDriver: WebDriver
) {
	val url = "https://twitter.com/i/flow/login"
	init {
	    PageFactory.initElements(HtmlElementDecorator(HtmlElementLocatorFactory(webDriver)), this)
	}

	@Name("Input email")
	@FindBy(xpath = "//input[@name=\"text\"]")
	lateinit var inputEmail: TextInput

	@Name("Input pass")
	@FindBy(xpath = "//input[@name=\"password\"]")
	lateinit var inputPass: TextInput

	@Name("Input username")
	@FindBy(xpath = "//input[@data-testid=\"ocfEnterTextTextInput\"]")
	lateinit var inputUsername: TextInput

	@Name("Error toast")
	@FindBy(xpath = "//div[@data-testid=\"toast\"]//span")
	lateinit var errorToast: WebElement

	fun login(email: String, pass: String, username: String): MainPage {
		inputEmail.sendKeys(email + Keys.ENTER)
		WebDriverFactory.wait(
			webDriver, 1,
			ExpectedConditions.visibilityOf(inputPass),
			ifTimeOut = {
				inputUsername.sendKeys(username + Keys.ENTER)
			}
		)
		inputPass.sendKeys(pass + Keys.ENTER)
		WebDriverFactory.wait(
			webDriver, 1,
			ExpectedConditions.visibilityOf(errorToast),
			ifExpected = { if (errorToast.isDisplayed) throw IllegalAccessException("Неверный пароль") }
		)
		return MainPage(webDriver)
	}
	

}