package stepDefinitions;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.opera.*;
import org.openqa.selenium.edge.*;

public class DriverCreator {
		
		public WebDriver createBrowser(String browser) {
			WebDriver driver = null;
			String projectPath = System.getProperty("user.dir");

			if(browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else if(browser.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", projectPath+"/src/test/resources/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if(browser.equals("edge")) {
				System.setProperty("webdriver.edge.driver", projectPath+"/src/test/resources/drivers/msedgedriver.exe");
				driver = new EdgeDriver();
			} else if(browser.equals("opera")) {
				System.setProperty("webdriver.opera.driver", projectPath+"/src/test/resources/drivers/operadriver.exe");
				driver = new OperaDriver();
			}
			
			return driver;
			
		}

}

