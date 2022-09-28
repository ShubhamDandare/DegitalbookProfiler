package com.degitalbook.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.degitalbook.entity.RefundPayment;
@Repository
public interface RefundRepository extends JpaRepository<RefundPayment,Long> {
     Optional<RefundPayment> findByBookId(Long bookId);
}
