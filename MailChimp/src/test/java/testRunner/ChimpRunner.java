package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features/chimp.feature", glue={"stepDefinitions"},
tags="@chimps",
monochrome=true,
plugin = {"pretty", "html:target/Reports/reports.html",
		"json:target/Reports/reports.json",
		"junit:target/Reports/reports.xml"}
)
public class ChimpRunner {

}