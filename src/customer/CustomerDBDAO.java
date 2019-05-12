/** Customer DbDAO - unfinished ! missing methods where collections are 
 * concerned.
 */

package customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import company.Company;
import coupon.Coupon;
import main.Database;

public class CustomerDBDAO implements CustomerDao {

	Connection con;

	@Override
	public void createCustomer(Customer customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "INSERT INTO Customer (id, custName, password)  VALUES(?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setLong(1, customer.getId());
			pstmt.setString(2, customer.getCustName());
			pstmt.setString(3, customer.getPassword());

			pstmt.executeUpdate();
			System.out.println("customer created : " + customer.toString());
		} catch (SQLException e) {
			throw new Exception("customer creation failed");
		} finally {
			con.close();
		}
	}

	@Override
	public void removeCustomer(Customer customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "DELETE FROM Customer WHERE id=?";

		try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			pstm1.setLong(1, customer.getId());
			pstm1.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer");
		} finally {
			con.close();
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		try (Statement stm = con.createStatement()) {
			String sql = "UPDATE customer " + " SET compName ='" + customer.getCustName() + "', password = '"
					+ customer.getPassword() + "', email = " + "' WHERE ID = " + customer.getId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update Company failed");
		}
	}

	@Override
	public Customer getCustomer(int id) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Customer customer = new Customer();
		try (Statement stm = con.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setId(rs.getLong(1));
			customer.setCustName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get Customer data");
		} finally {
			con.close();
		}
		return customer;
	}

	@Override
	public Set<Customer> getAllCustomers() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Customer> set = new HashSet<>();
		String sql = "SELECT * FROM Customer ";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));

				set.add(customer);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer data");
		} finally {
			con.close();
		}
		return set;
	}

	@Override
	public boolean login(String custName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Coupon> getCoupons(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
