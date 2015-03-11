package com.makeandbuild.vessl.sample.persistence;
import com.makeandbuild.vessl.persistence.jdbc.BaseDao;
import com.makeandbuild.vessl.sample.domain.Event;

import java.util.Date;
import java.util.List;

public interface EventDao extends BaseDao<Event, String> {
}
