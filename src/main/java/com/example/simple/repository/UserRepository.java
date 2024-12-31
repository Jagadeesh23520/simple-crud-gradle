package com.example.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.simple.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	@Query(value = "select u from Users u where u.userName  like %:name%")
	List<Users> searchByUserName(@Param(value = "name") String name);

	List<Users> findById(int id);

	Users findByUserUID(String userUID);

	void deleteById(Integer id);

}
