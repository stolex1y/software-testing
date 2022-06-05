package ru.stolexiy.elements

import org.openqa.selenium.Keys
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TextInput

internal class NewTweetDialog : HtmlElement() {

    @FindBy(xpath = "//div[@data-testid=\"tweetTextarea_0\"]")
    private lateinit var textArea: TextInput

    @FindBy(xpath = "//div[@data-testid=\"tweetButton\"]")
    private lateinit var tweetButton: Button

    fun publishTweet(text: String) {
        textArea.sendKeys(text)
        tweetButton.sendKeys(Keys.ENTER)
    }

    override fun getText(): String {
        return textArea.text
    }
}