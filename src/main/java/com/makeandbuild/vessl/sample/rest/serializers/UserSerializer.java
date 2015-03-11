package com.makeandbuild.vessl.sample.rest.serializers;

import static com.makeandbuild.vessl.sample.rest.serializers.SerializerUtil.writeNumberField;
import static com.makeandbuild.vessl.sample.rest.serializers.SerializerUtil.writeObjectField;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.makeandbuild.vessl.sample.domain.User;

public class UserSerializer  extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		writeNumberField("id", value.getId(), jgen);
		writeObjectField("createdAt", value.getCreatedAt(), jgen);
		writeObjectField("latitude", value.getLatitude(), jgen);
		writeObjectField("loginCount", value.getLoginCount(), jgen);
		writeObjectField("longitude", value.getLongitude(), jgen);
		writeObjectField("username", value.getUsername(), jgen);
		writeObjectField("userType", value.getUserType(), jgen);
		jgen.writeEndObject();
	}
}
