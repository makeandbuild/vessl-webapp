package com.makeandbuild.vessl.sample.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.makeandbuild.vessl.persistence.jdbc.SaveWhen;

@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id")
    @SaveWhen(insert = true, update = false)
    private String id;

    @Column(name = "type")
    @SaveWhen(insert = true, update = true)
    private String type;

    @Column(name = "parent_event_id")
    @SaveWhen(insert = true, update = false)
    private String parentId;

    @Column(name = "user_id")
    @SaveWhen(insert = true, update = false)
    private Long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
