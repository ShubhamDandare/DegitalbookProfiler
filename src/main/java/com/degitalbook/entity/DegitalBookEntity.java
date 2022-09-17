package com.degitalbook.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DegitalBookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String category;
	private String image;
	private double price;
	private String publisher;
	private boolean active;
	private String content;
	private String author;
	@Temporal(TemporalType.DATE)
	private Date publishdate=new Date(System.currentTimeMillis());
	private boolean isblock;
	
	
	
	public boolean isIsblock() {
		return isblock;
	}
	public void setIsblock(boolean isblock) {
		this.isblock = isblock;
	}
	public Date getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public DegitalBookEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DegitalBookEntity [id=" + id + ", title=" + title + ", category=" + category + ", image=" + image
				+ ", price=" + price + ", publisher=" + publisher + ", active=" + active + ", content=" + content
				+ ", author=" + author + ", publishdate=" + publishdate + ", isblock=" + isblock + "]";
	}
	public DegitalBookEntity(long id, String title, String category, String image, double price, String publisher,
			boolean active, String content, String author, Date publishdate, boolean isblock) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.image = image;
		this.price = price;
		this.publisher = publisher;
		this.active = active;
		this.content = content;
		this.author = author;
		this.publishdate = publishdate;
		this.isblock = isblock;
	}
	
	
	
	

}
