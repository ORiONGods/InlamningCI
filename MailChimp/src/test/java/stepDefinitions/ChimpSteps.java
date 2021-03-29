package stepDefinitions;

import org.openqa.selenium.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ChimpSteps {
	private WebDriver driver;
	
	@Before
	public void openBrowser() throws InterruptedException {
		DriverCreator creator = new DriverCreator();  
		driver = creator.createBrowser("edge");
		driver.navigate().to("https://login.mailchimp.com/signup/");
	}

	@After
	public void closeBrowser() throws InterruptedException {
		driver.close();
		//driver.quit();
	}

}
