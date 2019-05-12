/** Company DbDAO - unfinished ! methods with Collections are not 
 * complete
 */

package company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import coupon.Coupon;
import main.Database;

public class CompanyDBDAO implements CompanyDao {

	Connection con;

	@Override
		public void insertCompany(Company company) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl(), "root", "1234");
			String sql = "INSERT INTO Company (id, comp_name, password, email)  VALUES(?,?,?,?)";
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	
				pstmt.setLong(1, company.getId());
				pstmt.setString(2, company.getCompName());
				pstmt.setString(3, company.getPassword());
				pstmt.setString(4, company.getEmail());
	
				pstmt.executeUpdate();
				System.out.println("Company created : " + company.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				con.close();
			}
		}

	@Override
	public void removeCompany(Company company) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "DELETE FROM Company WHERE id=?";

		try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			pstm1.setLong(1, company.getId());
			pstm1.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove company");
		} finally {
			con.close();
		}
	}

	@Override
	public void updateCompany(Company company) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		try (Statement stm = con.createStatement()) {
			String sql = "UPDATE Company " + " SET compName ='" + company.getCompName() + "', password = '"
					+ company.getPassword() + "', email = " + "', email = '" + company.getEmail() + "' WHERE ID = "
					+ company.getId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update Company failed");
		}
	}

	@Override
	public Company getCompany(int id) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Company company = new Company();
		try (Statement stm = con.createStatement()) {
			String sql = "SELECT * FROM Company WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			company.setId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));

		} catch (SQLException e) {
			throw new Exception("unable to get Company data");
		} finally {
			con.close();
		}
		return company;
	}

	@Override
	public synchronized Set<Company> getAllCompanies() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl(), "root", "1234");
		Set<Company> set = new HashSet<>();
		String sql = "SELECT id FROM Company ";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				// Set<Coupon> = rs.get
				set.add(new Company(compName, password, email));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Company data");
		} finally {
			con.close();
		}
		return set;
	}

	@Override
	public Set<Coupon> getCoupons(String compName) throws Exception {

		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Coupon> set = new HashSet<>();
		try (Statement stm = con.createStatement()) {
			String sql = "SELECT * FROM Company WHERE comp_name=" + compName;
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(rs.getString(6));

				set.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Coupon data");
		} finally {
			con.close();
		}
		return set;

	}

	@Override
	public boolean login(String compName, String password) {

		return false;
	}

}
