package ru.stolexiy.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.yandex.qatools.htmlelements.element.Link

internal class ProfilePage(
    driver: WebDriver
) : MainPage(driver) {

    @FindBy(xpath = ".//a[contains(@href, \"following\")]")
    private lateinit var following: Link

    @FindBy(xpath = "//div[@data-testid=\"primaryColumn\"]")
    private lateinit var followingPage: FollowingPage

    fun getFollowingPage(): FollowingPage {
        following.sendKeys(Keys.ENTER)
        return followingPage
    }


}
