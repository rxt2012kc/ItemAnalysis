package com.amazon.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@Getter
@Setter
@PropertySource("classpath:properties/config.properties")
public class AppConfig {

	@Value("#{configProperties['AssociateTag']}")
	private String associateTag;
	
	@Value("#{configProperties['AWSAccessKeyId']}")
	private String awsAccessKeyId;

	@Value("#{configProperties['AWSSecretKey']}")
	private String awsSecretKey;

	public String getAssociateTag() {
		return associateTag;
	}

	public void setAssociateTag(String associateTag) {
		this.associateTag = associateTag;
	}

	public String getAwsAccessKeyId() {
		return awsAccessKeyId;
	}

	public void setAwsAccessKeyId(String awsAccessKeyId) {
		this.awsAccessKeyId = awsAccessKeyId;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

}
