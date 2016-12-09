package com.revature.sfadapter.util;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SFWSAccessObject {

	@Column(name = "SF_ACCESS_TOKEN", nullable = false)
	private String accessToken;

	@Column(name="SF_REFRESH_TOKEN")
	private String refreshToken;

	@Column(name = "SF_INSTANCE_URL", nullable = false)
	private String instanceUrl;

	@Column(name = "SF_USER_URL", nullable = false)
	private String userUrl;

	@Column(name = "SF_ISSUED_TIME", nullable = false)
	private String issued;

	@Id
	@Column(name = "SF_USER_SIG")
	private String signature;

	public SFWSAccessObject(){}

	public SFWSAccessObject(String accessToken, String refreshToken, String instanceUrl, String userUrl, String issued,
							String signature) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.instanceUrl = instanceUrl;
		this.userUrl = userUrl;
		this.issued = issued;
		this.signature = signature;
	}

	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty(value="access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	@JsonProperty(value="refresh_token")
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	@JsonProperty(value="instance_url")
	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	public String getuserUrl() {
		return userUrl;
	}

	@JsonProperty(value="id")
	public void setuserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public String getIssued() {
		return issued;
	}

	@JsonProperty(value="issued_at")
	public void setIssued(String issued) {
		this.issued = issued;
	}

	public String getSignature() {
		return signature;
	}

	@JsonProperty(value="signature")
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "SFWSAccessObject{" +
				"accessToken='" + accessToken + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", instanceUrl='" + instanceUrl + '\'' +
				", userUrl='" + userUrl + '\'' +
				", issued='" + issued + '\'' +
				", signature='" + signature + '\'' +
				'}';
	}
}
