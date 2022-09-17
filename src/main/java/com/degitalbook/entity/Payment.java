package com.degitalbook.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long readerId;
	private Long bookId;
	private double price;
	private Date paymentdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReaderId() {
		return readerId;
	}

	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", readerId=" + readerId + ", bookId=" + bookId + ", price=" + price
				+ ", paymentdate=" + paymentdate + "]";
	}

	public Payment(Long id, Long readerId, Long bookId, double price, Date paymentdate) {
		super();
		this.id = id;
		this.readerId = readerId;
		this.bookId = bookId;
		this.price = price;
		this.paymentdate = paymentdate;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

}
