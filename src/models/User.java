package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String username;
	private String password;
	private String email;
	private int uid;
	private boolean manager;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	public User(String username, String password, String email, int uid, boolean manager, String firstName,
		String lastName, String phone, String address) {
			this.username = username;
			this.password = password;
			this.email = email;
			this.uid = uid;
			this.manager = manager;
			this.firstName = firstName;
			this.lastName = lastName;
			this.phone = phone;
			this.address = address;
	}
	public User(ResultSet set) {
		try {
			this.username = set.getString(2);
			this.password = set.getString(3);
			this.uid = set.getInt(1);
			this.email = set.getString(4);
			this.manager = set.getBoolean(9);
			this.firstName = set.getString(5);
			this.lastName = set.getString(6);
			this.phone = set.getString(7);
			this.address = set.getString(8);
		} catch (SQLException e) {
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
