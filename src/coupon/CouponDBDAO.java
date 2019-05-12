package coupon;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

import main.Database;

public class CouponDBDAO implements CouponDao {

	Connection con;

	@Override
	public void insertCoupon(Coupon coupon) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "INSERT INTO Coupon (id, title, startDate, endDate, amount, type, message, price, image)  VALUES(?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setLong(1, coupon.getId());
			pstmt.setString(2, coupon.getTitle());
			pstmt.setDate(3, (Date) coupon.getStartDate());
			pstmt.setDate(4, (Date) coupon.getEndDate());
			pstmt.setLong(5, coupon.getAmount());
			pstmt.setString(6, coupon.getType().toString());
			pstmt.setString(7, coupon.getMessage());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());

			pstmt.executeUpdate();
			System.out.println("Coupon created : " + coupon.toString());
		} catch (SQLException e) {
			throw new Exception("Coupon creation failed");
		} finally {
			con.close();
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "DELETE FROM Coupons WHERE id=?";

		try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			pstm1.setLong(1, coupon.getId());
			pstm1.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove Coupon");
		} finally {
			con.close();
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		try (Statement stm = con.createStatement()) {
			String sql = "UPDATE Coupon " + " SET couponName ='" + coupon.getTitle() + "', startDate = '"
					+ coupon.getStartDate() + "', endDate = " + coupon.getEndDate() + "', amount = '"
					+ coupon.getAmount() + "', type = '" + coupon.getType() + "', message = '" + coupon.getMessage()
					+ "', price = '" + coupon.getPrice() + "', image = '" + coupon.getImage() + "' WHERE ID = "
					+ coupon.getId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update Company failed");
		}
	}

	@Override
	public Coupon getCoupon(int id) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Coupon coupon = new Coupon();
		try (PreparedStatement pstmt = (PreparedStatement) con.createStatement()) {
			String sql = "SELECT * FROM Coupon WHERE ID=" + id;
			ResultSet rs = pstmt.executeQuery(sql);
			rs.next();
			coupon.setId(rs.getLong(1));
			coupon.setTitle(rs.getString(2));
			coupon.setStartDate(rs.getDate(3));
			coupon.setEndDate(rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setType(rs.getString(6));

		} catch (SQLException e) {
			throw new Exception("unable to get product data");
		} finally {
			con.close();
		}
		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Coupon> set = new HashSet<>();
		String sql = "SELECT * FROM Coupon";
		try (PreparedStatement pstmt = (PreparedStatement) con.createStatement();
				ResultSet rs = pstmt.executeQuery(sql)) {
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
	public Set<Coupon> getCouponByType(CouponType couponType) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Coupon> set = new HashSet<>();
		String sql = "SELECT * FROM Coupon WHERE coupon_type =" + couponType;
		try (PreparedStatement pstmt = (PreparedStatement) con.createStatement();
				ResultSet rs = pstmt.executeQuery(sql)) {
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

}
