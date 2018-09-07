package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Order;
import provider.ConnectionProvider;

public class OrderDataDAO {
	public static String AddOrder(Order order) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into BookOrder (Isbn, Quantity) " + 
			       "	values(?, ?)");  
			   
			   ps.setLong(1, order.getIsbn());
			   ps.setInt(2, order.getQuantity());
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
	public static String deleteBookOrder(Long isbn, Timestamp ts) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "delete from BookOrder where " + 
			       "	isbn = ? and timeRequested = ?");  
			   
			   ps.setLong(1,isbn);
			   ps.setTimestamp(2, ts);
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
	public static List<Order> searchBookOrder(Long isbn, Integer q1, Integer q2) {
		return searchBookOrder(isbn, q1, q2, null, null, 0, 0, null, null);
	}
	public static List<Order> searchBookOrder(Long isbn, Integer q1, Integer q2,
			Timestamp ts1, Timestamp ts2, int limit, int offset, String sort_by, AtomicInteger queryCount) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   StringBuilder sb = new StringBuilder();
			   if (isbn != null || q1 != null || q2 != null || ts1 != null || ts2 != null) {
				   sb.append("where ");
			   }
			   boolean prev = false;
			   if (isbn != null) {
				   sb.append("ISBN = ? ");
				   prev = true;
			   }
			   if (q1 != null) {
				   if (prev) {
					   sb.append("And ");
				   }
				   sb.append("Quantity >= ? ");
				   prev = true;
			   }
			   if (q2 != null) {
				   if (prev) {
					   sb.append("And ");
				   }
				   sb.append("Quantity <= ? ");
				   prev = true;
			   }
			   if (ts1 != null) {
				   if (prev) {
					   sb.append("And ");
				   }
				   sb.append("TimeRequested >= ? ");
				   prev = true;
			   }
			   if (ts2 != null) {
				   if (prev) {
					   sb.append("And ");
				   }
				   sb.append("TimeRequested <= ? ");
				   prev = true;
			   }
			   StringBuilder cmd1 = new StringBuilder("Select * From BookOrder ");
			   StringBuilder cmd2 = new StringBuilder("Select count(*) From BookOrder ");
			   cmd2.append(sb);
			   if (sort_by != null) {
				   sb.append(" Order By ");
				   sb.append(sort_by);
			   }
			   if (limit != 0) {
				   sb.append(" limit ? ");
			   }
			   if (offset != 0) {
				   sb.append(" offset ? ");
			   }
			   cmd1.append(sb);
			   PreparedStatement ps1 = con.prepareStatement(cmd1.toString());
			   PreparedStatement ps2 = con.prepareStatement(cmd2.toString());
			   int ia = 1;
			   if (isbn != null) {
				   ps1.setLong(ia, isbn);
				   ps2.setLong(ia, isbn);
				   ia++;
			   }
			   if (q1 != null) {
				   ps1.setInt(ia, q1);
				   ps2.setInt(ia, q1);
				   ia++;
			   }
			   if (q2 != null) {
				   ps1.setInt(ia, q2);
				   ps2.setInt(ia, q2);
				   ia++;
			   }
			   if (ts1 != null) {
				   ps1.setTimestamp(ia, ts1);
				   ps2.setTimestamp(ia, ts1);
				   ia++;
			   }
			   if (ts2 != null) {
				   ps1.setTimestamp(ia, ts2);
				   ps2.setTimestamp(ia, ts2);
				   ia++;
			   }
			   if (limit != 0) {
				   ps1.setInt(ia, limit);
				   ia++;
			   }
			   if (offset != 0) {
				   ps1.setInt(ia, offset);
				   ia++;
			   }
			   ResultSet set = ps1.executeQuery(); 
			   List<Order> pubs = new ArrayList<Order>();
			   while(set.next()) {
				   pubs.add(new Order(set));
			   }
			   if (queryCount != null) {
				   set = ps2.executeQuery();
				   set.next();
				   queryCount.set(set.getInt(1));
			   }
			   return pubs;
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	public static int total_record() {
		try {
			Connection connect = ConnectionProvider.getConnection();
			ResultSet set2 = connect.createStatement().executeQuery(
					"Select count(*) From BookOrder");
			set2.next();
			return set2.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
