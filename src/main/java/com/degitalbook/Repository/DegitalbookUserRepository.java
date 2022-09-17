package com.degitalbook.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.degitalbook.entity.DegitalbookUser;

@Repository
public interface DegitalbookUserRepository extends JpaRepository<DegitalbookUser, Long> {

	Optional<DegitalbookUser> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Optional<DegitalbookUser> findByEmail(String email);

}
