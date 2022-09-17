package com.degitalbook.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.degitalbook.Repository.DegitalbookPaymentRepository;
import com.degitalbook.Repository.DegitalbookRepository;
import com.degitalbook.Repository.DegitalbookUserRepository;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;
import com.degitalbook.entity.DegitalbookUser;
import com.degitalbook.entity.Payment;

@Service
public class DegitalBookService {

	@Autowired
	private DegitalbookRepository repository;

	@Autowired
	private DegitalbookUserRepository userRepository;

	@Autowired
	private DegitalbookPaymentRepository paymentRepository;

	public DegitalBookEntity saveDegitalbook(DegitalBookEntity bookEntity) {
		DegitalBookEntity save = repository.save(bookEntity);
		return save;
	}

	public DegitalBookEntity updateDegitalbook(long id, DegitalBookEntity bookEntity) {
		DegitalBookEntity getbookbyID = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));
		getbookbyID.setId(bookEntity.getId());
		getbookbyID.setCategory(bookEntity.getCategory());
		getbookbyID.setTitle(bookEntity.getTitle());
		getbookbyID.setContent(bookEntity.getContent());
		getbookbyID.setImage(bookEntity.getImage());
		getbookbyID.setPrice(bookEntity.getPrice());
		getbookbyID.setPublisher(bookEntity.getPublisher());
		getbookbyID.setAuthor(bookEntity.getAuthor());
		DegitalBookEntity save = repository.save(getbookbyID);
		return save;
	}

	public DegitalBookEntity getDegitalbookById(long id) {
		DegitalBookEntity getbookbyID = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));
		return getbookbyID;
	}

	public List<DegitalBookEntity> getAllDegitalbook() {
		List<DegitalBookEntity> getbookbyID = repository.findAll();
		return getbookbyID;
	}

	public DegitalBookEntity deleteDegitalbookbyId(long id) {
		DegitalBookEntity getbookbyID = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));
		if (getbookbyID != null)
			repository.deleteById(id);
		DegitalBookEntity save = repository.save(getbookbyID);
		return save;
	}

	public List<DegitalBookEntity> searchDegitalbook(String category, String author, double price) {
		List<DegitalBookEntity> findByCategoryAndAuthorAndPrice = repository.findByCategoryAndAuthorAndPrice(category,
				author, price);

		return findByCategoryAndAuthorAndPrice;

	}

	public Boolean isUserAvailable(String userName) {
		Boolean isUserAvailable = userRepository.existsByUsername(userName);
		return isUserAvailable;
	}

	public Boolean isBookAvailable(Long bookId) {
		Boolean isBookAvaiable = repository.existsById(bookId);
		return isBookAvaiable;
	}

	public Optional<DegitalbookUser> getUserbyName(String username) {
		Optional<DegitalbookUser> findByUsername = userRepository.findByUsername(username);
		return findByUsername;
	}

	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);

	}

	public Boolean isuserAvailableByEmail(String email) {
		return userRepository.existsByEmail(email);

	}

	public Optional<DegitalbookUser> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Boolean isPaymentAvailableForReaderId(Long readerId) {
		return paymentRepository.existsByReaderId(readerId);
	}

	public Map<String, Set<Long>> getReaderBookId(Long id) {
		List<Payment> findAllByreaderId = paymentRepository.findAllByreaderId(id);
		Set<Long> uniquebooklist = new HashSet<>();
		Map<String, Set<Long>> map = new HashMap<String, Set<Long>>();
		findAllByreaderId.forEach(book -> {
			uniquebooklist.add(book.getBookId());
		});
		map.put("BookID", uniquebooklist);
		return map;
	}

	public ResponseEntity<Map<String, Long>> buyDegitalBook(BuyBookRequest buybook) {
		Map<String, Long> map = new HashMap<>();
		Boolean userAvailable = isUserAvailable(buybook.getUsername());
		Boolean bookAvailable = isBookAvailable(buybook.getBookid());
		if (userAvailable && bookAvailable) {

			DegitalBookEntity degitalbook = getDegitalbookById(buybook.getBookid());
			Optional<DegitalbookUser> userbyName = getUserbyName(buybook.getUsername());
			Payment payment = new Payment();

			DegitalbookUser degitalbookUser = userbyName.get();

			payment.setBookId(degitalbook.getId());
			payment.setPaymentdate(degitalbook.getPublishdate());
			payment.setReaderId(degitalbookUser.getId());
			payment.setPrice(degitalbook.getPrice());

			Payment savePayment = savePayment(payment);
			map.put("PaymentId", savePayment.getId());
			map.put("BookId", savePayment.getBookId());

		}
		ResponseEntity<Map<String, Long>> request = new ResponseEntity<Map<String, Long>>(map, HttpStatus.OK);
		return request;
	}

	public ResponseEntity findAllPurchaseBook(String email) {
		Optional<DegitalbookUser> findByEmail = userRepository.findByEmail(email);
		DegitalbookUser degitalbookUser = findByEmail.get();
		Boolean paymentAvailableForReaderId = isPaymentAvailableForReaderId(degitalbookUser.getId());
		if (paymentAvailableForReaderId) {
		}
		Map<String, Set<Long>> readerBookId = getReaderBookId(degitalbookUser.getId());
		ResponseEntity entity = new ResponseEntity(readerBookId, HttpStatus.OK);
		return entity;

	}

	public Map<String, String> readerReadBook(String email, Long bookId) {
		Boolean isuserAvailableByEmail = isuserAvailableByEmail(email);
		Map<String, String> map = new HashMap<>();
		if (isuserAvailableByEmail) {
			DegitalBookEntity degitalbookById = getDegitalbookById(bookId);
			map.put("author", degitalbookById.getAuthor());
			map.put("category", degitalbookById.getCategory());
			map.put("content", degitalbookById.getContent());
			map.put("publisher", degitalbookById.getPublisher());

		}
		return map;
	}

}
