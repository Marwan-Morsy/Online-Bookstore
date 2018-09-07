package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Category;
import provider.ConnectionProvider;

public class CategoryDataDAO {
	public static String getCategoryName(int cid) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Category where cid = ?;");  
			   
			   ps.setInt(1, cid);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Category pub = new Category(set);
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
	public static Integer getCategoryId(String category) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Category where name = ?;");  
			   
			   ps.setString(1, category);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Category pub = new Category(set);
				   return pub.getCid();
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
	public static List<Category> searchCategory(Category category) {
		return searchCategory(category, 0, 0, null, null);
	}
	
	public static List<Category> searchCategory(Category category, int limit, int offset, String sort_by, AtomicInteger queryCount) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   StringBuilder bs = new StringBuilder();
			   boolean prev = false;
			   if (category.getName() != null && !category.getName().trim().isEmpty()) {
				   bs.append(" name like ?");
				   prev = true;
			   }
			   if (category.getCid() != null) {
				   if (prev) {
					   bs.append(" and ");
				   }
				   bs.append(" cid = ?");
			   }
			   StringBuilder sqlCom = new StringBuilder();
			   StringBuilder sqlCom2 = new StringBuilder();
			   if (bs.length() == 0) {
				   sqlCom.append("Select * From Category");
				   sqlCom2.append("Select count(*) From Category");
			   } else {
				   sqlCom.append("Select * From Category where ");
				   sqlCom.append(bs);
				   sqlCom2.append("Select count(*) From Category where");
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
			   if (category.getName() != null && !category.getName().trim().isEmpty()) {
				   ps.setString(ia, "%" + category.getName() + "%");
				   ps2.setString(ia, "%" + category.getName() + "%");
				   ia++;
			   }
			   if (category.getCid() != null) {
				   ps.setInt(ia, category.getCid());
				   ps2.setInt(ia, category.getCid());
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
			   List<Category> pubs = new ArrayList<Category>();
			   while(set.next()) {
				   pubs.add(new Category(set));
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
	public static List<Category> getFirstMatchCategory(Category category) {
		try{  
			Connection con= ConnectionProvider.getConnection(); 
			PreparedStatement ps=con.prepareStatement("Select * From Category where name like ? limit 10");  
			   ps.setString(1, category.getName() + "%"); 
			   
			   ResultSet set = ps.executeQuery(); 
			   List<Category> pubs = new ArrayList<Category>();
			   while(set.next()) {
				   pubs.add(new Category(set));
			   }
			   return pubs;
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	public static String addCategory(Category category) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into Category (Name)\n" + 
			       "	values(?);");  
			   
			   ps.setString(1, category.getName());
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
	public static String updateCategory(Integer cid, String newName) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Update Category set Name = ?" + 
			       "	where cid = ?");  
			   
			   ps.setString(1, newName);
			   ps.setInt(2, cid);
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
					"Select count(*) From Category");
			set2.next();
			return set2.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
