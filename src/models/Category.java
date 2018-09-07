package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {
	private Integer cid;
	private String name;
	public Category(Integer cid, String name) {
		this.cid = cid;
		this.name = name;
	}
	public Category(ResultSet set) {
		try {
			this.cid = set.getInt(1);
			this.name = set.getString(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
