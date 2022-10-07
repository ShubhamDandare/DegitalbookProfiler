package com.degitalbook.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.degitalbook.Repository.DegitalbookPaymentRepository;
import com.degitalbook.Repository.DegitalbookRepository;
import com.degitalbook.Repository.DegitalbookUserRepository;
import com.degitalbook.Service.DegitalBookService;
import com.degitalbook.entity.BuyBookRequest;
import com.degitalbook.entity.DegitalBookEntity;
import com.degitalbook.entity.DegitalbookUser;
import com.degitalbook.entity.Payment;

@SpringBootTest
public class DegitalbookServiceTest {

	@Mock
	public DegitalbookRepository bookRepository;

	@Mock
	public DegitalbookUserRepository userRepository;

	@Mock
	public DegitalbookPaymentRepository payRepository;
	

	@InjectMocks
	public DegitalBookService service;

	@Test
	public void saveDegitalbookTest() {

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");
		when(bookRepository.save(entity)).thenReturn(entity);
		DegitalBookEntity saveDegitalbook = service.saveDegitalbook(entity);
		long id = saveDegitalbook.getId();
		assertThat(id == 1);

//		when(service.saveDegitalbook(entity)).thenReturn(entity);
//		DegitalBookEntity save = bookRepository.save(entity);
//		long id = save.getId();
//		assertThat(id == 1);

	}

	@Test
	public void updateDegitalbookTest() {
		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		DegitalBookEntity tosave = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("REX");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
		when(bookRepository.save(tosave)).thenReturn(tosave);

		ResponseEntity<DegitalBookEntity> degitalbook = service.updateDegitalbook(1, tosave);
		assertThat(degitalbook.getBody().getAuthor().equals("REX"));

	}

	@Test
	public void getAllDegitalbookTest() {
		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		DegitalBookEntity entity1 = new DegitalBookEntity();
		entity1.setId(2);
		entity1.setTitle("AWS Cloud Computing");
		entity1.setAuthor("REX");
		entity1.setCategory("cloud computing");
		entity1.setContent("cloude");
		entity1.setActive(true);
		entity1.setPrice(5543.30);
		entity1.setPublisher("shubham");
		when(bookRepository.findAll()).thenReturn(Arrays.asList(entity, entity1));
		List<DegitalBookEntity> allDegitalbook = service.getAllDegitalbook();
		assertThat(allDegitalbook.get(1).getAuthor().equals("REX"));
	}

	@Test
	public void searchDegitalbookTest() {

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		when(bookRepository.findAllByCategoryAndAuthorAndPrice("cloud computing", "Jhon Denver", 5543.30))
				.thenReturn(Arrays.asList(entity));
		List<DegitalBookEntity> searchDegitalbook = service.searchDegitalbook("cloud computing", "Jhon Denver",
				5543.30);
		DegitalBookEntity degitalBookEntity = searchDegitalbook.get(0);
		assertThat(degitalBookEntity.getCategory().equals("cloud computing"));

	}

	@Test
	public void isUserAvailableTest() {
		boolean isavailable = true;
		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");
		user.setId(1l);
		user.setPassword("123asd");
		user.setUsername("12345asd");

		when(userRepository.existsByUsername("12345asd")).thenReturn(isavailable);
		Boolean userAvailable = service.isUserAvailable("12345asd");
		assertThat(userAvailable == true);

	}

