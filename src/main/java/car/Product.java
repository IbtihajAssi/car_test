package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
public class Product {
       private String name;
       private String description;
       private int price;
       private String category;
       int quantity;
       Connection con=null;
       PreparedStatement stm=null;
   	   ResultSet rs=null;
   	public  static Logger logger=Logger.getLogger(Login.class.getName());
   	
   	
    public Product() {
    	
    }
       public Product(String name,String description,int price,int quantity,String category) {
    	   this.name=name;
    	   this.description=description;
    	   this.price=price;
    	   this.category=category;
    	   this.quantity=quantity;
       }
       
       public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}
	
		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}

		public int getPrice() {
			return price;
		}


		public void setPrice(int price) {
			this.price = price;
		}
		
		public String getCategory() {
			return category;
		}


		public void setCategory(String category) {
			this.category = category;
		}
		public void setquantity(int quantity) {
			this.quantity = quantity;
		}
		public int getQuientity() {
			return quantity;
		}
		   public void viewCategories() {
	    		try {
		   			Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   			String sql="Select * from Category ";
		   			stm=con.prepareStatement(sql);
					rs=stm.executeQuery();
		   			while (rs.next()) {
		   			
		   				logger.info(rs.getString("category"));
		   
		   			}

		   			stm.close();
		   			rs.close();
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
			}   
	     
		   public void insertProduct(Product p){	     	   
	    	   try {
	    			Class.forName("com.mysql.jdbc.Driver");
	    			String url="jdbc:mysql://localhost/caracc";
	    			con=DriverManager.getConnection(url,"root","");
	    			String sql="INSERT INTO product (name,description,price,quantity,category) values(?,?,?,?,?)";
	    			stm=con.prepareStatement(sql);
	    		
	    	stm.setString(1,p.getName());
	    	stm.setString(2,p.getDescription());
	    	stm.setInt(3,p.getPrice());
	    	stm.setInt(4,p.getQuientity());
	    	stm.setString(5,p.getCategory());
	    int num=stm.executeUpdate();
	    if (num!=0)  Admin.flaginsertP=true;
		else Admin.flaginsertP=false;
		
	    	stm.close();
	    		}
	    		catch(Exception e) {
	    			e.printStackTrace();
	    		} 
	    	   
	    	   
	       }
		   
		   public void updateProduct(String id,String name,String desc,int price,int quintity) {
	    	   try {
	    		   Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   		    String sql="Update product set name=?,description=?,price=?,quantity=? where id='"+id+"'";
		            stm=con.prepareStatement(sql);
		            stm.setString(1, name);
		            stm.setString(2, desc);
		            stm.setInt(3, price);
		            stm.setInt(4, quintity);
		            
		            stm.executeUpdate();
		            stm.close();
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
	    	   
	       }
		   public void removeProdct(int id ,String category) {
	    	   try {
		   			Class.forName("com.mysql.jdbc.Driver");
					String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   			String sql="Delete from product where ID='" +id+"'and category='"+category+"' ";
		   			stm=con.prepareStatement(sql);
		   			int num =stm.executeUpdate();
		   					if (num!=0)  Admin.flagdeleteP=true;
					else Admin.flagdeleteP=false;
		   			stm.close();
		   			
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
	       }
		
		
		public String getProductName(int productId) {
			String name=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
		   name=rs.getString("name");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return name;
	}
		
		public int getProductPrice(int productId) {
			int price=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
		   price=rs.getInt("price");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
		public int getProductQuantity(int productId) {
			int quantity=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
				 quantity=rs.getInt("quantity");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quantity;
	}	
  
		public int getProductId(String name) {
			int id=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where name='" +name+"' ";
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
		
		public void updateProductQuantity(int productId,int p_quantity){
			 try {
	  		   Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   		    String sql="Update product set quantity=? where id='"+productId+"'";
		            stm=con.prepareStatement(sql);
		           
		            stm.setInt(1, p_quantity);
		            
		            stm.executeUpdate();
		            stm.close();
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
		}

		public void searchByName(String name) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url="jdbc:mysql://localhost/caracc";
				con=DriverManager.getConnection(url,"root","");
				String sql="Select*from product where name='" +name+"' ";
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				if(rs.next()) {
					Customer.flag_search=true;
					
				logger.info("id    name      description       price     category"+"\n"+
				rs.getInt("id")+" "+rs.getString("name")+" "+rs.getString("description")+" "+rs.getInt("price")+"$"+rs.getString("category"));//print order
					
				}
				else {
					Customer.flag_search=false;
				}
			
				rs.close();
				stm.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
	public void searchByPrice( int price) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from product where price='" +price+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				Customer.flag_search=true;
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					logger.info(rs.getInt("id")+" "+rs.getString("name")+" "+rs.getString("description")+" "+rs.getInt("price")+"$"+rs.getString("category"));//print order
				}
			}
			else {
				Customer.flag_search=false;
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		}

	public void searchByCategory(String category) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				Customer.flag_search=true;
				
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					logger.info(rs.getInt("id")+" "+rs.getString("name")+" "+rs.getString("description")+" "+rs.getInt("price")+"$");//print order
				}
			}
			else {
				Customer.flag_search=false;
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}













}