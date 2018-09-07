package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Author;
import models.Book;
import models.Category;
import models.Publisher;
import provider.ConnectionProvider;

public class BookDataDAO {
	
	static final String[] attributes = {"ISBN", "Title", "PublicationDate", "Threshold", "Price", "CopiesNumber",
			"cid", "pid"};
	public static Book findBook(Long isbn) {
		   try{  
			   Connection con= ConnectionProvider.getConnection();     
			   PreparedStatement ps=con.prepareStatement(  
			       "select * from Book where isbn=?");  
			   
			   ps.setLong(1, isbn);  
			   ResultSet rs=ps.executeQuery(); 
			   
			   if(rs.next()) {
				   Book u = new Book(rs);
				   return u;
			   } else {
				   return null;
			   }
			                 
		   }catch(Exception e){
			   e.printStackTrace();
		   }  
	      
	      return null;
   }
	public static List<Book> selectBookQuery(Book book, Float pr1, Float pr2, List<List<Author>> authors, List<Category> cids, List<Publisher> pids,
			int limit, int offset, AtomicInteger queryCount, String sort_attr, boolean hasStock) {
		return selectBookQuery(book, pr1, pr2, authors, cids, pids,
				limit, offset, queryCount, sort_attr, null, null, hasStock?1:0, null);
	}
	public static List<Book> selectBookQuery(Book book, Float pr1, Float pr2, List<List<Author>> authors, List<Category> cids, List<Publisher> pids,
			int limit, int offset, AtomicInteger queryCount, String sort_attr,
			Integer lt, Integer ht, Integer lc, Integer hc) {
		try {
			Connection connect = ConnectionProvider.getConnection();
			boolean sauthor = authors != null && !authors.isEmpty();
			boolean scategory = cids != null && !cids.isEmpty();
			boolean spublisher = pids != null && !pids.isEmpty();
			StringBuilder sb2 = new StringBuilder();
			List<Integer> iOptions = new ArrayList<Integer>();
			List<Float> fOptions = new ArrayList<Float>();
			if (sort_attr != null && sort_attr.contains("Category.name")) {
				sb2.append("(");
			}
			if (sort_attr != null && sort_attr.contains("Publisher.name")) {
				sb2.append("(");
			}
			if (sauthor) {
				sb2.append("(Book natural join AuthoredBy natural join Author) ");
			} else {
				sb2.append(" Book ");
			}
			if (sort_attr != null && sort_attr.contains("Category.name")) {
				sb2.append(" inner join Category on Category.cid = Book.cid)");
			}
			if (sort_attr != null && sort_attr.contains("Publisher.name")) {
				sb2.append(" inner join Publisher on Publisher.pid = Book.pid)");
			}
			StringBuilder sb = new StringBuilder();
			sb.append(" where ");
			boolean prev_cond = false;
			if (book.getIsbn() != null) {
				sb.append("ISBN = ? ");
				prev_cond = true;
			}
			if (book.getCopiesNumber() != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("CopiesNumber = ? ");
				iOptions.add(book.getCopiesNumber());
				prev_cond = true;
			}
			if (book.getThreshold() != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("threshold = ? ");
				iOptions.add(book.getThreshold());
				prev_cond = true;
			}
			if (lt != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append(" threshold >= ? ");
				iOptions.add(lt);
				prev_cond = true;
			}
			if (ht != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append(" threshold <= ? ");
				iOptions.add(ht);
				prev_cond = true;
			}
			if (lc != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append(" copiesNumber >= ? ");
				iOptions.add(lc);
				prev_cond = true;
			}
			if (hc != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append(" copiesNumber <= ? ");
				iOptions.add(hc);
				prev_cond = true;
			}
			if (pr1 != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("price >= ?");
				fOptions.add(pr1);
				prev_cond = true;
			}
			if (pr2 != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("price <= ?");
				fOptions.add(pr2);
				prev_cond = true;
			}
			if (book.getTitle() != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("title like ? ");
				prev_cond = true;
			}
			if (book.getPublicationDate() != null) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("PublicationDate = ? ");
				prev_cond = true;
			}
			if (scategory) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("(");
				for (int i = 0; i < cids.size(); i++) {
					if (i != 0) {
						sb.append(" or ");
					}
					sb.append("Book.cid = ");
					sb.append((int)cids.get(i).getCid());
				}
				sb.append(")");
				prev_cond = true;
			}
			if (spublisher) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append("(");
				for (int i = 0; i < pids.size(); i++) {
					if (i != 0) {
						sb.append(" or ");
					}
					sb.append("Book.pid = ");
					sb.append((int)pids.get(i).getPid());
				}
				sb.append(")");
				prev_cond = true;
			}
			if (sauthor) {
				if (prev_cond) {
					sb.append(" and ");
				}
				sb.append(" isbn in ");
				sb.append("(");
				sb.append("Select Distinct ISBN From (");
				boolean prev_in_cond = false;
				for (int j = 0; j < authors.size(); j++) {
					if (!authors.get(j).isEmpty()) {
						if (prev_in_cond) {
							sb.append(" natural join ");
						}
						sb.append("( select Distinct ISBN from (AuthoredBy natural join Author) where ");
						for (int i = 0; i < authors.get(j).size(); i++) {
							if (i != 0) {
								sb.append(" or ");
							}
							sb.append("name like \'%");
							sb.append(authors.get(j).get(i).getName());
							sb.append("%\'");
						}
						sb.append(") as a");
						sb.append(j);
						prev_in_cond = true;
					}
				}
				sb.append(")");
				sb.append(")");
				prev_cond = true;
			}
			if (!sb.toString().equals(" where ")) {
				sb2.append(sb);
			}
			StringBuilder sb3 = new StringBuilder("Select count(DISTINCT ISBN) From ");
			sb3.append(sb2);
			if (sort_attr != null) {
				sb2.append(" Order by ");;
				sb2.append(sort_attr);
				sb2.append(" ");
			}
			if (limit != 0) {
				sb2.append(" limit ? ");
			}
			if (offset != 0) {
				sb2.append(" offset ? ");
			}
			StringBuilder sb1 = new StringBuilder("SELECT Distinct ISBN, "
					+ "Title,PublicationDate,Threshold,"
					+ "Price,CopiesNumber,Book.CID,Book.PID ");
			if (sort_attr != null && sort_attr.contains("Category.name")) {
				sb1.append(",Category.name");
			}
			if (sort_attr != null && sort_attr.contains("Publisher.name")) {
				sb1.append(",Publisher.name");
			}
			sb1.append(" From ");
			sb1.append(sb2);
			PreparedStatement ps1 = connect.prepareStatement(sb1.toString());
			PreparedStatement ps2 = connect.prepareStatement(sb3.toString());
			int i = 0;
			int ia = 0;
			if (book.getIsbn() != null) {
				ps1.setLong(ia + 1, book.getIsbn());
				ps2.setLong(ia + 1, book.getIsbn());
				ia++;
			}
			for (i = 0; i < iOptions.size(); i++) {
				ps1.setInt(ia + i + 1, iOptions.get(i));
				ps2.setInt(ia + i + 1, iOptions.get(i));
			}
			ia += i;
			for (i = 0; i < fOptions.size(); i++) {
				ps1.setFloat(ia + i + 1, fOptions.get(i));
				ps2.setFloat(ia + i + 1, fOptions.get(i));
			}
			ia += i + 1;
			if (book.getTitle() != null) {
				ps1.setString(ia, "%" + book.getTitle() + "%");
				ps2.setString(ia, "%" + book.getTitle() + "%");
				ia++;
				
			}
			if (book.getPublicationDate() != null) {
				ps1.setDate(ia , book.getPublicationDate());
				ps2.setDate(ia , book.getPublicationDate());
				ia++;
			}
			if (limit != 0) {
				ps1.setInt(ia, limit);
				ia++;
			}
			if (offset != 0) {
				ps1.setInt(ia, offset);
			}
			ResultSet set = ps1.executeQuery();
			List<Book> books = new ArrayList<Book> ();
			ResultSet set2 = ps2.executeQuery();
			set2.next();
			queryCount.set(set2.getInt(1));
			while (set.next()) {
				books.add(new Book(set));
			}
			return books;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	public static List<Book> searchFirstMatchBook(String title) {
		try{  
		   Connection con= ConnectionProvider.getConnection();     
		   PreparedStatement ps=con.prepareStatement(  
		       "select * from Book where title like ? limit 10");  
		   List<Book> books = new ArrayList<Book> ();
		   ps.setString(1, title + "%");  
		   ResultSet set = ps.executeQuery(); 
		   
		   while (set.next()) {
				books.add(new Book(set));
			}
			return books;
		                 
	   }catch(Exception e){
		   e.printStackTrace();
	   }  
      
      return null;
	}
	public static int total_record() {
		try {
			Connection connect = ConnectionProvider.getConnection();
			ResultSet set2 = connect.createStatement().executeQuery(
					"Select count(*) From Book");
			set2.next();
			return set2.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String updateBook (Book oldBook, Book book, List<Author> oldAuthors,
			List<Author> newAuthors) throws SQLException {
		Connection connect = ConnectionProvider.getConnection();
		try {
			StringBuilder sb = new StringBuilder("UPDATE Book SET ");
			boolean prev_cond = false;
			List<Integer> iOptions = new ArrayList<Integer>();
			if (book.getIsbn() != null) {
				sb.append("ISBN = ?");
				prev_cond = true;
			}
			if (book.getTitle() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("title = ?");
				prev_cond = true;
			}
			if (book.getPublicationDate() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("PublicationDate = ? ");
				prev_cond = true;
			}
			if (book.getPrice() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("price = ? ");
				prev_cond = true;
			}
			if (book.getThreshold() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("threshold = ? ");
				iOptions.add(book.getThreshold());
				prev_cond = true;
			}
			if (book.getCopiesNumber() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("CopiesNumber = ? ");
				iOptions.add(book.getCopiesNumber());
				prev_cond = true;
			}
			if (book.getCid() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("Cid = ? ");
				iOptions.add(book.getCid());
				prev_cond = true;
			}
			if (book.getPid() != null) {
				if (prev_cond) {
					sb.append(" , ");
				}
				sb.append("pid = ? ");
				iOptions.add(book.getPid());
				prev_cond = true;
			}
			sb.append("WHERE ISBN = ?");
			PreparedStatement ps = connect.prepareStatement(sb.toString());
			int ia = 1;
			if (book.getIsbn() != null) {
				ps.setLong(ia, book.getIsbn());
				ia++;
			}
			if (book.getTitle() != null) {
				ps.setString(ia, book.getTitle());
				ia++;
			}
			if (book.getPublicationDate() != null) {
				ps.setDate(ia, book.getPublicationDate());
				ia++;
			}
			if (book.getPrice() != null) {
				ps.setFloat(ia, book.getPrice());
				ia++;
			}
			int i;
			for (i = 0; i < iOptions.size(); i++) {
				ps.setInt(ia + i, iOptions.get(i));
			}
			ia+=i;
			ps.setLong(ia, oldBook.getIsbn());
			connect.setAutoCommit(false);
			if (oldAuthors != null) {
				for (int j = 0; j < oldAuthors.size(); j++) {
					deleteAuthorConnection(oldBook.getIsbn(), oldAuthors.get(j).getAid(), connect);
				}
			}
			ps.executeUpdate();
			if (newAuthors != null) {
				for (int j = 0; j < newAuthors.size(); j++) {
					addAuthorConnection(book.getIsbn(), newAuthors.get(j).getAid(), connect);
				}
			}
			connect.commit();
			connect.setAutoCommit(true);
			return null;
		} catch (SQLException ex) {
			connect.rollback();
			connect.setAutoCommit(true);
			return ex.getMessage();
	   }catch(Exception e){
		   e.printStackTrace();
		   connect.rollback();
	   }   finally {
		   connect.setAutoCommit(true);
	   }
	   return "Unknown Error";
	}
	
	private static String deleteAuthorConnection(Long isbn, Integer aid, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("Delete From AuthoredBy where isbn = ? and aid = ?");
			ps.setLong(1, isbn);
			ps.setInt(2, aid);
			ps.executeUpdate();
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	private static String addAuthorConnection(Long isbn, Integer aid, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("Insert Into AuthoredBy(ISBN, AID) values( ? ,?)");
			ps.setLong(1, isbn);
			ps.setInt(2, aid);
			ps.executeUpdate();
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	public static String addBook(Book book) throws SQLException {
		return addBook(book, null);
	}
	public static String addBook(Book book, List<Author> authors) throws SQLException {
		Connection con= ConnectionProvider.getConnection();
		try{  
			   
			   con.setAutoCommit(false);
			   PreparedStatement ps=con.prepareStatement(  
			       "insert into Book (ISBN, Title, PublicationDate, Threshold, Price, CopiesNumber, Cid, Pid)\n" + 
			       "	values(?, ?, ?, ?, ?, ?,\n" + 
			       "		?, ?);");  
			   
			   ps.setLong(1, book.getIsbn());
			   ps.setString(2, book.getTitle());
			   ps.setDate(3, book.getPublicationDate());
			   ps.setInt(4, book.getThreshold());
			   ps.setFloat(5, book.getPrice());
			   ps.setInt(6, book.getCopiesNumber());
			   ps.setInt(7, book.getCid());
			   ps.setInt(8, book.getPid());
			   ps.executeUpdate(); 
			   if (authors != null) {
				   for (int i = 0; i < authors.size(); i++) {
					   BookDataDAO.addAuthorConnection(book.getIsbn(), authors.get(i).getAid(), con);
				   }
			   }
			   con.commit();
			   con.setAutoCommit(true);
			   return null;
		   } catch (SQLException ex) {
			   con.rollback();
			   con.setAutoCommit(true);
			   return ex.getMessage();
		   }catch(Exception e){
			   con.rollback();
			   con.setAutoCommit(true);
			   e.printStackTrace();
		   }
		   return "Unknown Error";
	}
}
