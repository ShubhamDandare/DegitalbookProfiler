package com.degitalbook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.degitalbook.Service.DegitalBookService;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;
import com.degitalbook.entity.DegitalbookUser;
import com.degitalbook.entity.Payment;
import com.degitalbook.entity.RefundPayment;

@SpringBootTest
public class DegitalbookControllerTest {

	@Mock
	public DegitalBookService bookService;

	@InjectMocks
	public DegitalbookController bookController;

	@Test
	public void saveBookTest() {

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");
		when(bookService.saveDegitalbook(entity)).thenReturn(entity);
		DegitalBookEntity createBookEntity = bookController.createBookEntity(entity);
		assertThat(createBookEntity.getId()).isEqualTo(1);

	}

	@Test
	public void searchBook() {

		DegitalBookEntity book = new DegitalBookEntity();
		book.setCategory("Motivation");
		book.setAuthor("Rexx");
		book.setPrice(1234.43);

		List<DegitalBookEntity> entity = new ArrayList<>();
		entity.add(book);

		when(bookService.searchDegitalbook("Motivation", "Rexx", 1234.43)).thenReturn(entity);
		ResponseEntity<Object> searchBooks = bookController.SearchBooks("Motivation", "Rexx", 1234.43);
		HashMap<String, String> map = (HashMap<String, String>) searchBooks.getBody();
		String category = map.get("Category");
		assertThat(category.equals("Motivation"));

	}

	@Test
	public void buyBookTest() {

		BuyBookRequest request = new BuyBookRequest();
		request.setBookid(1L);
		request.setEmail("2mail.com");
		request.setUsername("asd");

		Map<String, Long> map = new HashMap<>();

		map.put("PaymentId", 12345L);
		map.put("bookid", 1L);

		ResponseEntity<Map<String, Long>> entity = new ResponseEntity(map, HttpStatus.OK);
		when(bookService.buyDegitalBook(request)).thenReturn(entity);
		ResponseEntity<Map<String, Long>> book = bookController.buyDegitalBook(request);
		Map<String, Long> body = book.getBody();

		assertThat(body.get("bookid").equals(1L));

	}

	@Test
	public void readBookTest() {

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setCategory("eee");
		entity.setContent("aaa");
		entity.setPublisher("qqq");
		entity.setAuthor("asd");
		entity.setId(1L);

		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");

		Map<String, String> map = new HashMap<>();

		map.put("category", entity.getCategory());
		map.put("content", entity.getContent());
		map.put("publisher", entity.getPublisher());
		map.put("author", entity.getAuthor());
		map.put("bookid", String.valueOf(entity.getId()));

		when(bookService.readerReadBook("123@gmail.com", 1L)).thenReturn(map);

		ResponseEntity<Map<String, String>> readBooks = bookController.readBooks("123@gmail.com", 1L);
		Map<String, String> asd = readBooks.getBody();
		assertThat(asd.get("bookid").equals("1L"));

	}

	@Test
	public void getPurchaseBookByPaymentIdTest() {
		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1L);
		entity.setTitle("Smile");
		entity.setAuthor("shubham");
		entity.setCategory("happiness");
		entity.setContent("laugh");

		Map<String, String> map = new HashMap<>();
		map.put("bookid", String.valueOf(entity.getId()));
		map.put("title", entity.getTitle());
		map.put("author", entity.getAuthor());
		map.put("category", entity.getCategory());
		map.put("content", entity.getContent());

		Payment pay = new Payment();
		pay.setId(1L);
		pay.setBookId(1L);
		pay.setReaderId(1L);
		pay.setPrice(1232.33);

		when(bookService.getPurchaseBookByPaymentId("123@gmail.com", 1L)).thenReturn(map);
		ResponseEntity<Map<String, String>> purchaseBookByPaymentId = bookController
				.getPurchaseBookByPaymentId("123@gmail.com", 1L);
		Map<String, String> body = purchaseBookByPaymentId.getBody();
		String id = body.get("bookid");
		assertThat(id.equals("1L"));

	}

	@Test
	public void RefundPaymentByBookIdTest() {

		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");
		user.setId(1L);

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1L);
		entity.setTitle("Smile");
		entity.setAuthor("shubham");
		entity.setCategory("happiness");
		entity.setContent("laugh");
		entity.setPrice(1234.22);

		Map<String, String> map = new HashMap<>();
		map.put("bookid", String.valueOf(entity.getId()));
		map.put("title", entity.getTitle());
		map.put("author", entity.getAuthor());
		map.put("category", entity.getCategory());
		map.put("content", entity.getContent());
		map.put("Price", String.valueOf(entity.getPrice()));

		RefundPayment refund = new RefundPayment();
		refund.setBookId(1L);
		refund.setRefundId(1L);
		refund.setPaymentId(1L);
		refund.setReaderId(1L);
		refund.setRefundDate(new Date(System.currentTimeMillis()));
		refund.setRefundedAmount(1234.22);

		when(bookService.RefundPaymentByBookId("123@gmail.com", 1L, refund)).thenReturn(map);
		ResponseEntity<Map<String, String>> refundPaymentByBookId = bookController
				.RefundPaymentByBookId("123@gmail.com", 1L, refund);
		Map<String, String> str = refundPaymentByBookId.getBody();
		assertThat(str.get("bookid").equals("1L"));
	}

}
