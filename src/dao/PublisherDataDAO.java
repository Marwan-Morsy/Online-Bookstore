package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Publisher;
import provider.ConnectionProvider;

public class PublisherDataDAO {
	public static String getPublisherName(int pid) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Publisher where pid = ?;");  
			   
			   ps.setInt(1, pid);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Publisher pub = new Publisher(set);
				   return pub.getName();
			   } else {
				   return null;
			   }
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	public static List<Publisher> searchPublisher(Publisher publisher, int limit, int offset, String sort_by, AtomicInteger queryCount) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   StringBuilder bs = new StringBuilder();
			   boolean prev = false;
			   if (publisher.getName() != null && !publisher.getName().trim().isEmpty()) {
				   bs.append(" name like ?");
				   prev = true;
			   }
			   if (publisher.getPid() != null) {
				   if (prev) {
					   bs.append(" and ");
				   }
				   bs.append(" pid = ?");
			   }
			   if (publisher.getAddress() != null && !publisher.getAddress().trim().isEmpty()) {
				   if (prev) {
					   bs.append(" and ");
				   }
				   bs.append(" address like ?");
			   }
			   if (publisher.getPhone() != null && !publisher.getPhone().trim().isEmpty()) {
				   if (prev) {
					   bs.append(" and ");
				   }
				   bs.append(" phone like ?");
			   }
			   StringBuilder sqlCom = new StringBuilder();
			   StringBuilder sqlCom2 = new StringBuilder();
			   if (bs.length() == 0) {
				   sqlCom.append("Select * From Publisher");
				   sqlCom2.append("Select count(*) From Publisher");
			   } else {
				   sqlCom.append("Select * From Publisher where ");
				   sqlCom.append(bs);
				   sqlCom2.append("Select count(*) From Publisher where");
				   sqlCom2.append(bs);
			   }
			   if (sort_by != null) {
				   sqlCom.append(" Order By ");
				   sqlCom.append(sort_by);
			   }
			   if (limit != 0) {
				   sqlCom.append(" limit ? ");
			   }
			   if (offset != 0) {
				   sqlCom.append(" offset ? ");
			   }
			   PreparedStatement ps = con.prepareStatement(sqlCom.toString());
			   PreparedStatement ps2 = con.prepareStatement(sqlCom2.toString());
			   int ia = 1;
			   if (publisher.getName() != null && !publisher.getName().trim().isEmpty()) {
				   ps.setString(ia, "%" + publisher.getName() + "%");
				   ps2.setString(ia, "%" + publisher.getName() + "%");
				   ia++;
			   }
			   if (publisher.getPid() != null) {
				   ps.setInt(ia, publisher.getPid());
				   ps2.setInt(ia, publisher.getPid());
				   ia++;
			   }
			   if (publisher.getAddress() != null && !publisher.getAddress().trim().isEmpty()) {
				   ps.setString(ia, "%" + publisher.getAddress() + "%");
				   ps2.setString(ia, "%" + publisher.getAddress() + "%");
				   ia++;
			   }
			   if (publisher.getPhone() != null && !publisher.getPhone().trim().isEmpty()) {
				   ps.setString(ia, "%" + publisher.getPhone() + "%");
				   ps2.setString(ia, "%" + publisher.getPhone() + "%");
				   ia++;
			   }
			   if (limit != 0) {
				   ps.setInt(ia, limit);
				   ia++;
			   }
			   if (offset != 0) {
				   ps.setInt(ia, offset);
				   ia++;
			   }
			   ResultSet set = ps.executeQuery(); 
			   List<Publisher> pubs = new ArrayList<Publisher>();
			   while(set.next()) {
				   pubs.add(new Publisher(set));
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
	public static Integer getPublisherId(String publisherName) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Publisher where name = ?;");  
			   
			   ps.setString(1, publisherName);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Publisher pub = new Publisher(set);
				   return pub.getPid();
			   } else {
				   return null;
			   }
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	
	public static List<Publisher> searchPublisher(Publisher publisher) {
		return searchPublisher(publisher, 0, 0, null, null);
	}
	public static List<Publisher> searchFirstMatchPublisher(Publisher publisher) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   boolean name = publisher.getName() != null,
					  address = publisher.getAddress() != null,
					  phone = publisher.getPhone() != null;
			   StringBuilder sql = new StringBuilder("Select * From Publisher where ");
			   boolean prev_added = false;
			   if (phone) {
				   sql.append("phone like ? ");
				   prev_added = true;
			   }
			   if (name) {
				   if (prev_added) {
					   sql.append(" and ");
				   }
				   sql.append(" name like ? ");
				   prev_added = true;
			   }
			   if (address) {
				   if (prev_added) {
					   sql.append(" and ");
				   }
				   sql.append(" address like ?");
				   prev_added = true;
			   }
			   PreparedStatement ps=con.prepareStatement(  
			       sql.toString());  
			   int col = 1;
			   if (phone) {
				   ps.setString(col, "%" + publisher.getPhone() + "%");
				   col++;
			   }
			   if (name) {
				   ps.setString(col, "%" + publisher.getName() + "%");
				   col++;
			   }
			   if (address) {
				   ps.setString(col, "%" + publisher.getAddress() + "%");
				   col++;
			   }
			   ResultSet set = ps.executeQuery(); 
			   List<Publisher> pubs = new ArrayList<Publisher>();
			   while(set.next()) {
				   pubs.add(new Publisher(set));
			   }
			   return pubs;
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	public static String addPublisher(Publisher publisher) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into Publisher (Name, Phone, Address)\n" + 
			       "	values(?, ?, ?);");  
			   
			   ps.setString(1, publisher.getName());
			   ps.setString(2, publisher.getPhone());
			   ps.setString(3, publisher.getAddress());
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
	public static int total_record() {
		try {
			Connection connect = ConnectionProvider.getConnection();
			ResultSet set2 = connect.createStatement().executeQuery(
					"Select count(*) From Publisher");
			set2.next();
			return set2.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String updatePublisher(Integer pid, Publisher pub) {
		try{  
			   Connection con= ConnectionProvider.getConnection();
			   StringBuilder sb = new StringBuilder();
			   boolean prev = false,
					   pubname = pub.getName() != null && !pub.getName().trim().isEmpty(),
					   pubphone = pub.getPhone() != null && !pub.getPhone().trim().isEmpty(),
					   pubaddress = pub.getAddress() != null && !pub.getAddress().trim().isEmpty();
			   if (pubname) {
				   sb.append(" name = ?");
				   prev = true;
			   }
			   if (pubphone) {
				   if (prev) {
					   sb.append(",");
				   }
				   sb.append("phone = ?");
			   }
			   if (pubaddress) {
				   if (prev) {
					   sb.append(",");
				   }
				   sb.append("address = ?");
			   }
			   if (sb.length() != 0) {
				   PreparedStatement ps=con.prepareStatement(  
				       "Update Publisher set "+ sb.toString() + 
				       "	where pid = ?");
				   int ia = 1;
				   if (pubname) {
					   ps.setString(ia, pub.getName().trim());
					   ia++;
				   }
				   if (pubphone) {
					   ps.setString(ia, pub.getPhone().trim());
					   ia++;
				   }
				   if (pubaddress) {
					   ps.setString(ia, pub.getAddress().trim());
					   ia++;
				   }
				   ps.setInt(ia, pid);
				   ps.executeUpdate(); 
				   return null;
			   } else {
				   return "No Edited Attributes";
			   }
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
}
