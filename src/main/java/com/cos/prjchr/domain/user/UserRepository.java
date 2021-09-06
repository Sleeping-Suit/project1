package com.cos.prjchr.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value="insert into user (email,password,username) values (:username,:password,:email)", nativeQuery = true)
	void join(String username, String password, String email);
}
