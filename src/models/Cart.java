package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
	private List<CartItem> items;
	private Map<Long, CartItem> map;
	private Float totalPrice;
	public Cart() {
		items = new ArrayList<CartItem>();
		map = new HashMap<Long, CartItem> ();
		totalPrice = 0f;
	}
	public void addToCart(Book book) {
		if (!map.containsKey(book.getIsbn())) {
			CartItem item = new CartItem(book, 1);
			items.add(item);
			totalPrice += book.getPrice();
			map.put(book.getIsbn(), item);
		}
	}
	
	public void changeQuantity(Long isbn, Integer quantity) {
		if (map.containsKey(isbn)) {
			CartItem item = map.get(isbn);
			if (quantity > 0) {
				totalPrice += ((quantity - item.getQuantity()) * item.getBook().getPrice());
				item.setQuantity(quantity);
			} else {
				totalPrice -= item.getQuantity() * item.getBook().getPrice();
				map.remove(isbn);
				items.remove(item);
			}
		}
	}
	public void update_prices() {
		totalPrice = 0f;
		for (int i = 0; i < items.size(); i++) {
			long isbn = items.get(i).getBook().getIsbn();
			items.get(i).update_item();
			totalPrice += items.get(i).getQuantity() * items.get(i).getBook().getPrice();
			if (items.get(i).getBook() == null) {
				map.remove(isbn);
				items.remove(items.get(i));
			}
		}
	}
	public boolean hasBook(Long isbn) {
		return map.containsKey(isbn);
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	public void clearCart() {
		map.clear();
		items.clear();
		totalPrice = 0f;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
}
