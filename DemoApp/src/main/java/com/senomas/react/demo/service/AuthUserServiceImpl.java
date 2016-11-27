package com.senomas.react.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senomas.boot.security.domain.SecurityUser;
import com.senomas.boot.security.service.AuthUserService;
import com.senomas.react.demo.model.User;
import com.senomas.react.demo.repo.UserRepository;

@Component
public class AuthUserServiceImpl implements AuthUserService {

	@Autowired
	UserRepository repo;

	@Override
	@Transactional
	public SecurityUser findByLogin(String username) {
		User user = repo.findByUsername(username);
		user.getAuthorities();
		return user;
	}

	@Override
	public SecurityUser save(SecurityUser user) {
		return repo.save((User) user);
	}

}
