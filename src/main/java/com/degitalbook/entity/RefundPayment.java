package com.degitalbook.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class RefundPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long refundId;
	
	private Long paymentId;
	private Double refundedAmount;
	private Date refundDate;
	private Integer statusId;
	private String  refundStatus;
	private Long readerId;
	private Long bookId;
	public Long getRefundId() {
		return refundId;
	}
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Double getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(Double refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
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
	@Override
	public String toString() {
		return "RefundPayment [refundId=" + refundId + ", paymentId=" + paymentId + ", refundedAmount=" + refundedAmount
				+ ", refundDate=" + refundDate + ", statusId=" + statusId + ", refundStatus=" + refundStatus
				+ ", readerId=" + readerId + ", bookId=" + bookId + "]";
	}
	public RefundPayment(Long refundId, Long paymentId, Double refundedAmount, Date refundDate, Integer statusId,
			String refundStatus, Long readerId, Long bookId) {
		super();
		this.refundId = refundId;
		this.paymentId = paymentId;
		this.refundedAmount = refundedAmount;
		this.refundDate = refundDate;
		this.statusId = statusId;
		this.refundStatus = refundStatus;
		this.readerId = readerId;
		this.bookId = bookId;
	}
	public RefundPayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
