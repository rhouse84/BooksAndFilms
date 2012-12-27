package booksandfilms.client.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	Long id;
	@Indexed private String name;
	@Indexed private String emailAddress;
	@Unindexed private Date lastLoginOn;
	@Unindexed private Date lastActive;
	@Unindexed private boolean adminUser;
	@Indexed private String uniqueId;
	@Unindexed private boolean ultimateUser;
	
	public UserAccount() {}
	public UserAccount(String loginId, Integer loginProvider) {
		this();
		this.setUniqueId(loginId + "-" + loginProvider);
		this.setName(loginId);
	}
	public UserAccount(Long id) {
		this();
		this.setId(id);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Date getLastLoginOn() {
		return lastLoginOn;
	}
	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}
	public Date getLastActive() {
		return lastActive;
	}
	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}
	public boolean getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public boolean getUltimateUser() {
		return ultimateUser;
	}
	public void setUltimateUser(boolean ultimateUser) {
		this.ultimateUser = ultimateUser;
	}
}
