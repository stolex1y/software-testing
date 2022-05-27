package ru.stolexiy

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.concurrent.TimeUnit

object WebDriverFactory {

    fun create(): WebDriver {
        return ChromeDriver().apply {
            manage().window().maximize()
            manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
            manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS)
            manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS)

        }
    }
}