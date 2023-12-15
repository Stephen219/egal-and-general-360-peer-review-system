package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;





import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

public class FormPageTest {

    private WebDriver driver;

    /**
     * Sets up the ChromeDriver and navigates to the login page.
     */

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(options);
    }
    /**
     * Tests that a user can login and access the dashboard.
     */

    // TODO//: while worKing in this TEST i was in a  branch that is behind  the user wer default user and password wer default password
    // later we change it to Henry and password user

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void testLoginAndAccessDashboard() {
        String pageSource = driver.getPageSource();
        driver.get("https://localhost:8443/login");
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        usernameInput.sendKeys("user");
        passwordInput.sendKeys("user");
        WebElement loginForm = driver.findElement(By.cssSelector("form[action='/login']"));
        loginForm.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/account"));
        assertEquals("https://localhost:8443/account", driver.getCurrentUrl()); // check that the user is redirected to the dashboard
        WebElement toggleButton = driver.findElement(By.xpath("//button[contains(text(), 'Request Form')]")); //requests a form
        toggleButton.click();
        WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        popupWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Yes')]")));
        WebElement yesButton = driver.findElement(By.xpath("//button[contains(text(), 'Yes')]"));
        yesButton.click();
        WebDriverWait flashWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //WebElement flashMessage = flashWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='flashMessage' and contains(text(), 'your form request has been submited!')]")));
        WebElement flashMessage = flashWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='flashMessage']")));
        assertEquals("your form request has been submited! Cancel", flashMessage.getText());
        assertEquals("https://localhost:8443/account", driver.getCurrentUrl());

        assertTrue(driver.getPageSource().contains(" <div id=\"chartContainer\" class=\"bg-white p-4 rounded-md shadow flex mb-4\">"));

        assertTrue(driver.getPageSource().contains("<a href=\"/get_reviewers/form1\">"));
        driver.get("https://localhost:8443/get_reviewers/form1");
        assertTrue(driver.getPageSource().contains("<form id=\"form\" class=\"w-full max-w-sm bg-white flex flex-col py-5 px-8 rounded-lg shadow-lg m-6 \" action=\"/submit_reviewers/form1\" method=\"post\">"));
        driver.get("https://localhost:8443/review/form1");
        driver.get("https://localhost:8443/login");
    }

    /**
     * Closes our driver
     */

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
