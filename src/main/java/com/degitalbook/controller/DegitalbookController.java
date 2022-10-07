package com.degitalbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.degitalbook.Repository.DegitalbookPaymentRepository;
import com.degitalbook.Service.DegitalBookService;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;
import com.degitalbook.entity.Payment;
import com.degitalbook.entity.RefundPayment;
import com.degitalbook.entity.UpdateBookRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/degitalbook")
public class DegitalbookController {

	@Autowired
	private DegitalBookService service;

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/{id}")
	public DegitalBookEntity getbookByid(@PathVariable long id) {
		DegitalBookEntity degitalbookById = service.getDegitalbookById(id);
		return degitalbookById;
	}

	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PostMapping("/save")
	public DegitalBookEntity createBookEntity(@RequestBody DegitalBookEntity entity) {
		DegitalBookEntity saveDegitalbook = service.saveDegitalbook(entity);
		return saveDegitalbook;
	}

	@GetMapping("/searchbook")
	public List<DegitalBookEntity> SearchBooks(@RequestParam String category, @RequestParam String author,
			@RequestParam double price) {

		List<DegitalBookEntity> searchDegitalbook = service.searchDegitalbook(category, author, price);
		Map<String, String> map = new HashMap<>();
		searchDegitalbook.forEach(degitalbook -> {
			map.put("Author", degitalbook.getAuthor());
			map.put("Title", degitalbook.getTitle());
			map.put("Publisher", degitalbook.getPublisher());
			map.put("Price", String.valueOf(degitalbook.getPrice()));
			map.put("PublishedDate", String.valueOf(degitalbook.getPublishdate()));
			map.put("Category", degitalbook.getCategory());
		});

	//	return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		return searchDegitalbook;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@PostMapping("/buybook")
	public ResponseEntity<Map<String, Long>> buyDegitalBook(@RequestBody BuyBookRequest buybook) {
		ResponseEntity<Map<String, Long>> buyDegitalBook = service.buyDegitalBook(buybook);
		return buyDegitalBook;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/purchasebooks/{email}")
	public ResponseEntity<Map<String, Set<Long>>> findAllPurchaseBook(@PathVariable String email) {
		return service.findAllPurchaseBook(email);

	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/readbook/{email}/{bookId}")
	public ResponseEntity<Map<String, String>> readBooks(@PathVariable("email") String email,
			@PathVariable("bookId") Long bookId) {
		Map<String, String> readerReadBook = service.readerReadBook(email, bookId);
		ResponseEntity entity = new ResponseEntity(readerReadBook, HttpStatus.OK);
		return entity;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/getPurchaseBookByPaymentId/{email}")
	public ResponseEntity getPurchaseBookByPaymentId(@PathVariable String email, @RequestParam Long paymentId) {
		Map<String, String> purchaseBookByPaymentId = service.getPurchaseBookByPaymentId(email, paymentId);
		ResponseEntity entity = new ResponseEntity(purchaseBookByPaymentId, HttpStatus.OK);
		return entity;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@PostMapping("refund/{email}/{bookId}")
	public ResponseEntity RefundPaymentByBookId(@PathVariable String email, @PathVariable Long bookId,
			@RequestBody RefundPayment refund) {
		Map<String, String> refundPaymentByBookId = service.RefundPaymentByBookId(email, bookId, refund);
		ResponseEntity entity = new ResponseEntity(refundPaymentByBookId, HttpStatus.OK);
		return entity;

	}

//	@PreAuthorize("hasRole('ROLE_READER')")
//	@GetMapping("/getallpurchaseBook/")
//	public List<Payment> viewAllpurchaseBook(@PathVariable Long payId) {
//		List<Payment> findAll = paymentRepo.findAll();
//		return findAll;
//	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/getallbooks")
	public List<DegitalBookEntity> getAllBookEntity() {
		List<DegitalBookEntity> saveDegitalbook = service.getAllDegitalbook();
		return saveDegitalbook;
	}

	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PutMapping("/updatebook/{id}")
	public ResponseEntity<DegitalBookEntity> updateBookEntity(@PathVariable long id, @RequestBody DegitalBookEntity entity) {
		ResponseEntity<DegitalBookEntity> updateDegitalbook = service.updateDegitalbook(id, entity);

		return updateDegitalbook;

	}

	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@DeleteMapping("/deletebook/{id}")
	public String deletebookByid(@PathVariable Long id) {
		String deleteDegitalbookbyId = service.deleteDegitalbookbyId(id);
		return deleteDegitalbookbyId;
	}

}
