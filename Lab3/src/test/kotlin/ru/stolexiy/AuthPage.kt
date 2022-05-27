import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.TextInput
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

internal class AuthPage(
	private val webDriver: WebDriver
) {
	init {
	    PageFactory.initElements(HtmlElementDecorator(HtmlElementLocatorFactory(webDriver)), this)
	}

	@Name("Input email")
	@FindBy(xpath = "//input[@name=\"text\"]")
	lateinit var inputEmail: TextInput

	@Name("Input pass")
	@FindBy(xpath = "//input[@name=\"password\"]")
	lateinit var inputPass: TextInput

	@Name("Login submit")
	@FindBy(xpath = "//div[@data-testid=\"LoginForm_Login_Button\"]")
	lateinit var btn: Button
	

	fun login(email: String, pass: String) {
		inputEmail.sendKeys(email)
		Actions(webDriver).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform()
		inputPass.sendKeys(pass)
		btn.submit()
	}
	

}