package models;

import dao.BookDataDAO;

public class CartItem {
	private Book book;
	private int quantity;
	
	public CartItem(Book book, int quantity) {
		this.book = book;
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void update_item() {
		if (this.book != null) {
			this.book = BookDataDAO.findBook(this.book.getIsbn());
		}
	}
}
