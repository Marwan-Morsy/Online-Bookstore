package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Purchase {
	private Integer uid;
	private Long isbn;
	private Timestamp purchaseDate;
	private Integer amount;
	private Float totalPrice;
	public Purchase(Integer uid, Long isbn, Timestamp purchaseDate, Integer amount, Float totalPrice) {
		this.uid = uid;
		this.isbn = isbn;
		this.purchaseDate = purchaseDate;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}
	public Purchase(ResultSet s) {
		try {
			this.uid = s.getInt(1);
			this.isbn = s.getLong(2);
			this.purchaseDate = s.getTimestamp(3);
			this.amount = s.getInt(4);
			this.totalPrice = s.getFloat(5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
