package com.degitalbook.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.degitalbook.entity.ERole;
import com.degitalbook.entity.Role;
@Repository
public interface DegitalbookRoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(ERole name);

}
