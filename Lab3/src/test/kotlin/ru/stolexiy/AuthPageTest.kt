package ru.stolexiy

import AuthPage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.chrome.ChromeDriver
import kotlin.test.assertEquals

private const val authPageUrl = "https://twitter.com/i/flow/login"

internal class AuthPageTest {

    val driver = WebDriverFactory.create()
    val authPage = AuthPage(driver)

    @BeforeEach
    internal fun loadStartPage() {
        driver.get(authPageUrl)

    }

    @Test
    internal fun `test valid login and pass`() {
        authPage.login("filimonov.alks@gmail.com", "3td-mQ5-mKN-iKF")
        assertEquals("Главная / Твиттер", driver.title)
    }

    @AfterEach
    internal fun killWebDriver() {
        driver.quit()
    }
}