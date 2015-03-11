package com.makeandbuild.vessl.sample.rest;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.makeandbuild.vessl.persistence.jdbc.BaseDao;
import com.makeandbuild.vessl.rest.ResourceSerializedBase;
import com.makeandbuild.vessl.sample.domain.Event;
import com.makeandbuild.vessl.sample.domain.User;
import com.makeandbuild.vessl.sample.persistence.EventDao;
import com.makeandbuild.vessl.sample.rest.serializers.EventSerializer;
import com.makeandbuild.vessl.sample.rest.serializers.UserSerializer;

@Path("/events")
public class EventResource extends ResourceSerializedBase<Event,String> {
	@Autowired
	EventDao eventDao;

	@Autowired
	EventSerializer eventSerializer;

	@Autowired
	UserSerializer userSerializer;

	public EventResource() {
		super(Event.class);
	}

	@Override
	protected BaseDao<Event, String> getDao() {
		return this.eventDao;
	}

	@Override
	protected void addModuleSerializers(SimpleModule testModule){
		testModule.addSerializer(Event.class, eventSerializer);
		testModule.addSerializer(User.class, userSerializer);
	}
}
