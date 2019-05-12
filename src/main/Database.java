package main;

import java.sql.*;

public class Database {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/couponsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";
	
	public static String sqlCompany = "create table Company (" + "id integer not null primary key,"
			+ "comp_name varchar(30) not null," + "password text not null, " + "email text not null)";

	public static String sqlCustomer = "create table Customer (" + "id integer not null primary key,"
			+ "Cust_Name varchar(50) not null," + "password text not null)";

	public static String sqlCoupon = "create table Coupon (" + "id integer not null primary key,"
			+ "title text not null," + "start_date date not null," + "end_date date not null," + "amount integer,"
			+ "coupon_type text not null, " + "message text," + "price double," + "image text)";

	public static String sqlCompanyCoupons = "create table CompanyCoupon("
			+ " CompanyId integer not null, CompanyCouponId integer not null, foreign key "
			+ " (CompanyId) references Company (id) on delete cascade,"
			+ "foreign key (CompanyCouponId) references Coupon (id) on delete cascade, primary key (CompanyId , CompanyCouponId) )";

	public static String sqlDropTableCompany = "drop table company";
	public static String sqlDropTableCustomer = "drop table customer";
	public static String sqlDropTableCoupon = "drop table coupon";

	public static String getDriverData() {
		return "JDBC_DRIVER";
	}

	public static String getDBUrl() {
		return "jdbc:mysql://localhost:3306/couponsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";
	}

	public static void createTable(String sql) throws SQLException {
		Connection con = null;
		con = DriverManager.getConnection(Database.getDBUrl(), "root", "1234");
		Statement st = con.createStatement();
		st.executeUpdate(sql);
		System.out.println("Success " + sql);
		con.close();
	}

	public static void dropTable(String sql) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(DB_URL, "root", "1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement p = con.prepareStatement(sql);
		p.executeUpdate();
		System.out.println("Table dropped");
		try {
			
		} finally {
			con.close();
		}
		
	}
}
