import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MainTest {
    WebDriver driver;
    Wait<WebDriver> wait;
    ChromeOptions options = new ChromeOptions();

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void mainTest() {
        driver.get("https://www.rgs.ru");

        WebElement menu = driver.findElement(By.xpath(
                "//*[@class=\"dropdown adv-analytics-navigation-line1-link current\"]/a"));
        menu.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@class= \"container-rgs-main-menu-links-grid-wrap\"]")));

        WebElement dms = driver.findElement(By.xpath(
                "//*[@class=\"adv-analytics-navigation-line3-link\"]/a[@href=\"" +
                        "https://www.rgs.ru/products/private_person/health/dms/generalinfo/index.wbp\"]"));
        dms.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"content-document-header\"]")));
        WebElement stringCheck1 = driver.findElement(By.xpath("//*[@class=\"content-document-header\"]"));
        Assert.assertTrue("Текст отсутствует", stringCheck1.isDisplayed());

        WebElement reqSend = driver.findElement(By.xpath("//*[@class=" +
                "\"btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button\"]"));
        reqSend.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class = \"modal-content\"]")));

        WebElement stringCheck2 = driver.findElement(By.xpath("//h4[contains(@class, 'modal-title')]//b[text()]"));
        Assert.assertTrue("Текст отсутствует 2", stringCheck2.isDisplayed());

        WebElement lastName = driver.findElement(By.xpath("//*[@name = \"LastName\"]"));
        lastName.click();
        lastName.sendKeys("Петров");
        Assert.assertEquals("Фамилия отсутствует", "Петров", lastName.getAttribute("value"));

        WebElement firstName = driver.findElement(By.xpath("//*[@name = \"FirstName\"]"));
        firstName.click();
        firstName.sendKeys("Борис");
        Assert.assertEquals("Имя отсутствует", "Борис", firstName.getAttribute("value"));

        WebElement middleName = driver.findElement(By.xpath("//*[@name = \"MiddleName\"]"));
        middleName.click();
        middleName.sendKeys("Иванович");
        Assert.assertEquals("Отчество отсутствует", "Иванович", middleName.getAttribute("value"));

        WebElement selectInput = driver.findElement(By.xpath("//select[contains(@data-bind, 'value:Region')]"));
        selectInput.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[contains(@data-bind, 'value:Region')]" +
                "//option[@value='77']")));
        WebElement region = driver.findElement(By.xpath("//select[contains(@data-bind, 'value:Region')]//option[@value='77']"));
        region.click();
        Assert.assertEquals("Регион отсутствует", "77", region.getAttribute("value"));

        WebElement phoneNumber = driver.findElement(By.xpath("//*[contains(@data-bind, \"value: Phone\")]"));
        phoneNumber.click();
        phoneNumber.sendKeys("987 654 32 10");

        Assert.assertEquals("Номер телефона отсутствует", "+7 (987) 654-32-10",
                phoneNumber.getAttribute("value"));

        WebElement email = driver.findElement(By.xpath("//*[contains(@data-bind, \"value: Email\")]"));
        email.click();
        email.sendKeys("qwertyqwerty");
        Assert.assertEquals("Почта отсутствует", "qwertyqwerty", email.getAttribute("value"));

        WebElement contactDate = driver.findElement(By.xpath("//*[contains(@data-bind, \"value: ContactDate\")]"));
        contactDate.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains" +
                "(@class, \"datepicker rgs-datepicker datepicker-dropdown dropdown-menu" +
                " datepicker-orient-left datepicker-orient-top\")]")));
        driver.findElement(By.xpath("//*[contains(@class, \"datepicker-month active\")]")).click();
        driver.findElement(By.xpath("//*[contains(@data-datepicker-timestamp, \"1590537600000\")]")).click();
        Assert.assertEquals("Дата отсутствует", "27.05.2020",
                contactDate.getAttribute("value"));

        WebElement textArea = driver.findElement(By.xpath("//textarea"));
        textArea.click();
        textArea.sendKeys("Коммент");
        Assert.assertEquals("Коммент отсутствует", "Коммент", textArea.getAttribute("value"));

        WebElement checkBox = driver.findElement(By.xpath("//*[@class= \"checkbox\"]"));
        checkBox.click();

        WebElement button = driver.findElement(By.xpath("//*[@id= \"button-m\"]"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"validation-error-text\"]")));
        WebElement validationError = driver.findElement(By.xpath("//*[@class=\"validation-error-text\"]"));
        Assert.assertTrue("Текст валидации почты отсутствует", validationError.isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
