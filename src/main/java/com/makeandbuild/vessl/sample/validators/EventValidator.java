package com.makeandbuild.vessl.sample.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.makeandbuild.vessl.sample.domain.Event;
import com.makeandbuild.vessl.validation.ValidationType;

@ValidationType("data")
public class EventValidator implements Validator {
    Log logger = LogFactory.getLog(getClass());
    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Event value = (Event) o;
        if (value != null) {
            if (value.getType() == null){
                errors.rejectValue("type", "local.error.null", "type must be provided");            	
            }
        } else {
            errors.reject("local.error.empty", "Event does not contain any information");
        }
    }
}
