package com.makeandbuild.vessl.sample.persistence;

import com.makeandbuild.vessl.persistence.jdbc.BaseDao;
import com.makeandbuild.vessl.sample.domain.User;

public interface UserDao extends BaseDao<User, Long> {
}
