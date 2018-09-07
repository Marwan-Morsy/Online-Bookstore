package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Book {
	private Long isbn;
	private String title;
	private Date publicationDate;
	private Integer threshold;
	private Float price;
	private Integer copiesNumber;
	private Integer cid;
	private Integer pid;
	public Book(Long isbn, String title, Date publicationDate, Integer threshold,
			Float price, Integer copies, Integer cid, Integer pid) {
		this.isbn = isbn;
		this.title = title;
		this.publicationDate = publicationDate;
		this.threshold = threshold;
		this.price = price;
		this.copiesNumber = copies;
		this.cid = cid;
		this.pid = pid;
	}
	public Book(ResultSet set) {
		try {
			GregorianCalendar calender = new GregorianCalendar();
			calender.setLenient(true);
			this.isbn = set.getLong(1);
			this.title = set.getString(2);
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(set.getString(3));
			this.publicationDate = new Date(utilDate.getTime());
			//System.out.println(set.getString(3) +" >>> "+ utilDate.toString() + " === " + this.publicationDate.toString());
			this.threshold = set.getInt(4);
			this.price = set.getFloat(5);
			this.copiesNumber = set.getInt(6);
			this.cid = set.getInt(7);
			this.pid = set.getInt(8);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(isbn);
		}
		
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getCopiesNumber() {
		return copiesNumber;
	}

	public void setCopiesNumber(Integer copiesNumber) {
		this.copiesNumber = copiesNumber;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
}
