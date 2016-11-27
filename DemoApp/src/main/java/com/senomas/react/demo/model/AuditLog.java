package com.senomas.react.demo.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.senomas.common.rs.Views;

@Entity
@Table(name = "AUDIT_LOG")
public class AuditLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@JsonView(Views.List.class)
	private Long id;

	@NotNull
	@Column(name="TIMESTAMP")
	@JsonView(Views.List.class)
	private Date timestamp;

	@Column(name = "USERNAME", length = 50)
	@JsonView(Views.List.class)
	private String username;

	@NotNull
	@Column(name = "ACTION", length = 50)
	@JsonView(Views.List.class)
	private String action;

	@Column(name = "OBJECT_TYPE", length = 200)
	@JsonView(Views.List.class)
	private String objectType;

	@Column(name = "OBJECT_KEY", length = 200)
	@JsonView(Views.List.class)
	private String objectKey;

	@Lob @Basic(fetch=FetchType.LAZY)
	@Column(name  = "OBJECT")
	@JsonView(Views.Detail.class)
	private String object;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public String getObjectKey() {
		return objectKey;
	}
	
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getObject() {
		return object;
	}
	
	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((objectKey == null) ? 0 : objectKey.hashCode());
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLog other = (AuditLog) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (objectKey == null) {
			if (other.objectKey != null)
				return false;
		} else if (!objectKey.equals(other.objectKey))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditLog [id=" + id + ", timestamp=" + timestamp + ", username=" + username + ", action=" + action
				+ ", objectType=" + objectType + ", objectKey=" + objectKey + ", object=" + object + "]";
	}
}
