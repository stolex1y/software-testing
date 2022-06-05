package ru.stolexiy.elements

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

@FindBy(xpath = "//div[@data-testid=\"primaryColumn\"]")
internal class PrimaryColumn : HtmlElement() {

    @FindBy(xpath = ".//article[@data-testid=\"tweet\"]")
    private lateinit var tweets: List<Tweet>

    fun getTweets() = tweets
}