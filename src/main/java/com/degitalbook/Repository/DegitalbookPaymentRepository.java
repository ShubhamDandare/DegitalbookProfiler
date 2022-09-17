package com.degitalbook.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.degitalbook.entity.Payment;

@Repository
public interface DegitalbookPaymentRepository extends JpaRepository<Payment, Long>{
	Boolean existsByReaderId(Long readerId);
	List<Payment> findAllByreaderId(Long readerId);
	Optional<Payment>  findById(Long paymentId);
	

}
