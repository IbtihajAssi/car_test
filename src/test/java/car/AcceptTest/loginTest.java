package car.AcceptTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import car.Login;

public class loginTest {

	Login obj;
	
@Given("user is on the login page")
public void userIsOnTheLoginPage() {
	obj=new Login();
}



@When("user with usertype\"{int}\" set email with {string} and user set password with {string}")
public void userWithUsertypeSetEmailWithAndUserSetPasswordWith(Integer usertype, String email, String password) {
	 obj.checkEmail(email,usertype.toString());
	 obj.checkpassword(email,password,usertype.toString());
	

}

@Then("the user should go to his panel")
public void theUserShouldGoToHisPanel() {
	 boolean flag;
	    if(obj.flagemail==true && obj.flagpass==true) {
	    	flag=true;
	    }
	    
	    else flag=false;
	    assertTrue(flag);
		 
}

@Then("the user should go back to login")
public void theUserShouldGoBackToLogin() {
	boolean flag;
    if(obj.flagemail==true && obj.flagpass==false) flag=true;
    else flag=false;
    assertTrue(flag);
}


@Then("the user should recieve invalid user email message and go back to login")
public void theUserShouldRecieveInvalidUserEmailMessageAndGoBackToLogin() {
	boolean flag;
  if(obj.flagemail==false) flag=true;
  else flag=false;
  assertTrue(flag);
}

}
