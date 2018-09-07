package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Order {
	private Long isbn;
	private Timestamp timeRequested;
	private Integer quantity;
	
	public Order(Long isbn, Timestamp timeRequested, Integer quantity) {
		this.isbn = isbn;
		this.setTimeRequested(timeRequested);
		this.quantity = quantity;
	}
	
	public Order(ResultSet set) {
		try {
			this.isbn = set.getLong(1);
			this.setTimeRequested(set.getTimestamp(2));
			this.quantity = set.getInt(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Timestamp getTimeRequested() {
		return timeRequested;
	}

	public void setTimeRequested(Timestamp timeRequested) {
		this.timeRequested = timeRequested;
	}
	
	
}
