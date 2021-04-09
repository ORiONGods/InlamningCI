package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
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
	String someEmpty = "Please enter a value";
	String noAtSign = "An email address must contain a single @";
	String noDomain = "The domain portion of the email address is invalid";
	String noUserPart = "The username portion of the email address is empty";

	@Before
	public void openBrowser() throws InterruptedException {
		DriverCreator creator = new DriverCreator();  
		driver = creator.createBrowser("chrome");    //valids: chrome, firefox, edge, opera, safari
		driver.navigate().to("https://login.mailchimp.com/signup/");
		//driver.manage().window().maximize();
	}

	@Given("I have entered an {string}")
	public void i_have_entered_an_email(String theEmail) {
		if(theEmail.equals("valid")) {   // Valid email input
			sendKeys(driver, txt_email, randomUser(6) + "@smaif.se");
		} else if(theEmail.equals("")) {
			sendKeys(driver, txt_email, "");
		} else if(theEmail.equals("badAt")) {
			sendKeys(driver, txt_email, randomUser(6));
		} else if(theEmail.equals("noUser")) {
			sendKeys(driver, txt_email, "@smaif.se");
		} else if(theEmail.equals("domain")) {
			sendKeys(driver, txt_email, randomUser(6) + "@.se");
			noDomain += " (the portion after the @: .se)";   //how to use RegEx here?!
		}
	}

	@And("I have also entered an {string}")
	public void i_have_also_entered_an_username(String theUsername) {
		if(theUsername.equals("valid")) {   // Valid username input
			sendKeys(driver, txt_user, randomUser(8));
		} else if(theUsername.equals("longUser")) {
			sendKeys(driver, txt_user, randomUser(100));
		} else if(theUsername.equals("trump")) {  // Known dupe
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
	}


	@When("I press Sign Up")
	public void i_press_sign_up() throws InterruptedException {
		submitBtn(driver, By.cssSelector("button[id=create-account]"));
	}

	@Then("I will check for {string}")
	public void i_will_check_for(String verify)  throws InterruptedException {
		
		String expected = "";
		String actual = "";

		if(verify.equals("allGood")) {
			titleWait(driver, allGood);
			expected = allGood;
			actual = driver.getTitle();	
		}
		else {
			if(verify.equals("hundredLong")) {
				someWait(driver, txt_error);
				expected = hundredLong;
			} else if(verify.equals("userExists")) {
				someWait(driver, txt_error);
				expected = userExists;
			}  else if(verify.equals("noAtSign")) {
				someWait(driver, txt_error);
				expected = noAtSign;
			} else if(verify.equals("noDomain")) {
				someWait(driver, txt_error);
				expected = noDomain;
			} else if(verify.equals("noUserPart")) {
				someWait(driver, txt_error);
				expected = noUserPart;
			} else if(verify.equals("someEmpty")) {
				if(driver.findElement(txt_email).getText()=="" || driver.findElement(txt_user).getText()=="") {
					someWait(driver, txt_error);
					expected = someEmpty;
				}
			}
			actual = driver.findElement(txt_error).getText();
		}
		assertEquals(expected, actual);
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
	
	private void submitBtn(WebDriver driver, By by) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.
				elementToBeClickable(by));
		driver.findElement(by).submit();
	}

	private void sendKeys(WebDriver driver, By by, String keys) {		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(by));
		driver.findElement(by).sendKeys(keys);
	}
	
	private void someWait(WebDriver driver, By by) {		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	private void titleWait(WebDriver driver, String title) {		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.titleContains(allGood));
	}
	

	private String randomUser(int howLong) {
		Random r = new Random();
		String userName = "";
		char c = 0;
		int sum = 0;
		for(int i=0;i<howLong;i++) {   //Create a xx chars long Username
			if(i==0) {
				c = (char) (r.nextInt(26) + 'A');   //First char is UPPERCASE
			} else {
				c = (char) (r.nextInt(26) + 'a');
			}
			userName = userName + c;
		}
		for(int i=0;i<userName.length();i++) {    //Then calculate a checksum. Char*index
			char ch = userName.charAt(i);
			sum+=(int)ch*(i+1);
		}
		return userName+sum;
	}

}
