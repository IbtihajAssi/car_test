package car;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.logging.Logger;

public class Login {
	public  Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scanner=new Scanner(System.in);
	public  String scan;
	public boolean flaglogin=false;
	public boolean flagemail=false;
	public boolean flagpass=false;
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
    public boolean isLoginPage=false;
    public boolean flagname=false;
    public boolean flagconfpass=false;
	public Login() {
		isLoginPage=true;
	}
	public void checkEmail(String email,String usertype) {
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select email from users where email='" +email+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
				flagemail=true;

			}
			stm.close();
			rs.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void checkpassword(String email,String pass,String usertype) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select email from users where email='" +email+"'and password='" +pass+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (!rs.next()) {
				flagpass=false;
			}
			else{
				flagpass=true;
			}
stm.close();
rs.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void logIn(String usertype,String email,String password) {

		if(!email.contains("@")||!email.contains(".")) {
			logger.info("syntex error in email");
			flaglogin=true;
			start(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(!flagemail) {//doesnt exist email
				logger.info("user email doesnt exist"); //
				flaglogin=true;
				start(usertype);

			}
			else{ //user inter correct email
				checkpassword(email,password,usertype);
				
				if(flagpass) {
					logger.info(usertype);

					if(usertype.equalsIgnoreCase("admin")) {
						Admin admin=new Admin();
                     admin.adminDashboard();
					}
					else if(usertype.equalsIgnoreCase("customer")) {//customer log in sucsessfully
						Customer customer=new Customer();
						customer.customerDashboard(email);
					}
					else  {//installer log in sucsessfully

					}
				}
				else {
					logger.info("you enter incorrect password"); //
					flaglogin=true;
					start(usertype);
				}
			}

		}
	}
	
public void insertuser(String email,String name,String password,String usertype) {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="INSERT INTO users (name,email,password,user_type) values(?,?,?,?)";
		stm=con.prepareStatement(sql);
stm.setString(1,name);
stm.setString(2,email);
stm.setString(3, password);
stm.setString(4, usertype);
stm.executeUpdate();
stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
	public void regesterUser(String email,String username,String password,String confirmPassword,String usertype) {
		
		if(!email.contains("@")||!email.contains(".")) {
			logger.info("syntex error in email");
			signup(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(flagemail) {//already exist email
				logger.info("this email already exist"); //
				signup(usertype);
			}
			else{ //user inter correct email
				int count=0;
				for(int i=0;i<username.length();i++) {
					if(Character.isDigit(username.charAt(i))) {
						count++;
					}
				}
				if(count!=username.length() &&  !Character.isDigit(username.charAt(0))) {
					
						flagname=true;
				if(confirmPassword.equals(password)) {
					flagconfpass=true;
					
					insertuser(email,username,password,usertype);
				logger.info("you sign up sucessfulley");
				logIn(usertype,email,password);
				}
				else {
					logger.info("your password doesnt match your confirm password");
					signup(usertype);
				}
				
					
				}
				else {
					logger.info("your name should contain character ");
					signup(usertype);

				}
			}

		}

		
		
	}
	public void signup(String usertype) {
		logger.info(" Enter your email :");
		String email=scanner.nextLine();


		logger.info(" Enter your username :");
		String username=scanner.nextLine();

		logger.info(" Enter your password :");
		String password=scanner.nextLine();

		logger.info(" Confirm your password :");
		String confirmPassword=scanner.nextLine();
		
		regesterUser( email,username,password,confirmPassword,usertype);
		

	}

	public void start(String usertype) {
		if(!flaglogin) {
			if(!usertype.equalsIgnoreCase("admin")){
				logger.info("1- sign up");
			}
			logger.info("2- login");
			logger.info("3- go back");
			scan=scanner.nextLine();
		}
		if(scan.equalsIgnoreCase("1")&&!usertype.equalsIgnoreCase("admin")) {
			signup(usertype);
		}
		else if(scan.equalsIgnoreCase("2")) {
			logger.info("to login please enter your email and password");
			logger.info(" email: ");
			String email=scanner.nextLine();
			logger.info("password: ");
			String pass=scanner.nextLine();

			logIn(usertype,email,pass);

		}
		else if(scan.equalsIgnoreCase("3")) {
			mainMenue();
		}

	}

	public void mainMenue() {
		logger.info("Welcome to Carr Accessories company");
		while(true) {
			logger.info("Please choose between the specific users");
			logger.info("1-Admin");
			logger.info("2-Customer");
			logger.info("3-Installer");
			logger.info("4-exit");
			scan=scanner.nextLine();

			if(scan.equalsIgnoreCase("1")) {// user is admin
				start("admin");

			}

			else if(scan.equalsIgnoreCase("2")){//user is Customer
				start("customer");
			}

			else if(scan.equalsIgnoreCase("3")){//user is installer
				start("installer");
			}
			else if(scan.equalsIgnoreCase("4")){//user is installer
				logger.info("you log out succesfully");

				System.exit(0);
			}
			else {
				logger.info("please make sure to enter the right user");

			}

		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Connection con=null;
//		PreparedStatement stm=null;
//		ResultSet rs=null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url="jdbc:mysql://localhost/caracc";
//			con=DriverManager.getConnection(url,"root","");
//			String sql="Select email from users where id=1;" ;
//			stm=con.prepareStatement(sql);
//			rs=stm.executeQuery();
//			if (rs.next()) {
//				System.out.println(rs.getString("email"));
//			}
//
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		Login l=new Login();
		l.mainMenue();



	}


}