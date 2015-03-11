package com.makeandbuild.vessl.sample.persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.makeandbuild.vessl.persistence.jdbc.BaseDaoImpl;
import com.makeandbuild.vessl.persistence.jdbc.CascadeDelete;
import com.makeandbuild.vessl.persistence.jdbc.ReflectionBasedJdbcMapper;
import com.makeandbuild.vessl.sample.domain.User;

public class UserDaoImpl extends BaseDaoImpl<User,Long> implements UserDao {
	@Autowired
	@CascadeDelete (joinAttributeName = "userId")
	EventDao eventDao;
	
	public UserDaoImpl() {
		super(ReflectionBasedJdbcMapper.proxy(User.class), User.class, Long.class);
	}
}