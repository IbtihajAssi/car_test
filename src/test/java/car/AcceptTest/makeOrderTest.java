package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class makeOrderTest {

	Order order;
	 int Orderid;
	 int quantity;
	@Given("customer want to make an order")
	public void customerWantToMakeAnOrder() {
		order=new Order(); 
	}
	@When("customer with name {string} and customer id {string}")
	public void customerWithNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(Integer.parseInt(Cid));
	}
	@When("make order with product name {string} and product id '{int}'and product price '{int}'")
	public void makeOrderWithProductNameAndProductIdAndProductPrice(String Pname, Integer Pid, Integer Pprice) {
		order.setproductname(Pname);
		order.setproductId(Pid);
		order.setproductprice(Pprice);
	}
	@When("make order with quantity {string}")
	public void makeOrderWithQuantity(String string) {
	   order.setproductquntity(Integer.parseInt(string));
	}
	@Then("make order successfully")
	public void makeOrderSuccessfully() {
		order.insertOrder(order);
		assertTrue(Customer.finsertorder);
	}
	
	@When("set order with quantity {string}")
	public void setOrderWithQuantity(String string) {
		quantity=Integer.parseInt(string);
	    order.setproductquntity(2);
	    
	}
	@Then("update order successfully")
	public void updateOrderSuccessfully() {
		Orderid=order.getOrderId(order) ;
		assertTrue(	order.updateOrder(Orderid, quantity));
	}

}
