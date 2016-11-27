package com.senomas.react.demo.repo;

import com.senomas.common.persistence.BasicFilter;
import com.senomas.common.persistence.PageRepository;
import com.senomas.react.demo.model.AuthorityData;

public interface AuthorityRepository extends PageRepository<AuthorityData, Long, BasicFilter<AuthorityData>, AuthorityData> {
	
}
