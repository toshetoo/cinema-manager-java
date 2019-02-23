package seleniumTest;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class UiTest {
    WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.ultimateqa.com/automation/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testOpenFillForms() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        List<WebElement> forms = driver.findElements(By.className("et_pb_contact_form"));
        Assert.assertEquals("Should have two forms", 2, forms.size());

        Assert.assertEquals("Url should be correct", "https://www.ultimateqa.com/filling-out-forms/", driver.getCurrentUrl());
    }

    @Test
    public void testCursorShouldBeHandOverButtons() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions hover = new Actions(driver);
        WebElement el = driver.findElements(By.className("et_pb_button")).get(1);
        hover.moveToElement(el).build().perform();

        explicitWait(1000);

        Assert.assertEquals("Cursor should be pointer over a button", "pointer", el.getCssValue("cursor"));
    }

    @Test
    public void testSubmitButtonShouldBeWhiteOnHover() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions hover = new Actions(driver);
        WebElement el = driver.findElements(By.className("et_pb_button")).get(1);
        hover.moveToElement(el).build().perform();

        explicitWait(1000);

        Assert.assertEquals("Cursor should be pointer over a button", "rgba(0, 0, 0, 0.05)", el.getCssValue("background-color"));
    }


    @Test
    public void testTrySubmitWithoutData() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement form = driver.findElements(By.tagName("form")).get(2);
        WebElement submitBtn = form.findElement(By.tagName("button"));
        submitBtn.click();

        WebElement nameInput = form.findElement(By.id("et_pb_contact_name_1"));
        Assert.assertTrue("Name input should have error class", nameInput.getAttribute("class").contains("et_contact_error"));

        WebElement msgInput = form.findElement(By.id("et_pb_contact_message_1"));
        Assert.assertTrue("Message input should have error class", msgInput.getAttribute("class").contains("et_contact_error"));

        WebElement captchaInput = form.findElement(By.name("et_pb_contact_captcha_1"));
        Assert.assertTrue("Captcha input should have error class", captchaInput.getAttribute("class").contains("et_contact_error"));
    }

    @Test
    public void testMenuClickShouldShowMenu() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement menu = driver.findElement(By.className("mobile_menu_bar_toggle"));
        menu.click();

        WebElement menuItems = driver.findElement(By.id("mobile_menu"));

        Assert.assertTrue("Menu should be visible when clicking on the toggle", menuItems.isDisplayed());
    }

    @Test
    public void testSubmitFormWithWrongAncCorrectCaptcha() {
        WebElement fillLink = driver.findElement(By.linkText("Fill out forms"));
        fillLink.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement form = driver.findElements(By.tagName("form")).get(2);
        WebElement nameInput = form.findElement(By.id("et_pb_contact_name_1"));
        nameInput.sendKeys("My test name");

        WebElement msgInput = form.findElement(By.id("et_pb_contact_message_1"));
        msgInput.sendKeys("My test message");

        WebElement captchaInput = form.findElement(By.name("et_pb_contact_captcha_1"));
        WebElement questionSpan = form.findElement(By.className("et_pb_contact_captcha_question"));
        String[] questionText = questionSpan.getText().split(Pattern.quote("+"));

        int firstCapctaDigit = Integer.parseInt(questionText[0].trim());
        int secondCaptchaDigit = Integer.parseInt(questionText[1].trim());

        captchaInput.sendKeys("" + firstCapctaDigit + secondCaptchaDigit + 1);

        WebElement submitBtn = form.findElement(By.tagName("button"));
        submitBtn.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement errorMsg = driver.findElement(By.cssSelector(".et-pb-contact-message ul li"));
        Assert.assertTrue("Captcha input should have error class", errorMsg.isDisplayed());

        WebElement questionSpan2 = form.findElement(By.className("et_pb_contact_captcha_question"));
        String[] questionText2 = questionSpan2.getText().split(Pattern.quote("+"));

        int firstCapctaDigit2 = Integer.parseInt(questionText2[0].trim());
        int secondCaptchaDigit2 = Integer.parseInt(questionText2[1].trim());
        captchaInput.clear();
        int res = (firstCapctaDigit2 + secondCaptchaDigit2);
        captchaInput.sendKeys(Integer.toString(res));
        submitBtn.click();

        explicitWait(5000);

        Assert.assertEquals("Form should not be visible", 2, driver.findElements(By.tagName("form")).size());

        WebElement successMessage = driver.findElements(By.className("et-pb-contact-message")).get(1);
        Assert.assertTrue("Success message should be visible", successMessage.isDisplayed());
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(2000);
        driver.close();
    }

    private void explicitWait(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
