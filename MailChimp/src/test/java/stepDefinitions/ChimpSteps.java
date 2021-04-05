package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.*;
import io.cucumber.java.en.*;

public class ChimpSteps {
	private WebDriver driver;
	By txt_email = By.id("email");
	By txt_user = By.id("new_username");
	By txt_pass = By.name("password");
	By txt_error = By.cssSelector(".invalid-error");
	String allGood = "Success | Mailchimp";
	String hundredLong = "Enter a value less than 100 characters long";
	String userExists = "Another user with this username already exists. Maybe it's your evil twin. Spooky.";
	String empty = "Please enter a value";
	String noAtSign = "An email address must contain a single @";
	String noDomain = "The domain portion of the email address is invalid";
	String noUserPart = "The username portion of the email address is empty";

	@Before
	public void openBrowser() throws InterruptedException {
		DriverCreator creator = new DriverCreator();  
		driver = creator.createBrowser("edge");
		driver.navigate().to("https://login.mailchimp.com/signup/");
		//driver.manage().window().maximize();
	}

	@Given("I have entered an {string}")
	public void i_have_entered_an_email(String theEmail) {
		if(theEmail.equals("valid")) {  // Valid email input
			sendKeys(driver, txt_email, randomUser(6) + "@smaif.se");
		} else if(theEmail.equals("")) {
			sendKeys(driver, txt_email, "");
		} else if(theEmail.equals("badAt")) {
			sendKeys(driver, txt_email, randomUser(6));
		} else if(theEmail.equals("noUser")) {
			sendKeys(driver, txt_email, "@smaif.se");
		} else if(theEmail.equals("domain")) {
			sendKeys(driver, txt_email, randomUser(6) + "@.se");
			noDomain += " (the portion after the @: .se)";
		}
	}

	@And("I have also entered an {string}")
	public void i_have_also_entered_an_username(String theUsername) {
		if(theUsername.equals("valid")) { // Valid username input
			sendKeys(driver, txt_user, randomUser(8));
		} else if(theUsername.equals("longUser")) {
			sendKeys(driver, txt_user, randomUser(100));
		} else if(theUsername.equals("trump")) { // Known dupe
			sendKeys(driver, txt_user, "trump");
		} else if(theUsername.equals("")) { 
			sendKeys(driver, txt_user, "");
		}

	}

	@And("I have also entered a {string}")
	public void i_have_also_entered_a_(String thePassword) {
		sendKeys(driver, txt_pass, "aQ!23456");		
	}

	@And("I say no to spam but yes to cookies")
	public void i_say_no_to_spam_but_yes_to_cookies() throws InterruptedException {
		click(driver, By.cssSelector("input[name=marketing_newsletter]"));
		click(driver, By.cssSelector("#onetrust-accept-btn-handler"));
		//Thread.sleep(1500);
	}

	@When("I press Sign Up")
	public void i_press_sign_up() throws InterruptedException {
		driver.findElement(By.cssSelector("button[id=create-account]")).submit();
		Thread.sleep(1000);
	}

	@Then("I will {string}")
	public void i_will(String verify)  throws InterruptedException {


		if(verify.equals("allGood")) {
			assertEquals(allGood, driver.getTitle());
		} else if(verify.equals("hundredLong")) {
			assertEquals(hundredLong, driver.findElement(txt_error).getText());
		} else if(verify.equals("userExists")) {
			assertEquals(userExists, driver.findElement(txt_error).getText());
		} else if(verify.equals("empty")) {
			if(driver.findElement(txt_email).getText()=="" || driver.findElement(txt_user).getText()=="") {
				assertEquals(empty, driver.findElement(txt_error).getText());
			}
		} else if(verify.equals("noAtSign")) {
			assertEquals(noAtSign, driver.findElement(txt_error).getText());
		} else if(verify.equals("noDomain")) {
			assertEquals(noDomain, driver.findElement(txt_error).getText());
		} else if(verify.equals("noUserPart")) {
			assertEquals(noUserPart, driver.findElement(txt_error).getText());
		}
	}

	@After
	public void closeBrowser() throws InterruptedException {
		//driver.close();
		driver.quit();
	}

	private void click(WebDriver driver, By by) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.
				elementToBeClickable(by));
		driver.findElement(by).click();
	}

	private void sendKeys(WebDriver driver, By by, String keys) {		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(by));
		driver.findElement(by).sendKeys(keys);
	}

	private String randomUser(int howLong) {
		Random r = new Random();
		String userName = "";
		char c = 0;
		int sum = 0;
		for(int i=0;i<howLong;i++) {
			if(i==0) {
				c = (char) (r.nextInt(26) + 'A');
			} else {
				c = (char) (r.nextInt(26) + 'a');
			}
			userName = userName + c;
		}
		for(int i=0;i<userName.length();i++) {
			char ch = userName.charAt(i);
			sum+=(int)ch*(i+1);
		}
		return userName+sum;
	}

}
