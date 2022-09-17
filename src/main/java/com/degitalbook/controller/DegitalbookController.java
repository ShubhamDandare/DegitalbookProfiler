package com.degitalbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.degitalbook.Service.DegitalBookService;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "/*", maxAge = 3600)
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

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/searchbook")
	public ResponseEntity<Object> SearchBooks(@RequestParam String catagory, @RequestParam String author,
			@RequestParam double price) {

		List<DegitalBookEntity> searchDegitalbook = service.searchDegitalbook(catagory, author, price);
		Map<String, String> map = new HashMap<>();
		searchDegitalbook.forEach(degitalbook -> {
			map.put("Author", degitalbook.getAuthor());
			map.put("Title", degitalbook.getTitle());
			map.put("Publisher", degitalbook.getPublisher());
			map.put("Price", String.valueOf(degitalbook.getPrice()));
			map.put("PublishedDate", String.valueOf(degitalbook.getPublishdate()));
			map.put("Category", degitalbook.getCategory());
		});

		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@PostMapping("/buybook")
	public ResponseEntity<Map<String, Long>> buyDegitalBook(@RequestBody BuyBookRequest buybook) {
		ResponseEntity<Map<String, Long>> buyDegitalBook = service.buyDegitalBook(buybook);
		return buyDegitalBook;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/purchasebooks/{email}")
	public ResponseEntity findAllPurchaseBook(@PathVariable String email) {
		return service.findAllPurchaseBook(email);

	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/readers/{email}/books/{bookId}")
	public ResponseEntity readBooks(@PathVariable("email") String email, @PathVariable("bookId") Long bookId) {
		Map<String, String> readerReadBook = service.readerReadBook(email, bookId);
		ResponseEntity entity = new ResponseEntity(readerReadBook, HttpStatus.OK);
		return entity;
	}

	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/getallbooks")
	public List<DegitalBookEntity> getAllBookEntity() {
		List<DegitalBookEntity> saveDegitalbook = service.getAllDegitalbook();
		return saveDegitalbook;
	}

	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PutMapping("/{id}")
	public DegitalBookEntity updateBookEntity(@PathVariable long id, @RequestBody DegitalBookEntity entity) {
		DegitalBookEntity saveDegitalbook = service.updateDegitalbook(id, entity);
		return saveDegitalbook;

	}

	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@DeleteMapping("/{id}")
	public DegitalBookEntity deletebookByid(@PathVariable long id) {
		DegitalBookEntity degitalbookById = service.deleteDegitalbookbyId(id);
		return degitalbookById;
	}

}
