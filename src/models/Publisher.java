package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Publisher {
	private Integer pid;
	private String name;
	private String address;
	private String phone;
	
	public Publisher(Integer pid, String name, String address, String phone) {
		this.pid = pid;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	public Publisher(ResultSet set) {
		try {
			this.pid = set.getInt(1);
			this.name = set.getString(2);
			this.address = set.getString(3);
			this.phone = set.getString(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
