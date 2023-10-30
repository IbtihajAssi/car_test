package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
public class Customer {
	public  Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scanner=new Scanner(System.in);
	public  String scann;
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	public static boolean isavaliable=false;
	public static boolean finsertorder=false;
	public static boolean flag_search=false;
public static boolean flagdeleteO=false;
public Product product=new Product();
public Order order=new Order();

	public Customer() {

	}
	public void customerDashboard(String user) {
		int x=0;
		while(x!=1) {
			logger.info("Welcome, CUSTOMER!");
			logger.info("Please choose you want need.");
			logger.info("1.View category.");
			logger.info("2.View product.");
			logger.info("3.Make Installation request.");
			logger.info("4.Search.");
			logger.info("5.View Shopping cart");
			logger.info("6.Edit your profile");
			logger.info("7.Log OUT");

			String input=scanner.nextLine();
			if(input.equalsIgnoreCase("1")) {// View category
				product.viewCategories();
			}

			else if(input.equalsIgnoreCase("2")){//View Product
				logger.info("Enter name of category");
	              String category=scanner.nextLine();
				viewCategoryProduct(category);
				viewBuy(user);
			}
			
			else if(input.equalsIgnoreCase("3")){//to Make Installation request

			}
			else if(input.equalsIgnoreCase("4")){//
				search(user);
			}
			else if(input.equalsIgnoreCase("5")){// to View Order
				viewOrder(user);
				shoppingCart(user);
			}
			else if(input.equalsIgnoreCase("6")){// Edit profile
		     editCustomerProfile(user);
			}
			else if(input.equalsIgnoreCase("7")){
				System.out.println("Logging out...");

				x=1;
			} 
			else {
				logger.info("Invalid choice. Please enter 1, 2, 3,4,5,6 or 7.");

			}

		}

	}
	

