package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.*;
import io.cucumber.java.en.*;

public class ChimpSteps {
	private WebDriver driver;
	String allgood = "Success | Mailchimp";
	String uptohundred = "Enter a value less than 100 characters long";
	String exists = "Another user with this username already exists. Maybe it's your evil twin. Spooky.";
	String empty = "Please enter a value";
	
	
	
	@Before
	public void openBrowser() throws InterruptedException {
		DriverCreator creator = new DriverCreator();  
		driver = creator.createBrowser("edge");
		driver.navigate().to("https://login.mailchimp.com/signup/");
		//driver.manage().window().maximize();
	}

	@Given("^I have entered an {string}$")
	public void i_have_entered_an_email(String theEmail) {
		if(theEmail.equals("nobody")) {  // Valid input
			sendKeys(driver, By.id("email"), randomUser(6) + "@smaif.se");
		} else if(theEmail.equals("")) {
			sendKeys(driver, By.id("email"), "");
		}
	}

	@And("I have also entered an {string}")
	public void i_have_also_entered_an_username(String theUsername) {
		if(theUsername.equals("ORiONGods")) { // Valid input
			sendKeys(driver, By.id("new_username"), randomUser(8));
		} else if(theUsername.equals("longUser")) {
			sendKeys(driver, By.id("new_username"), randomUser(101));
		} else if(theUsername.equals("trump")) {
			sendKeys(driver, By.id("new_username"), "trump");
		}
		
		//driver.findElement(By.cssSelector("input[id=new_username]")).sendKeys("ORiONGods");
	}

	@And("I have also entered a password")
	public void i_have_also_entered_a_password() {
		sendKeys(driver, By.name("password"), "aQ!23456");		
	}
	
	@And("I do not want to have any spams")
	public void i_do_not_want_to_have_any_spams() {
		click(driver, By.cssSelector("input[name=marketing_newsletter]"));
	}

	@When("I press Sign Up")
	public void i_press_sign_up() {
		driver.findElement(By.cssSelector("button[id=create-account]")).submit();
	}
	
	@Then("I will {string}")
	public void i_will(String verify)  throws InterruptedException {
		
		
		if(verify.equals(allgood)) {
			assertEquals(verify, driver.getTitle());
		} else if(verify.equals(uptohundred)) {
			assertEquals(verify, driver.findElement(By.cssSelector(".invalid-error")).getText());
		} else if(verify.equals(exists)) {
			assertEquals(verify, driver.findElement(By.cssSelector(".invalid-error")).getText());
		} else if(verify.equals(empty)) {
			assertEquals(verify, driver.findElement(By.cssSelector("span[class=invalid-error]")).getText());
		}
	}
	
	@After
	public void closeBrowser() throws InterruptedException {
		//driver.close();
		//driver.quit();
	}
	
	private void click(WebDriver driver, By by) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.
				elementToBeClickable(by));
		driver.findElement(by).click();
	}
	
	private void sendKeys(WebDriver driver, By by, String keys) {		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(by));
		driver.findElement(by).sendKeys(keys);
		//driver.findElement(by).sendKeys(Keys.ENTER);
		//System.out.println("Sending keys");
	}

}
