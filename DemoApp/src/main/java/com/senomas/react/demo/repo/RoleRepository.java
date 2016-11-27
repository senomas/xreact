package com.senomas.react.demo.repo;

import com.senomas.common.persistence.BasicFilter;
import com.senomas.common.persistence.PageRepository;
import com.senomas.react.demo.model.Role;

public interface RoleRepository extends PageRepository<Role, Long, BasicFilter<Role>, Role> {
	
	Role findByCode(String code);
	
}
