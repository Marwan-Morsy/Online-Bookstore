package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import models.Cart;
import models.CartItem;
import models.Purchase;
import provider.ConnectionProvider;

public class PurchasesDAO {
	public static String insertPurchase(Purchase pur, Connection con) {
		try{       
		   PreparedStatement ps=con.prepareStatement(  
		       "insert into Purchase (uid, isbn, amount, totalPrice)\n" + 
		       "	values(?, ?, ?, ?);");  
		   
		   ps.setInt(1, pur.getUid());
		   ps.setLong(2, pur.getIsbn());
		   ps.setInt(3, pur.getAmount());
		   ps.setFloat(4, pur.getTotalPrice());
		   ps.executeUpdate(); 
		   ps.close();
		   return null;
	   } catch (SQLException ex) {
		   return ex.getMessage();
	   }catch(Exception e){
		   e.printStackTrace();
	   }  
	   return "Unknown Error";
	}
	public static String handleCart(Cart cart, int uid) throws SQLException {
		Connection con = ConnectionProvider.getConnection();
		String res = null;
		try {
			con.setAutoCommit(false);
			List<CartItem> items = cart.getItems();
			boolean error = false;
			for (int i = 0; !error && i < items.size(); i++) {
				CartItem item = items.get(i);
				Purchase pur = new Purchase(uid, item.getBook().getIsbn(),
						null, item.getQuantity(), item.getQuantity() * item.getBook().getPrice());
				res = insertPurchase(pur, con);
				if (res != null) {
					error = true;
					StringBuilder temp = new StringBuilder("Error in book with isbn ");
					temp.append(item.getBook().getIsbn());
					temp.append(" \"");
					temp.append(item.getBook().getTitle());
					temp.append("\" : ");
					temp.append(res);
					res =  temp.toString();
				}
			}
			if (error) {
				con.rollback();
			} else {
				con.commit();
			}
		} catch (SQLException e) {
			con.rollback();
			res = e.getMessage();
		} finally {
			con.setAutoCommit(true);
		}
		return res;
	}
}
