package com.makeandbuild.vessl.sample.rest.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SerializerUtil {
	static public void writeObjectField(String name, Object value, JsonGenerator jgen) throws JsonProcessingException, IOException {
		if (value != null){
			jgen.writeObjectField(name, value);
		}
	}
	static public void writeNumberField(String name, Long value, JsonGenerator jgen) throws JsonProcessingException, IOException {
		if (value != null){
			jgen.writeNumberField(name, value);
		}
	}
	static public void writeStringField(String name, String value, JsonGenerator jgen) throws JsonProcessingException, IOException {
		if (value != null){
			jgen.writeStringField(name, value);
		}
	}
}
