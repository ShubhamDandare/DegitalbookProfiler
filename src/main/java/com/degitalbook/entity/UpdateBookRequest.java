package com.degitalbook.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class UpdateBookRequest {
	private Long id;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}
	public boolean isIsblock() {
		return isblock;
	}
	public void setIsblock(boolean isblock) {
		this.isblock = isblock;
	}
	@Override
	public String toString() {
		return "UpdateBookRequest [id=" + id + ", title=" + title + ", category=" + category + ", image=" + image
				+ ", price=" + price + ", publisher=" + publisher + ", active=" + active + ", content=" + content
				+ ", author=" + author + ", publishdate=" + publishdate + ", isblock=" + isblock + "]";
	}
	
}
