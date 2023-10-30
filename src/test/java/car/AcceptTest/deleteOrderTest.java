package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class deleteOrderTest {
	Order order;
	
	@Given("customer want to delete an order")
	public void customerWantToDeleteAnOrder() {
		order=new Order();
	}

	@When("customer name {string} and customer id {string}")
	public void customerNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(Integer.parseInt(Cid));
	}
	@When("order with product name {string} and product id '{int}'and product price '{int}'")
	public void orderWithProductNameAndProductIdAndProductPrice(String pname, Integer PId, Integer Pprice) {
		int idd=PId;
		int pid=Pprice;
		order.setproductname(pname);
		order.setproductId(idd);
		order.setproductprice(pid);
	}

	@When("order with quantity {string}")
	public void orderWithQuantity(String quantity) {
		order.setproductquntity(Integer.parseInt(quantity));  
	}

	@Then("delete order successfully")
	public void deleteOrderSuccessfully() {
	   int id=order.getOrderId(order);
	   order.deleteOrder(id);
	   assertTrue(Customer.flagdeleteO);
	}
}
