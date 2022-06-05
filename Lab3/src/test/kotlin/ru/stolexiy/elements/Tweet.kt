package ru.stolexiy.elements

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.*

internal class Tweet : HtmlElement() {

    @FindBy(xpath = ".//*[@data-testid=\"caret\"]")
    private lateinit var moreBtn: Button

    @FindBy(xpath = ".//a")
    private lateinit var profileLink: Link

    @FindBy(xpath = ".//*[contains(@href, \"status\")]")
    private lateinit var tweetLink: Link

    @FindBy(xpath = ".//*[@data-testid=\"tweetText\"]")
    private lateinit var tweetText: WebElement

    @FindBy(xpath = ".//*[@data-testid=\"reply\"]")
    private lateinit var reply: TweetFooterButton

    @FindBy(xpath = ".//*[contains(@data-testid, \"retweet\")]")
    private lateinit var retweet: TweetFooterButton

    @FindBy(xpath = ".//*[contains(@data-testid, \"like\")]")
    private lateinit var like: TweetFooterButton

    @FindBy(xpath = ".//*[@aria-label=\"Поделиться твитом\"]")
    private lateinit var share: Button

    @FindBy(xpath = "//div[@id=\"layers\"]")
    private lateinit var newTweet: NewTweetDialog

    fun getReplies(): Long {
        return if (reply.text == "")
            0
        else
            reply.text.toLong()
    }

    fun getRetweets(): Long {
        return if (retweet.text == "")
            0
        else
            retweet.text.toLong()
    }

    fun replyToTweet(text: String) {
        reply.sendKeys(Keys.ENTER)
        newTweet.publishTweet(text)
    }

    fun getTweetText() = tweetText.text

    fun deleteTweet() {
        moreBtn.sendKeys(Keys.ENTER)
        findElement(By.xpath("//*[@role=\"menuitem\"]")).sendKeys(Keys.ENTER)
        findElement(By.xpath("//*[@data-testid=\"confirmationSheetConfirm\"]")).sendKeys(Keys.ENTER)
    }

    fun shareTweetByLinkCopie() {
        share.sendKeys(Keys.ENTER)
        findElements(By.xpath("//*[@role=\"menuitem\"]"))[2].sendKeys(Keys.ENTER)
    }

    fun getTweetLink() = tweetLink.reference

    fun retweet() {
        retweet.sendKeys(Keys.ENTER)
        findElement(By.xpath("//*[contains(@data-testid, \"retweetConfirm\")]")).sendKeys(Keys.ENTER)
    }

    fun getProfileLink() = profileLink.reference
}