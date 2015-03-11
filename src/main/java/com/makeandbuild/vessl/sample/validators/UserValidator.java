package com.makeandbuild.vessl.sample.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.makeandbuild.vessl.persistence.Criteria;
import com.makeandbuild.vessl.sample.domain.User;
import com.makeandbuild.vessl.sample.persistence.UserDao;
import com.makeandbuild.vessl.validation.ValidationType;

@ValidationType("data")
public class UserValidator implements Validator {
    @Autowired
    UserDao userDao;

    Log logger = LogFactory.getLog(getClass());
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user != null) {
            if (!userDao.exists(user.getId())){
                if (userDao.exists(new Criteria("username", user.getUsername()))) {
                    errors.rejectValue("username", "local.error.exists", "Username already taken");
                }
            }
            if (user.getUserType() == null){
                errors.rejectValue("userType", "local.error.null", "userType must be provided");            	
            }
        } else {
            errors.reject("local.error.empty", "User does not contain any information");
        }
    }
}
