/** Company Class
 * 
 */

package company;

import java.util.Set;

import coupon.Coupon;

public class Company {

	// variable declaration
	private long id;
	private String compName;
	private String password;
	private String email;
	private Set<Coupon> coupons;

	// CTor
	public Company(String compName, String password, String email) {
		setId(id);
		setCompName(compName);
		setPassword(password);
		setEmail(email);
		setCoupons(coupons);
	}

	public Company() {
	}

	// getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	// toString
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}

}
