package com.senomas.react.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.senomas.boot.Audited;
import com.senomas.common.rs.Views;



@Entity
@Audited("Attribute")
@Table(name = "ATTRIBUTE", uniqueConstraints = { @UniqueConstraint(columnNames = "TYPE"),
		@UniqueConstraint(columnNames = "VALUE") })
public class Attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@JsonView({Views.List.class, Audited.Key.class})
	private Long id;


	@NotNull
	@Column(name = "TYPE", length = 255)
	@JsonView({Views.List.class, Audited.Brief.class})
	private String type;

	@NotNull
	@Column(name = "VALUE", length = 50)
	@JsonView(Views.List.class)
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Attribute other = (Attribute) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogEvent [id=" + id + ", type=" + type + ", type=" + type
				+ ", value=" + value + "]";
	}

}
