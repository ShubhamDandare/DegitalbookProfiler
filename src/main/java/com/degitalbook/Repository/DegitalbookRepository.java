package com.degitalbook.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.degitalbook.entity.DegitalBookEntity;

@Repository
public interface DegitalbookRepository extends JpaRepository<DegitalBookEntity, Long> {

	boolean existsById(Long bookId);

	Optional<DegitalBookEntity> findById(Long bookId);

	List<DegitalBookEntity> findByCategoryAndAuthorAndPrice(String category, String author, double price);
}