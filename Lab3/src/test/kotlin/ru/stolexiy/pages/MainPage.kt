package ru.stolexiy.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import ru.stolexiy.elements.*
import ru.stolexiy.elements.Header
import ru.stolexiy.elements.PrimaryColumn
import ru.stolexiy.elements.SidebarColumn
import ru.stolexiy.elements.Tweet
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

internal open class MainPage(
    val driver: WebDriver
) {
    @FindBy(xpath = "//header")
    private lateinit var header: Header

    private lateinit var primaryColumn: PrimaryColumn

    private lateinit var sidebarColumn: SidebarColumn

    init {
        PageFactory.initElements(HtmlElementDecorator(HtmlElementLocatorFactory(driver)), this)
    }

    fun logout(): AuthPage {
        header.logout()
        driver.findElement(By.xpath("//*[@data-testid=\"confirmationSheetConfirm\"]")).sendKeys(Keys.ENTER)
        return AuthPage(driver)
    }

    fun getTweets(): List<Tweet> = primaryColumn.getTweets()

    fun getBookmarksPage(): MainPage {
        header.getBookmarksPage()
        return MainPage(driver)
    }

    fun getMessagesPage() {
        header.getMessagesPage()

    }

    fun getProfilePage(): ProfilePage {
        header.getProfilePage()
        return ProfilePage(driver)
    }

    fun publishTweet(text: String) {
        header.publishTweet(text)
    }

    fun getPopularUsers() = sidebarColumn.getPopularUsers()

    fun getSidebarColumn() = sidebarColumn

    fun newTweet() = header.newTweet()

}