	public void viewCategoryProduct(String category) {

		try {
			logger.info("please choose the number of product you want.");
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			String vailability=null;
			while (rs.next()) {
				String id="id = "+rs.getInt("id");
				String name= rs.getString("name");
				String description= rs.getString("description");
				int price= rs.getInt("price");

				if(rs.getInt("quantity")>0) {
					vailability="avaliable";
				}
				else {
					vailability="not avaliable";
				}

				logger.info(id+"  "+name+" "+description+" "+price+"$ "+vailability);

			}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void viewBuy(String customername){
		logger.info("1.add product to cart");
		logger.info("2.Back to  products");
		scann=scanner.nextLine();
		if(scann.equalsIgnoreCase("1")) {//to buy product
			logger.info("please enter id product");
			scann=scanner.nextLine();
			int productId=Integer.parseInt(scann);
			logger.info("enter quntity");
			scann=scanner.nextLine();//prodect quntity
			int quantity=Integer.parseInt(scann);

			productAvailable(quantity,productId);//add to order table 
			if(isavaliable) {
				int customerId=getCustomerId(customername);
				String productname=product.getProductName(productId);
				int price=product.getProductPrice(productId);
				Order orderr=new Order(customername,customerId, productId,  productname,quantity,price);
				order.insertOrder(orderr);
				if(finsertorder) {
					logger.info("insert order succsessfully");
					int p_quantity=product.getProductQuantity(productId);
					p_quantity-=quantity;
					product.updateProductQuantity(productId,p_quantity);
				}
				else {
					logger.info("insert order unsuccsessfully");	
				}
			}
			else {
				logger.info("This product is not avaliable or quantity avaliable not enough");	

			}

		}

		//		else if(scann.equalsIgnoreCase("2")){//to Back to category product
		//			viewProduct(id);//view description the Product
		//			}

	}
	public void search(String user) {
		logger.info("1.Search by name.");
		logger.info("2.Search by price.");
		logger.info("3.Search by category.");
		scann=scanner.nextLine();
		if(scann.equalsIgnoreCase("1")) {
			logger.info("enter name");
			scann=scanner.nextLine();
			product.searchByName(scann);

		}
		else if(scann.equalsIgnoreCase("2")) {
			logger.info("enter price");
			scann=scanner.nextLine();
			int price=Integer.parseInt(scann);
			product.searchByPrice(price);
		}
		else if(scann.equalsIgnoreCase("3")) {
			logger.info("enter category");
			scann=scanner.nextLine();

			product.searchByCategory(scann);
		}
		if(!flag_search) {
			logger.info("no product to display");
		}
		else {
			viewBuy(user);
		}
	}
	public void viewOrder(String customername) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from orders where customername='" +customername+"' and Buy=false";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			while (rs.next()) {
				logger.info(rs.getInt("id")+" "+rs.getString("productname")+" "+rs.getInt("productquantity")+" "+rs.getInt("productprice")+"$");//print order
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void shoppingCart(String user) {
		logger.info("choose betwen choices");
		logger.info("1.Update Order");
		logger.info("2.Delete Order");
		logger.info("3.Confirm Order");
		scann=scanner.nextLine();

		if(scann.equalsIgnoreCase("1")) {
			logger.info("to update quintity the product in your order please enter id order ");
			scann=scanner.nextLine();
			int orderid=Integer.parseInt(scann);

			logger.info(" please enter new quantity ");
			scann=scanner.nextLine();
			int Quantity=Integer.parseInt(scann);

			if(order.updateOrder(orderid,Quantity)) {
				logger.info("update order succsessfuly");
			}
			else {
				logger.info("update order unsuccsessfuly you enter incorrect id ");
			}
		}

		else if(scann.equalsIgnoreCase("2")){
			logger.info("to delete order please enter id order");
			scann=scanner.nextLine();
			int idd=Integer.parseInt(scann);
			order.deleteOrder(idd);
			if(flagdeleteO) {
				logger.info("Delete Order successfuly");	
			}
			else {
				logger.info("Delete Order unsuccessfuly, incorrect order Id");	
			}
		}
		else if(scann.equalsIgnoreCase("3")){
			confirmOrder(user);
		}

	}
	public int getCustomerId(String customeremail) {
		int id=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from users where email='" +customeremail+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				id=rs.getInt("id");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public void productAvailable(int quantity,int idproduct) { 

		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +idproduct+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			while (rs.next()) {
				int price=rs.getInt("price"); 
				if(rs.getInt("quantity")>0 && rs.getInt("quantity")>=quantity) {
					//product is avaliable
					isavaliable=true;
				}
				else {
					isavaliable=false;
				}
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void insertConfirmOrder(String customername,String city,String street,String phoneNumber) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Update orders set Buy=?,city=?,street=?,phoneNumber=? where customername='"+customername+"'";
			stm=con.prepareStatement(sql);

			stm.setBoolean(1,true);
			stm.setString(2, city);
			stm.setString(3,street);
			stm.setString(4, phoneNumber);

			stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void confirmOrder(String customername) {
		logger.info("Enter your address");
		logger.info("Enter your city");
		scann=scanner.nextLine();
		String city=scann;

		logger.info("Enter your steet");
		scann=scanner.nextLine();
		String street=scann;

		logger.info("Enter your phone number");
		scann=scanner.nextLine();
		String phoneNumber=scann;
		int count=0;
		for(int i=0;i<phoneNumber.length();i++) {
			if(Character.isDigit(phoneNumber.charAt(i))) {
				count++;
			}
		}
		if(count==phoneNumber.length()) {
			//all phoneNumber is digit
			insertConfirmOrder(customername,city,street,phoneNumber);
		}
		else {
			logger.info("should all phoneNumber is digit");
		}
	}
	public boolean editName(String user,String ename){
		boolean flagN=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Update users set name=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, ename);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagN=true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flagN;
	}
	public boolean editEmail(String user,String eemail){
		 boolean flagE=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Update users set email=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, eemail);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagE=true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flagE;
	}
	public boolean editPassword(String user,String epassword){
		boolean flagP=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Update users set password=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, epassword);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagP=true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flagP;
	}
	public String getCustomerPassword(String user) {
		String oldpass=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from users where email='" +user+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				oldpass=rs.getString("password");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return oldpass;
	}

	public void editCustomerProfile(String user){
		logger.info("1.edit your name");
		logger.info("2.edit your email");
		logger.info("3.edit your password");
		String edit=scanner.nextLine();
		if(edit.equalsIgnoreCase("1")) {
			logger.info("enter your new name");
			String ename=scanner.nextLine();
			editName(user,ename);
		}
		else if(edit.equalsIgnoreCase("2")) {
			logger.info("enter your new email");
			String eemail=scanner.nextLine();
			if(eemail.contains("@")||eemail.contains(".")) {
				editEmail(user,eemail);
			}
		}
		else if(edit.equalsIgnoreCase("3")) {
			logger.info("enter your old password");
			String oldPass=scanner.nextLine();
			logger.info("enter your new password");
			String epassword=scanner.nextLine();
			String oldpassword=getCustomerPassword(user);
			if(oldPass.equals(oldpassword)){
				editPassword(user,epassword);
			}
			else {
				logger.info("enter wronge old password");
			}
		}
	}

}