package ru.stolexiy.elements

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TypifiedElement

class TweetFooterButton : HtmlElement() {
    @FindBy(xpath = ".//*[@data-testid=\"app-text-transition-container\"]")
    private lateinit var btn: WebElement

    override fun getText(): String = btn.text
}