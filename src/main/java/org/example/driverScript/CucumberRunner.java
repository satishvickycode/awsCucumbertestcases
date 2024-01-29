package org.example.driverScript;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags= "@tag1",features = "/var/task/Features/login.feature",glue={"org.example.StepDefinations"},
plugin = {"json:/var/task/target/cucumber.json", "html:/var/task/target/cucumber.html"})
public class CucumberRunner extends AbstractTestNGCucumberTests{
}

