package uk.cf.ac.LegalandGeneralTeam11.FormRequestandFormTests;





import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.servlet.http.HttpServletRequest;
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
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

public class FormrequestTestProcess {

    private WebDriver driver;



    /**
     * Sets up the ChromeDriver and navigates to the login page.
     */

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        // make the browser headless to avoid opening a browser window during the test run on the CI server (Jenkins) and the local machine
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
    }
    /**
     * Tests that a user can login and access the dashboard.
     */

    // getting the url port and the prot of the server



    public String buildUrl(HttpServletRequest request) {
        String protocol = request.getScheme();
        String webAddress = request.getServerName();
        int port = request.getServerPort();
        return protocol + "://" + webAddress + ":" + port;
    }





    // TODO//: while worKing in this TEST i was in a  branch that is behind  the user wer default user and password wer default password
    // later we change it to Henry and password user

    @Test
    @WithMockUser(username = "test@gmail.com", password = "user", roles = "USER")
    public void testLoginAndAccessDashboard(

    ) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String url = buildUrl(request);
        String pageSource = driver.getPageSource();
        driver.get(url + "/login");
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        usernameInput.sendKeys("test@gmail.com");
        passwordInput.sendKeys("user");
        WebElement loginForm = driver.findElement(By.cssSelector("form[action='/login']"));
        loginForm.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/account"));
        assertEquals(url + "/account", driver.getCurrentUrl()); // check that the user is redirected to the dashboard
                // check that the user is redirected to the dashboard
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
        assertEquals(url+ "/account", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains(" <div id=\"chartContainer\" class=\"bg-white p-4 rounded-md shadow flex mb-4\">"));

        assertTrue(driver.getPageSource().contains("<a href=\"/get_reviewers/form1\">"));
        driver.get(url + "/get_reviewers/form1");
        assertTrue(driver.getPageSource().contains("<form id=\"form\" class=\"w-full max-w-sm bg-white flex flex-col py-5 px-8 rounded-lg shadow-lg m-6 \" action=\"/submit_reviewers/form1\" method=\"post\">"));
        // the above checks whether the submit reviewers is rendered fully
        driver.get(url + "/review/form1");
        driver.get(url + "/logout");
    }
    @Test
    @WithMockUser(username = "admin@gmail.com", password = "admin", roles = "ADMIN")
    public void testLoginAndAccessDashboardForAdmin(

    ) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String pageSource = driver.getPageSource();
        String url = buildUrl(request);
        driver.get(url + "/login");
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        usernameInput.sendKeys("admin@gmail.com");
        passwordInput.sendKeys("admin");
        WebElement loginForm = driver.findElement(By.cssSelector("form[action='/login']"));
        loginForm.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/admin"));
        assertEquals(url+ "/admin", driver.getCurrentUrl()); // check that the user is redirected to the dashboard
        //System.out.println(driver.getPageSource());
        assertTrue(driver.getPageSource().contains("<a class=\"inline-flex items-center w-full text-sm font-semibold text-white transition-colors duration-150 cursor-pointer hover:text-green-500\" href=\"admin/all_forms\">"));
        assertTrue(driver.getPageSource().contains("<a class=\"inline-flex items-center w-full text-sm font-semibold text-white transition-colors duration-150 cursor-pointer hover:text-green-500\" href=\"/admin/questions\">"));
        driver.get(url + "/all_forms");
        assertTrue(driver.getPageSource().contains("<h1 class=\"text-2xl font-bold text-center md:text-center\">All forms</h1>"));
        assertEquals(url + "/admin/all_forms", driver.getCurrentUrl());
        driver.get(url + "/admin/questions");
        assertEquals(url + "/admin/questions", driver.getCurrentUrl());
        driver.get(url + "/domain");
        assertEquals(url + "/domain", driver.getCurrentUrl());
        driver.get( url +"/logout");
        driver.get(url + "/review/form1");


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
