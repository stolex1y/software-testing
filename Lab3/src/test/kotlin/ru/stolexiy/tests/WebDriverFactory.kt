package ru.stolexiy.tests

import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

object WebDriverFactory {

    fun create(): WebDriver {
        return ChromeDriver().apply {
            manage().window().maximize()
            manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
//            manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS)
//            manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS)

        }
    }

    fun wait(
        driver: WebDriver, timeOutInSeconds: Long,
        condition: ExpectedCondition<WebElement>, ifTimeOut: () -> Unit = {},
        ifExpected: () -> Unit = {}) {
        try {
            WebDriverWait(driver, timeOutInSeconds).until(condition)
            ifExpected()
        } catch (e: TimeoutException) {
            ifTimeOut()
        }
    }

    fun <T> waitAndAssert(
        driver: WebDriver,
        condition: ExpectedCondition<T>
    ) {
        try {
            WebDriverWait(driver, 1).until(condition)
            assertTrue(true)
        } catch (e: TimeoutException) {
            assertTrue(false)
        }
    }
}