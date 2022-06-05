package ru.stolexiy.elements

import org.openqa.selenium.Keys
import org.openqa.selenium.support.FindBy
import ru.stolexiy.pages.MainPage
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.Link

internal class Header(
) : HtmlElement() {

    @FindBy(xpath = ".//a[@data-testid=\"AppTabBar_Home_Link\"]")
    private lateinit var homeLink: Link

    @FindBy(xpath = ".//a[@data-testid=\"AppTabBar_Explore_Link\"]")
    private lateinit var exploreLink: Link

    @FindBy(xpath = ".//*[contains(@href, \"bookmarks\")]")
    private lateinit var bookmarksLink: Link

    @FindBy(xpath = ".//a[@data-testid=\"AppTabBar_DirectMessage_Link\"]")
    private lateinit var messagesLink: Link

    @FindBy(xpath = ".//a[@data-testid=\"AppTabBar_Profile_Link\"]")
    private lateinit var profileLink: Link

    @FindBy(xpath = ".//*[@data-testid=\"SideNav_NewTweet_Button\"]")
    private lateinit var newTweetBtn: Link

    @FindBy(xpath = ".//*[@data-testid=\"SideNav_AccountSwitcher_Button\"]")
    private lateinit var profileBtn: Button

    @FindBy(xpath = "//*[@data-testid=\"AccountSwitcher_Logout_Button\"]")
    private lateinit var logoutBtn: Button

    @FindBy(xpath = "//div[@id=\"layers\"]")
    private lateinit var newTweet: NewTweetDialog

    fun getHomePage() {
        homeLink.sendKeys(Keys.ENTER)
    }

    fun getExplorePage() {
        exploreLink.sendKeys(Keys.ENTER)
    }

    fun getBookmarksPage() {
        bookmarksLink.sendKeys(Keys.ENTER)
    }

    fun getMessagesPage() {
        messagesLink.sendKeys(Keys.ENTER)
    }

    fun getProfilePage() {
        profileLink.sendKeys(Keys.ENTER)
    }

    fun logout() {
        profileBtn.sendKeys(Keys.ENTER)
        logoutBtn.sendKeys(Keys.ENTER)
    }

    fun publishTweet(text: String) {
        newTweetBtn.sendKeys(Keys.ENTER)
        newTweet.publishTweet(text)
    }

    fun newTweet(): NewTweetDialog {
        newTweetBtn.sendKeys(Keys.ENTER)
        return newTweet
    }
}