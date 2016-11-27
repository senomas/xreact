package com.senomas.react.demo.repo;

import javax.persistence.criteria.CriteriaBuilder;

import com.senomas.common.persistence.Filter;
import com.senomas.common.persistence.QueryBuilder;

public class UserFilter<T extends UserFilterObject> extends Filter<T> {

	@Override
	public void init(CriteriaBuilder builder, QueryBuilder query) {
		UserFilterObject data = getData();
		if (data != null) {
			if (data.getName() != null && data.getName().length() > 0) {
				query.whereAddAnd(builder.equal(query.root().get("name").as(String.class), data.getName()));
			}
		}
	}

}
