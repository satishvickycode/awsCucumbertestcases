package org.example.StepDefinations;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.example.Pages.LoginPage;
import org.example.util.MyScreenRecorder;


public class LoginSteps {

	@Before
	public void start() throws Exception {
		System.out.println(" ======================>Before method started");
		//MyScreenRecorder.startRecording("adpTest");
	}

	LoginPage lp = new LoginPage();

	@Given("User navigate to ADP url")
	public void user_navigate_to_adp_url() {
		System.out.println(" ======================>Before browserlaunch ");
		lp.launchbrowser();
	}

	@Given("user enters the following details")
	public void user_enters_the_following_details(DataTable dataTable) {
		LoginPage.enterDetails(dataTable);
	}

	@After
	public void end() throws Exception {
		System.out.println(" ====================After ");
		//MyScreenRecorder.stopRecording();
	}

}