	@Test
	public void isBookAvailableTest() {
		boolean isbookAvailable = true;
		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1L);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		when(bookRepository.existsById(1L)).thenReturn(isbookAvailable);
		Boolean bookAvailable = service.isBookAvailable(1L);
		assertThat(bookAvailable == true);
	}

	@Test
	public void getUserbyNameTest() {
		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");
		user.setId(1l);
		user.setPassword("123asd");
		user.setUsername("12345asd");

		when(userRepository.findByUsername("12345asd")).thenReturn(Optional.of(user));
		Optional<DegitalbookUser> userbyName = service.getUserbyName("12345asd");
		assertThat(userbyName.equals("12345asd"));

	}

	@Test
	public void savePaymentTest() {

		Payment pay = new Payment();
		pay.setBookId(1L);
		pay.setId(1L);
		pay.setPrice(123.33);
		pay.setReaderId(1L);

		when(payRepository.save(pay)).thenReturn(pay);
		Payment savePayment = service.savePayment(pay);
		assertThat(savePayment.getId().equals(1));

	}

	@Test
	public void isuserAvailableByEmailTest() {
		boolean isuserEmailAvailable = true;
		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");
		user.setId(1l);
		user.setPassword("123asd");
		user.setUsername("12345asd");

		when(userRepository.existsByEmail("123@gmail.com")).thenReturn(isuserEmailAvailable);
		Boolean isuserAvailableByEmail = service.isuserAvailableByEmail("123@gmail.com");
		assertThat(isuserAvailableByEmail.equals("123@gmail.com"));
	}

	@Test
	public void findUserByEmailTest() {

		DegitalbookUser user = new DegitalbookUser();
		user.setEmail("123@gmail.com");
		user.setId(1l);
		user.setPassword("123asd");
		user.setUsername("12345asd");

		when(userRepository.findByEmail("12345asd")).thenReturn(Optional.of(user));
		Optional<DegitalbookUser> findUserByEmail = service.findUserByEmail("12345asd");
		assertThat(findUserByEmail.equals("12345asd"));
	}

	@Test
	public void isPaymentAvailableForReaderIdTest() {
		boolean id = true;
		Payment pay = new Payment();
		pay.setBookId(1L);
		pay.setId(1L);
		pay.setPrice(123.33);
		pay.setReaderId(1L);

		when(payRepository.existsByReaderId(1L)).thenReturn(id);
		Boolean paymentAvailableForReaderId = service.isPaymentAvailableForReaderId(1L);

		assertThat(paymentAvailableForReaderId.equals(1L));
	}

	@Test
	public void getReaderBookIdTest() {
		List<Payment> list = new ArrayList<>();

		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1L);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		Payment pay = new Payment();
		pay.setBookId(1L);
		pay.setId(1L);
		pay.setPrice(123.33);
		pay.setReaderId(1L);
		list.add(pay);

		Set<Long> set1 = new HashSet<>();
		set1.add(entity.getId());

		Map<String, Set<Long>> map = new HashMap<>();
		map.put("BookID", set1);

		when(payRepository.findAllByreaderId(1L)).thenReturn(list);
		Map<String, Set<Long>> readerBookId = service.getReaderBookId(1L);
//		assertThat(readerBookId.get("BookId").equals(1L));

		Set<Long> set = readerBookId.get("BookID");
		set.equals(1L);

	}

	@Test
	public void getDegitalbookById() {
		DegitalBookEntity entity = new DegitalBookEntity();
		entity.setId(1L);
		entity.setTitle("AWS Cloud Computing");
		entity.setAuthor("Jhon Denver");
		entity.setCategory("cloud computing");
		entity.setContent("cloude");
		entity.setActive(true);
		entity.setPrice(5543.30);
		entity.setPublisher("shubham");

		when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
		DegitalBookEntity degitalbookById = service.getDegitalbookById(1L);
		assertThat(degitalbookById.equals(1L));

	}

//	@Test
//	public void buyDegitalBook() {
//		Boolean isavailable=true;
//		Boolean isbookAvailable=true;
//		
//		BuyBookRequest buy=new BuyBookRequest();
//		buy.setBookid(1L);
//		buy.setEmail("123@gmail.com");
//		buy.setUsername("12345asd");
//		
//		DegitalbookUser user = new DegitalbookUser();
//		user.setEmail("123@gmail.com");
//		user.setId(1l);
//		user.setPassword("123asd");
//		user.setUsername("12345asd");
//
//		DegitalBookEntity entity = new DegitalBookEntity();
//		entity.setId(1L);
//		entity.setTitle("AWS Cloud Computing");
//		entity.setAuthor("Jhon Denver");
//		entity.setCategory("cloud computing");
//		entity.setContent("cloude");
//		entity.setActive(true);
//		entity.setPrice(5543.30);
//		entity.setPublisher("shubham");
//		
//		Payment pay = new Payment();
//		pay.setBookId(1L);
//		pay.setId(1L);
//		pay.setPrice(5543.30);
//		pay.setReaderId(1L);
//		
////		Map<String, Long> map = new HashMap<>();
////
////		map.put("PaymentId", 1L);
////		map.put("BookId", 1L);
//		
//		when(payRepository.save(pay)).thenReturn(pay);
//		when(userRepository.existsByUsername("12345asd")).thenReturn(isavailable);
//		when(bookRepository.existsById(1L)).thenReturn(isbookAvailable);
//		when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
//		when(userRepository.findByUsername("12345asd")).thenReturn(Optional.of(user));
//		
//		ResponseEntity<Map<String,Long>> buyDegitalBook = service.buyDegitalBook(buy);
//		Map<String,Long> body = buyDegitalBook.getBody();
//		Long long1 = body.get("BookId");
//		long1.equals(1L);
//		
//		
//		
//	}
}
