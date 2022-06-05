package ru.stolexiy.tests

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import kotlin.test.assertEquals

internal class TweetTests : AbstractAuthenticatedTest() {

    val testTweetText = System.currentTimeMillis().toString()

    @Test
    fun `comment tweet test`() {
        assertThat(homePage.getTweets(), Matchers.hasSize(Matchers.not(0)))
        homePage.getTweets()[0].replyToTweet(testTweetText)
        WebDriverFactory.waitAndAssert(driver) {
            homePage.getTweets()[0].getTweetText().equals(testTweetText)
        }
        homePage.getTweets()[0].deleteTweet()
    }

    @Test
    fun `publish tweet test`() {
        homePage.publishTweet(testTweetText)
        WebDriverFactory.waitAndAssert(driver) {
            homePage.getTweets()[0].getTweetText().equals(testTweetText)
        }
    }

    @Test
    fun `delete tweet test`() {
        `publish tweet test`()
        val tweet = homePage.getTweets()[0]
        val tweetLink = tweet.getTweetLink()
        tweet.deleteTweet()
        driver.get(tweetLink)
        val tweetCount = homePage.getTweets().size
        assertEquals(0, tweetCount)
    }

    @Test
    fun `retweet other thweet test`() {
        val tweet = homePage.getTweets()[0]
        val tweetText = tweet.getTweetText()
        val oldRetweetsCount = tweet.getRetweets()
        tweet.retweet()
        val profilePage = homePage.getProfilePage()
        WebDriverFactory.waitAndAssert(driver) {
            val retweeted = profilePage.getTweets()[0]
            retweeted.getTweetText() == tweetText &&
                    retweeted.getRetweets() == (oldRetweetsCount + 1)
        }
        profilePage.getTweets()[0].retweet()
    }

    @Test
    fun `follow to user and observe tweets test`() {
        homePage.getProfilePage().getFollowingPage().unfollowAll()
        goToHomePage()
        val following = homePage.getPopularUsers().getFollowings()[0]
        val followingLink = following.getProfileLink()
        following.pressFollowBtn()
        goToHomePage()
        driver.get(homePage.getTweets()[0].getProfileLink())
        WebDriverFactory.waitAndAssert(driver) {
            driver.currentUrl.equals(followingLink)
        }
    }

    @Test
    fun `share a tweet`() {
        val tweet = homePage.getTweets()[0]
        tweet.shareTweetByLinkCopie()
        WebDriverFactory.waitAndAssert(
            driver,
            ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@data-testid=\"toast\"]//span"))),
        )
    }
}