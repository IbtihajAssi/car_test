package car;
import java.util.*;
import java.util.logging.Logger;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin {
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	public  static Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scann=new Scanner(System.in);
	public   String scan;
	public  Boolean checkprod=false;
	public static Boolean flaginsertP=false;
	public  static Boolean flagdeleteP=false;
	public Boolean flaginsertC=false;
	public Boolean flagdeleteC=false;
	public Product product=new Product();
       public Admin() {
    	   
       }
       
       
       public void adminDashboard() {
    	   int x=0;
    	   while(x!=1) {
    	   logger.info("chose the fnction you want: ");
    	   logger.info("1 Veiw Product Category");
    	   logger.info("2 Add Category");
    	   logger.info("3 Delete Category");
    	   logger.info("4 Veiw product.");
    	   logger.info("5 add new product.");
    	   logger.info("6 Update product.");
    	   logger.info("7 Delete Product");
    	   logger.info("8 View customer Account .");
    	   logger.info("9 View all order.");
    	   logger.info("10 View installation Request  .");
    	   logger.info("11 Add new installer.");
    	   logger.info("12 log out .");
    	   
    	   scan=scann.nextLine();

    	   switch(scan) {
    	   case "1": product.viewCategories();
    	    	      break;
    	   case "2":logger.info("Enter name of category");
           String category=scann.nextLine();
          if( !cheackCategory(category)) {
        	  addCategory(category); 
   		   if(flaginsertC) {
		        	  logger.info("Insert category succssesfuly");  
		          }
		          else {
		        	  logger.info("Insert category unsuccssesfuly");  
		          } 
          }
          else {
        	  logger.info("this category is already exist");  
          }
           break;
    	   case "3":logger.info("Enter name of category");
           String categoryy=scann.nextLine();
    		   deleteCategory(categoryy); 
    		   deleteProductCategory(categoryy);
    		   if(flagdeleteC) {
		        	  logger.info("delete category succssesfuly");  
		          }
		          else {
		        	  logger.info("delete category unsuccssesfuly");  
		          }
           break;
    	   case "4":  logger.info("Enter name of category");
    	              String cat=scann.nextLine();
    		          viewProduct(cat);
   
    	              break;

    	   case "5":addProduct();
           break;
    	   case "6":updateProduct();
           break;
    	   case "7":deleteProduct();
           break;
    	   case "8":veiwCustomerAccount();
           break;
    	   case "9":adminViewOrder();
           break;
    	   case "10"://veiwInstallationRequest();
           break;
    	   case "11":adminViewOrder();
           break;
    	   case "12": x=1;//veiwInstallationRequest();
               break;
    	    default: logger.info("please chose one of the availabe choises");
    	    adminDashboard();
    	   }
    	   }
       }
       
    public boolean cheackCategory(String category) {
    	boolean flag=false;
    	  try {
   		   Class.forName("com.mysql.jdbc.Driver");
   		   String url="jdbc:mysql://localhost/caracc";
   		   con=DriverManager.getConnection(url,"root","");
   		   String sql="Select * from category where category='" +category+"' ";
   		   stm=con.prepareStatement(sql);
   		   rs=stm.executeQuery();
   	if(rs.next()){
   		flag=true;
   	}
   	else {
   		flag=false;
   	}

   		   stm.close();
   		   rs.close();
   	   }
   	   catch(Exception e) {
   		   e.printStackTrace();
   	   }
    	  return flag;
    }
    public void  addCategory(String category){
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="INSERT INTO Category (category) values(?)";
    		   stm=con.prepareStatement(sql);

    		   stm.setString(1,category);

    		   int num=stm.executeUpdate();
    		   if (num!=0) flaginsertC=true;
    		   else flaginsertC=false;

    		   stm.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   } 
       }
       public void deleteCategory(String categoryy){
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Delete from Category where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   int num =stm.executeUpdate();
    		   if (num!=0) flagdeleteC=true;
    		   else flagdeleteC=false;
    		   stm.close();

    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }  

       }

       public void deleteProductCategory(String categoryy) {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Delete from product where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   stm.executeUpdate();

    		   stm.close();

    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }    
       }
       public void viewProduct(String category){
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select * from product where category='" +category+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   String id="id = "+rs.getInt("id");
    			   String name= rs.getString("name");
    			   String description= rs.getString("description");
    			   int price= rs.getInt("price");

    			   Integer q=rs.getInt("quantity");

    			   logger.info(id+"  "+name+" "+description+" "+price+"$ "+q);

    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
       }


       public void addProduct() {
    	   logger.info("Enter category:");
    	   String category=scann.nextLine();

    	   logger.info("Enter product name:");
    	   String pname=scann.nextLine();

    	   logger.info("Enter product description:");
    	   String pdescription=scann.nextLine();

    	   logger.info("Enter product price:");
    	   String pprice=scann.nextLine();

    	   logger.info("Enter product quientity:");
    	   String pquientity=scann.nextLine();

    	   checkProduct(pname);
    	   if(!checkprod) { // product doesn't found
    		   Product p=new Product( pname, pdescription, Integer.parseInt(pprice),Integer.parseInt(pquientity),category);
    		   product.insertProduct(p);
    		   if(flaginsertP)
    			   logger.info("product added successfuly");

    	   }
    	   else {
    		   logger.info("this product already found");


    	   }

       }

       public void checkProduct(String name) {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select name from product where name='" +name+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   if (rs.next()) {
    			   checkprod=true;
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }

       }


       public void updateProduct() {

    	   product.viewCategories();
    	   logger.info("Please enter the name of category you want to update ");
    	   String category=scann.nextLine();


    	   viewProduct(category);

    	   logger.info("enter Id of product you wnat to update ");
    	   String id=scann.nextLine();
    	   logger.info("enter new name of product  ");
    	   String name=scann.nextLine();
    	   logger.info("enter new description of product  ");
    	   String desc=scann.nextLine();
    	   logger.info("enter new price of product  ");
    	   String p=scann.nextLine();
    	   int price=Integer.parseInt(p);
    	   logger.info("enter new quantity of product  ");
    	   String q=scann.nextLine();
    	   int quantity=Integer.parseInt(q);
    	   product.updateProduct(id,name,desc,price,quantity);
       }


       public void deleteProduct() {

    	   product.viewCategories();
    	   logger.info("Please enter the name of category you want to delete ");
    	   String category=scann.nextLine();

    	   viewProduct(category);

    	   logger.info("enter Id of product you wnat to delete ");
    	   scan=scann.nextLine();  
    	   product.removeProdct(Integer.parseInt(scan),category);

    	   if(flagdeleteP)  logger.info("product delete successfuly");

    	   else {logger.info("you enter wrong product id");
    	   //p.viewProduct(category);
    	   }

       }
       public void adminViewOrder() {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select*from orders where Buy=true";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   logger.info(rs.getInt("id")+" "+rs.getString("customername")+" "+rs.getString("productname")+" "+rs.getInt("productquantity")+" "+
    					   rs.getInt("productprice")+"$"+" "+rs.getString("city")+" "+rs.getString("street")+" "+rs.getString("phoneNumber"));//print order
    		   }
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
       }

       public void veiwCustomerAccount() {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";

    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select * from users where user_type='customer'";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();

    		   while (rs.next()) {
    			   Integer id=rs.getInt("id");
    			   String s=id.toString();
    			   logger.info(s+"\t"+rs.getString("email")+"\t"+rs.getString("name"));
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
       }

}