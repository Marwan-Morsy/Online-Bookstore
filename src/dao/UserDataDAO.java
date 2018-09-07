package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.User;
import provider.ConnectionProvider;

public class UserDataDAO {
	 static final String[] attributes = {"username","password", "email", "firstName", "lastName", "phone", "address"};
	   // Find a User by userName and password.
	   public static User findUser(String userName, String password) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "select * from User where username=? and password=?");  
			   
			   ps.setString(1, userName);
			   ps.setString(2, md5(password));     
			   ResultSet rs=ps.executeQuery(); 
			   
			   if(rs.next()) {
				   User u = new User(rs);
				   return u;
			   } else {
				   return null;
			   }
			                 
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
	      
	      return null;
	   }
	   public static User findUserByEmail(String userName, String email) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "select * from User where username=? and email=?");  
			   
			   ps.setString(1, userName);
			   ps.setString(2, email);     
			   ResultSet rs=ps.executeQuery(); 
			   
			   if(rs.next()) {
				   User u = new User(rs);
				   return u;
			   } else {
				   return null;
			   }
			                 
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
	      
	      return null;
	   }
	   public static String addUser(User user) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into User (username, password, email, firstName, lastName, phone, address, manager)\n" + 
			       "	values(?, ?, ?, ?, ?, ?,\n" + 
			       "		?, ?);");  
			   
			   ps.setString(1, user.getUsername());
			   ps.setString(2, md5(user.getPassword()));     
			   ps.setString(3, user.getEmail());
			   ps.setString(4, user.getFirstName());
			   ps.setString(5, user.getLastName());
			   ps.setString(6, user.getPhone());
			   ps.setString(7, user.getAddress());
			   ps.setBoolean(8, user.isManager());
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	   }
	   
	   public static String updateUser(User user) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();
			   StringBuilder sql = new StringBuilder("Update User Set ");
			   List<String> values = new ArrayList<String> ();
			   values.add(user.getUsername());
			   values.add(user.getPassword());
			   values.add(user.getEmail());
			   values.add(user.getFirstName());
			   values.add(user.getLastName());
			   values.add(user.getPhone());
			   values.add(user.getAddress());
			   List<String> options = new ArrayList<String> ();
			   boolean prevAdded = false;
			   for (int i = 0; i < attributes.length; i++) {
				   if (values.get(i) != null && !values.get(i).isEmpty()) {
					   if (prevAdded) {
						   sql.append(" , ");
					   }
					   sql.append(attributes[i]);
					   sql.append(" = ? ");
					   if (attributes[i].equals("password")) {
						   options.add(md5(values.get(i)));
					   } else {
						   options.add(values.get(i));
					   }
					   prevAdded = true;
				   }
			   }
			   if (prevAdded) {
				   sql.append(", ");
			   }
			   sql.append("manager");
			   sql.append(" = ");
			   if (user.isManager()) {
				   sql.append(1);
			   } else {
				   sql.append(0);
			   }
			   sql.append(" where uid = ?");
			   PreparedStatement ps=con.prepareStatement(sql.toString());  
			   int i = 0;
			   for (i = 0; i < options.size(); i++) {
				   ps.setString(i + 1, options.get(i));
			   }
			   ps.setInt(i + 1, user.getUid());
			   ps.executeUpdate(); 
			   return null;
		   } catch (SQLException ex) {
			   return ex.getMessage();
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
		   return "Unknown Error";
	   }
	   public static List<User> searchUser(User user, boolean addManagerProperty, int limit, int offset,
			   String sort_by, AtomicInteger queryCount) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();
			   StringBuilder sql = new StringBuilder();
			   List<String> values = new ArrayList<String> ();
			   values.add(user.getUsername());
			   values.add(user.getPassword());
			   values.add(user.getEmail());
			   values.add(user.getFirstName());
			   values.add(user.getLastName());
			   values.add(user.getPhone());
			   values.add(user.getAddress());
			   List<String> options = new ArrayList<String> ();
			   boolean prevAdded = false;
			   for (int i = 0; i < attributes.length; i++) {
				   if (values.get(i) != null && !values.get(i).isEmpty()) {
					   if (prevAdded) {
						   sql.append(" and ");
					   }
					   sql.append(attributes[i]);
					   sql.append(" like ? ");
					   if (attributes[i].equals("password")) {
						   options.add(md5(values.get(i)));
					   } else {
						   options.add(values.get(i));
					   }
					   prevAdded = true;
				   }
			   }
			   if (addManagerProperty) {
				   if (prevAdded) {
					   sql.append(" and ");
				   }
				   sql.append("manager");
				   sql.append(" = ");
				   if (user.isManager()) {
					   sql.append(1);
				   } else {
					   sql.append(0);
				   }
			   }
			   StringBuilder cmd1 = new StringBuilder("Select * From User ");
			   StringBuilder cmd2 = new StringBuilder("Select count(*) From User ");
			   if (sql.length() != 0) {
				   cmd1.append(" where ");
				   cmd1.append(sql);
				   cmd2.append(" where ");
				   cmd2.append(sql);
			   }
			   if (sort_by != null) {
				   cmd1.append(" Order By ");
				   cmd1.append(sort_by);
			   }
			   if (limit != 0) {
				   cmd1.append(" limit ? ");
			   }
			   if (offset != 0) {
				   cmd1.append(" offset ? ");
			   }
			   PreparedStatement ps=con.prepareStatement(cmd1.toString());
			   PreparedStatement ps2=con.prepareStatement(cmd2.toString());  
			   int i = 0;
			   int ia = 1;
			   for (i = 0; i < options.size(); i++) {
				   ps.setString(i + ia, "%" + options.get(i) + "%");
				   ps2.setString(i + ia, "%" + options.get(i) + "%");
			   }
			   ia += i;
			   if (limit != 0) {
				   ps.setInt(ia, limit);
				   ia++;
			   }
			   if (offset != 0) {
				   ps.setInt(ia, offset);
				   ia++;
			   }
			   ResultSet set = ps.executeQuery(); 
			   List<User> pubs = new ArrayList<User>();
			   while(set.next()) {
				   pubs.add(new User(set));
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
	   public static String md5( String source ) {
		    try {
		        MessageDigest md = MessageDigest.getInstance( "MD5" );
		        byte[] bytes = md.digest( source.getBytes("UTF-8") );
		        return getString( bytes );
		    } catch( Exception e )  {
		        e.printStackTrace();
		        return null;
		    }
		}
	   
	   private static String getString( byte[] bytes ) 
	   {
	     StringBuffer sb = new StringBuffer();
	     for( int i=0; i<bytes.length; i++ )     
	     {
	        byte b = bytes[ i ];
	        String hex = Integer.toHexString((int) 0x00FF & b);
	        if (hex.length() == 1) 
	        {
	           sb.append("0");
	        }
	        sb.append( hex );
	     }
	     return sb.toString();
	   }
	   public static int total_record() {
			try {
				Connection connect = ConnectionProvider.getConnection();
				ResultSet set2 = connect.createStatement().executeQuery(
						"Select count(*) From User");
				set2.next();
				return set2.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
}
