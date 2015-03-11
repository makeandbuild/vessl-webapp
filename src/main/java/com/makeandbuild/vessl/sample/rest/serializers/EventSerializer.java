package com.makeandbuild.vessl.sample.rest.serializers;

import static com.makeandbuild.vessl.sample.rest.serializers.SerializerUtil.writeObjectField;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.makeandbuild.vessl.sample.domain.Event;
import com.makeandbuild.vessl.sample.domain.User;
import com.makeandbuild.vessl.sample.persistence.EventDao;
import com.makeandbuild.vessl.sample.persistence.UserDao;

public class EventSerializer  extends JsonSerializer<Event> {
	@Autowired
	EventDao eventDao;

	@Autowired
	UserDao userDao;

	@Override
	public void serialize(Event value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		writeObjectField("id", value.getId(), jgen);
		if (value.getParentId() != null) {
			Event parent = eventDao.find(value.getParentId());
			writeObjectField("parent", parent, jgen);
		}
		if (value.getUserId() != null) {
			User user = userDao.find(value.getUserId());
			writeObjectField("user", user, jgen);
		}
		writeObjectField("type", value.getType(), jgen);
		jgen.writeEndObject();
	}
}