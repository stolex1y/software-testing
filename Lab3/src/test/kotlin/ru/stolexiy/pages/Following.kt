package ru.stolexiy.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.Link
import ru.yandex.qatools.htmlelements.element.TypifiedElement

internal class FollowingPage : HtmlElement() {

    @FindBy(xpath = ".//div[@data-testid=\"UserCell\"]")
    private lateinit var followings: List<Following>

    fun getFollowings(): List<Following> {
        return followings
    }

    fun unfollowAll() {
        followings.forEach(Following::pressFollowBtn)
    }
}

class Following : HtmlElement() {
    @FindBy(xpath = ".//a")
    private lateinit var profileLink: Link

    @FindBy(xpath = ".//*[contains(@data-testid, \"follow\")]")
    private lateinit var followBtn: Button

    fun pressFollowBtn() {
        val wasFollowed = followBtn.getAttribute("data-testid").contains("unfollow")
        followBtn.sendKeys(Keys.ENTER)
        if (wasFollowed)
            this.findElement(By.xpath("//*[@data-testid=\"confirmationSheetConfirm\"]")).sendKeys(Keys.ENTER)
    }

    fun getProfileLink() = profileLink.reference
}