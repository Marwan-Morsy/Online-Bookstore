package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Author;
import provider.ConnectionProvider;

public class AuthorDataDAO {
	public static List<Author> searchAuthor(Author author) {
		return searchAuthor(author, 0, 0, null, null);
	}
	public static List<Author> searchAuthor(Author author, int limit, int offset, String sort_by, AtomicInteger queryCount) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   StringBuilder bs = new StringBuilder();
			   boolean prev = false;
			   if (author.getName() != null && !author.getName().trim().isEmpty()) {
				   bs.append(" name like ?");
				   prev = true;
			   }
			   if (author.getAid() != null) {
				   if (prev) {
					   bs.append(" and ");
				   }
				   bs.append(" aid = ?");
			   }
			   StringBuilder sqlCom = new StringBuilder();
			   StringBuilder sqlCom2 = new StringBuilder();
			   if (bs.length() == 0) {
				   sqlCom.append("Select * From Author");
				   sqlCom2.append("Select count(*) From Author");
			   } else {
				   sqlCom.append("Select * From Author where ");
				   sqlCom.append(bs);
				   sqlCom2.append("Select count(*) From Author where");
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
			   if (author.getName() != null && !author.getName().trim().isEmpty()) {
				   ps.setString(ia, "%" + author.getName() + "%");
				   ps2.setString(ia, "%" + author.getName() + "%");
				   ia++;
			   }
			   if (author.getAid() != null) {
				   ps.setInt(ia, author.getAid());
				   ps2.setInt(ia, author.getAid());
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
			   List<Author> pubs = new ArrayList<Author>();
			   while(set.next()) {
				   pubs.add(new Author(set));
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
	public static List<Author> getAuthorsOfBook(Long isbn) {
		try{  
		   Connection con= ConnectionProvider.getConnection(); 
		   
		   
		   PreparedStatement ps = con.prepareStatement("Select AID, Name From (Author Natural Join "
		   		+ "AuthoredBy) where isbn = ? ");
		   ps.setLong(1, isbn); 
		   ResultSet set = ps.executeQuery(); 
		   List<Author> pubs = new ArrayList<Author>();
		   while(set.next()) {
			   pubs.add(new Author(set));
		   }
		   return pubs;
	   } catch (SQLException ex) {
		   return null;
	   }catch(Exception e){
		   e.printStackTrace();
	   }  
	   return null;
	}
	public static Integer getAuthorId (String authorName) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Author where name = ?;");     
			   ps.setString(1, authorName);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Author author = new Author(set);
				   return author.getAid();
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
	public static Author getAuthorByName (String authorName) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Select * From Author where name = ?;");     
			   ps.setString(1, authorName);
			   ResultSet set = ps.executeQuery(); 
			   if (set.next()) {
				   Author author = new Author(set);
				   return author;
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
	public static List<Author> searchFirstMatchAuthor(Author author) {
		try{  
			   Connection con= ConnectionProvider.getConnection(); 
			   PreparedStatement ps = con.prepareStatement("Select * From Author where name like ? limit 10");
			   ps.setString(1, author.getName() + "%"); 
			   ResultSet set = ps.executeQuery(); 
			   List<Author> pubs = new ArrayList<Author>();
			   while(set.next()) {
				   pubs.add(new Author(set));
			   }
			   return pubs;
		   } catch (SQLException ex) {
			   return null;
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return null;
	}
	public static String updateAuthor(Integer aid, String newName) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "Update Author set Name = ?" + 
			       "	where aid = ?");  
			   
			   ps.setString(1, newName);
			   ps.setInt(2, aid);
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	}
	public static String addAuthor(Author author) {
		try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into Author (Name) " + 
			       "	values(?);");  
			   
			   ps.setString(1, author.getName());
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
					"Select count(*) From Author");
			set2.next();
			return set2.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
