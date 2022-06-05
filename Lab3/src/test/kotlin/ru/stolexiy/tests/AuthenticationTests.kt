package ru.stolexiy.tests

import ru.stolexiy.pages.AuthPage
import org.junit.jupiter.api.*
import org.openqa.selenium.support.ui.ExpectedConditions
import kotlin.test.assertContains

internal class AuthenticationTests {

    val driver = WebDriverFactory.create()
    val authPage = AuthPage(driver)

    @BeforeEach
    internal fun loadStartPage() {
        driver.get(authPage.url)
    }

    @Test
    internal fun `test invalid pass`() {
        assertThrows<IllegalAccessException> {
            authPage.login("filimonov.alks@gmail.com", "1", "filimonov_alks")
        }
    }

    @Test
    internal fun `test valid login and pass`() {
        authPage.login("filimonov.alks@gmail.com", "3td-mQ5-mKN-iKF", "filimonov_alks")
        WebDriverFactory.waitAndAssert(driver, ExpectedConditions.urlContains("twitter.com/home"))
    }

    @Test
    internal fun `test logout`() {
        val homepage = authPage.login("filimonov.alks@gmail.com", "3td-mQ5-mKN-iKF", "filimonov_alks")
        homepage.logout()
        WebDriverFactory.waitAndAssert(driver, ExpectedConditions.urlContains("twitter.com/?logout"))
    }

    @AfterEach
    internal fun killWebDriver() {
        driver.quit()
    }
}