package ru.stolexiy.tests

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.support.ui.ExpectedConditions
import ru.stolexiy.pages.AuthPage
import ru.stolexiy.pages.MainPage

internal abstract class AbstractAuthenticatedTest {

    val driver = WebDriverFactory.create()
    val homeUrl = "https://twitter.com/home"
    lateinit var homePage: MainPage
    private val authPage = AuthPage(driver)

    @BeforeEach
    internal fun loadStartPage() {
        driver.get(authPage.url)
        homePage = authPage.login("filimonov.alks@gmail.com", "3td-mQ5-mKN-iKF", "filimonov_alks")
        WebDriverFactory.waitAndAssert(driver, ExpectedConditions.urlContains(homeUrl))
    }

    fun goToHomePage() {
        driver.get(homeUrl)
        WebDriverFactory.waitAndAssert(driver, ExpectedConditions.urlContains(homeUrl))
    }

    @AfterEach
    internal fun killWebDriver() {
        driver.quit()
    }
}