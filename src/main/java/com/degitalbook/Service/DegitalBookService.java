package com.degitalbook.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
import com.degitalbook.Repository.RefundRepository;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;
import com.degitalbook.entity.DegitalbookUser;
import com.degitalbook.entity.Payment;
import com.degitalbook.entity.RefundPayment;
import com.degitalbook.entity.UpdateBookRequest;

@Service
public class DegitalBookService {

	@Autowired
	private DegitalbookRepository bookRepository;

	@Autowired
	private DegitalbookUserRepository userRepository;

	@Autowired
	private DegitalbookPaymentRepository paymentRepository;

	@Autowired
	private RefundRepository refundRepo;

	public DegitalBookEntity saveDegitalbook(DegitalBookEntity bookEntity) {
		DegitalBookEntity save = bookRepository.save(bookEntity);
		return save;
	}

	public ResponseEntity updateDegitalbook(long id, UpdateBookRequest bookEntity) {

		DegitalBookEntity getbookbyID = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));

		getbookbyID.setCategory(bookEntity.getCategory());
		getbookbyID.setTitle(bookEntity.getTitle());
		getbookbyID.setContent(bookEntity.getContent());
		getbookbyID.setImage(bookEntity.getImage());
		getbookbyID.setPrice(bookEntity.getPrice());
		getbookbyID.setPublisher(bookEntity.getPublisher());
		getbookbyID.setAuthor(bookEntity.getAuthor());
		getbookbyID.setId(bookEntity.getId());
		DegitalBookEntity save = bookRepository.save(getbookbyID);
		ResponseEntity entity = new ResponseEntity(save, HttpStatus.OK);

		return entity;
	}

	public DegitalBookEntity getDegitalbookById(long id) {
		DegitalBookEntity getbookbyID = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));
		return getbookbyID;
	}

	public List<DegitalBookEntity> getAllDegitalbook() {
		List<DegitalBookEntity> getbookbyID = bookRepository.findAll();
		return getbookbyID;
	}

	public String deleteDegitalbookbyId(Long id) {
		DegitalBookEntity getbookbyID = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("degital book not found" + id));
		bookRepository.delete(getbookbyID);
		return "book deleted successfully.......";
	}

	public List<DegitalBookEntity> searchDegitalbook(String category, String author, double price) {
		List<DegitalBookEntity> findByCategoryAndAuthorAndPrice = bookRepository
				.findByCategoryAndAuthorAndPrice(category, author, price);

		return findByCategoryAndAuthorAndPrice;

	}

	public Boolean isUserAvailable(String userName) {
		Boolean isUserAvailable = userRepository.existsByUsername(userName);
		return isUserAvailable;
	}

	public Boolean isBookAvailable(Long bookId) {
		Boolean isBookAvaiable = bookRepository.existsById(bookId);
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
			DegitalbookUser degitalbookUser = getUserbyName(buybook.getUsername())
					.orElseThrow(() -> new RuntimeException("Username Not Found"));
			Payment payment = new Payment();

			// DegitalbookUser degitalbookUser = userbyName.get();

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
		DegitalbookUser degitalbookUser = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Email not Found"));
		// DegitalbookUser degitalbookUser = findByEmail.get();
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

	public Map<String, String> getPurchaseBookByPaymentId(String email, Long paymentId) {
		Boolean isuserAvailableByEmail = isuserAvailableByEmail(email);
		Map<String, String> map = new HashMap<>();
		if (isuserAvailableByEmail) {
			Payment payment = paymentRepository.findById(paymentId)
					.orElseThrow(() -> new RuntimeException("payment ID NOT Found :" + paymentId));

			Long bookId = payment.getBookId();
			DegitalBookEntity book = bookRepository.findById(bookId)
					.orElseThrow(() -> new RuntimeException("Book ID NOT Found In Payment :" + bookId));

			map.put("author", book.getAuthor());
			map.put("title", book.getTitle());
			map.put("price", String.valueOf(book.getPrice()));
			map.put("publisher", book.getPublisher());
			map.put("content", book.getContent());
			map.put("publishdate", String.valueOf(book.getPublishdate()));

		}
		return map;
	}

	public Map<String, String> RefundPaymentByBookId(String email, Long bookId, RefundPayment refund) {
		Map<String, String> map = new HashMap<>();
		Boolean existsByEmail = userRepository.existsByEmail(email);
		boolean existsByBookId = paymentRepository.existsByBookId(bookId);
		// String status="pending";
		if (existsByEmail && existsByBookId) {
			DegitalbookUser user = userRepository.findByEmail(email)
					.orElseThrow(() -> new RuntimeException("email not found :" + email));
			Payment payment = paymentRepository.findByBookId(bookId)
					.orElseThrow(() -> new RuntimeException("book not found :" + bookId));
//			RefundPayment refundPayment = refundRepo.findByBookId(bookId).
//					orElseThrow(()->new RuntimeException("book not found in refundpayment"));
//			 Date paymentdate = payment.getPaymentdate();
//			 paymentdate=DateUti.addDays(paymentdate, 1);
//			Instant start = paymentdate.toInstant();
//			Instant stop = paymentdate.toInstant();
//			Instant target = start.plus(24, ChronoUnit.HOURS);
//			Boolean isExactly24HoursLater = stop.equals(target);	
//			if (!isExactly24HoursLater) {
			refund.setBookId(payment.getBookId());
			refund.setPaymentId(payment.getId());
			refund.setRefundedAmount(payment.getPrice());
			refund.setReaderId(payment.getReaderId());
			refund.setRefundDate(new Date(System.currentTimeMillis()));
			RefundPayment save = refundRepo.save(refund);
			map.put("Refund_PaymentPrice", String.valueOf(save.getRefundedAmount()));
			map.put("email", user.getEmail());
			map.put("bookId", String.valueOf(save.getBookId()));
		}
		// }
		return map;
	}
}
