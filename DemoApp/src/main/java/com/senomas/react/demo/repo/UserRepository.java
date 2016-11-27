package com.senomas.react.demo.repo;

import com.senomas.common.persistence.PageRepository;
import com.senomas.react.demo.model.User;

public interface UserRepository extends PageRepository<User, Long, UserFilter<UserFilterObject>, UserFilterObject> {
	
	User findByUsername(String username);

	User findByLoginToken(String loginToken);
}
