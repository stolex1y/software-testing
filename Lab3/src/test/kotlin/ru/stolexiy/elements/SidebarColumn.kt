package ru.stolexiy.elements

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.stolexiy.pages.FollowingPage
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TextInput

@FindBy(xpath = "//div[@data-testid=\"sidebarColumn\"]")
internal class SidebarColumn : HtmlElement() {

    @FindBy(xpath = ".//aside")
    private lateinit var popularUsers: FollowingPage

    @FindBy(xpath = "//*[@data-testid=\"SearchBox_Search_Input\"]")
    lateinit var inputSearchInTwitter: WebElement

    fun getPopularUsers() = popularUsers
}

