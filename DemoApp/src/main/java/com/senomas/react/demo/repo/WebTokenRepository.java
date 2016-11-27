package com.senomas.react.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.senomas.react.demo.model.WebToken;

public interface WebTokenRepository extends CrudRepository<WebToken, String> {
	
	WebToken findByUsername(String username);

	WebToken findByRefreshToken(String refreshToken);

}
