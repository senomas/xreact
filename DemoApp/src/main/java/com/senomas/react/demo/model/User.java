package com.senomas.react.demo.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.codec.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.senomas.boot.Audited;
import com.senomas.boot.security.domain.SecurityUser;
import com.senomas.common.rs.Views;

@Entity
@Audited("User")
@Table(name = "APP_USER", uniqueConstraints = {@UniqueConstraint(columnNames = "USERNAME"), @UniqueConstraint(columnNames = "NAME")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements SecurityUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@JsonView({ Views.List.class, Audited.Key.class })
	private Long id;

	@NotNull
	@Column(name = "USERNAME", length = 50)
	@JsonView({ Views.List.class, Audited.Brief.class })
	private String username;

	@NotNull
	@Column(name = "PASSWORD", length = 50)
	@JsonIgnore
	private String password;

	@NotNull
	@Column(name = "NAME", length = 100)
	@JsonView({ Views.List.class, Audited.Detail.class })
	public String name;

	@Column(name = "LAST_LOGIN")
	@JsonView(Views.List.class)
	private Date lastLogin;

	@Column(name = "LOGIN_TOKEN", length = 50)
	@JsonIgnore
	public String loginToken;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "APP_USER_ROLE", joinColumns = @JoinColumn(name = "FUSER", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "FROLE", referencedColumnName = "ID"))
	@JsonView({ Views.List.class, Audited.Detail.class })
	private List<Role> roles;

	@NotNull
	@Column(name = "STATUS", length = 50)
	@JsonView({ Views.List.class, Audited.Detail.class })
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (password != null && password.length() > 0)
			throw new RuntimeException("Can not change username");
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlainPassword(String password) {
		if (username == null || username.length() == 0)
			throw new RuntimeException("Need to set username first");
		encryptUsernamePassword(username, password);
	}

	public void encryptUsernamePassword(String username, String plainPassword) {
		try {
			this.username = username;

			Mac hmac = Mac.getInstance("HmacSHA256");
			hmac.init(new SecretKeySpec("Av@T@r".getBytes("UTF-8"), "HmacSHA256"));

			this.password = new String(Base64.encode(hmac.doFinal((username + plainPassword).getBytes("UTF-8"))),
					"UTF-8");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	@JsonView({ Views.Detail.class })
	public String getLogin() {
		return username;
	}

	@Override
	public String getLoginToken() {
		return loginToken;
	}

	@Override
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
		this.lastLogin = new Date();

	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> res = new LinkedList<>();
		for (Role rg : roles) {
			res.addAll(rg.getAuthorities());
		}
		return res;
	}

	public boolean validatePassword(String plainPassword) {
		try {
			Mac hmac = Mac.getInstance("HmacSHA256");
			hmac.init(new SecretKeySpec("Av@T@r".getBytes("UTF-8"), "HmacSHA256"));

			return this.password.equals(
					new String(Base64.encode(hmac.doFinal((username + plainPassword).getBytes("UTF-8"))), "UTF-8"));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((loginToken == null) ? 0 : loginToken.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (loginToken == null) {
			if (other.loginToken != null)
				return false;
		} else if (!loginToken.equals(other.loginToken))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", lastLogin=" + lastLogin + ", loginToken=" + loginToken + ", roles=" + roles + ", status=" + status
				+ "]";
	}
}
