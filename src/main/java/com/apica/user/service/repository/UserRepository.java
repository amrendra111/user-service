package com.apica.user.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apica.user.service.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	@Override
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM user where id = :id ;", nativeQuery = true)
	void deleteById(@Param(value = "id") Integer id);

	List<User> findByFirstName(String string);

}
