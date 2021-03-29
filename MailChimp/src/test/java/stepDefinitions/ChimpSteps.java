package stepDefinitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class ChimpSteps {
	private WebDriver driver;
	
	@Before
	public void openBrowser() throws InterruptedException {
		DriverCreator creator = new DriverCreator();  
		driver = creator.createBrowser("edge");
		driver.navigate().to("https://login.mailchimp.com/signup/");
		//driver.manage().window().maximize();
	}

	@Given("I have entered an email")
	public void i_have_entered_an_email() {
		driver.findElement(By.id("email")).sendKeys("henrik.odfors@smaif.se");
	}

	@And("I have also entered an username")
	public void i_have_also_entered_an_username() {
		driver.findElement(By.cssSelector("input[id=new_username]")).sendKeys("ORiONGods");
	}

	@And("I have also entered a password")
	public void i_have_also_entered_a_password() {
		driver.findElement(By.name("password")).sendKeys("aQ!2345678");
		
	}
	
	@And("I do not want to have any spams")
	public void i_do_not_want_to_have_any_spams() {
		driver.findElement(By.cssSelector("input[name=marketing_newsletter]")).click();
	}

	@When("I press Sign Up")
	public void i_press_sign_up() {
		driver.findElement(By.cssSelector("button[id=create-account]")).submit();
	}

	@Then("the result should be at a verification page")
	public void the_result_should_be_at_a_verification_page() {
		String titleCompare = driver.getTitle();
		assertEquals("Success | Mailchimp", titleCompare);
	}
	
	@After
	public void closeBrowser() throws InterruptedException {
		//driver.close();
		//driver.quit();
	}

}
