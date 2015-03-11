package com.makeandbuild.vessl.sample.rest;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.makeandbuild.vessl.persistence.jdbc.BaseDao;
import com.makeandbuild.vessl.rest.ResourceSerializedBase;
import com.makeandbuild.vessl.sample.domain.User;
import com.makeandbuild.vessl.sample.persistence.UserDao;
import com.makeandbuild.vessl.sample.rest.serializers.UserSerializer;

@Path("/users")
public class UserResource extends ResourceSerializedBase<User, Long> {
	@Autowired
	UserDao userDao;

	public UserResource() {
		super(User.class);
	}

	@Override
	protected BaseDao<User, Long> getDao() {
		return userDao;
	}

	@Autowired
	UserSerializer userSerializer;

	@Override
	protected void addModuleSerializers(SimpleModule testModule){
		testModule.addSerializer(User.class, userSerializer);
	}
}
