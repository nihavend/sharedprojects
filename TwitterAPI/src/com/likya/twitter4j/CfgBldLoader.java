package com.likya.twitter4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import twitter4j.conf.ConfigurationBuilder;

public class CfgBldLoader {
	
	public static ConfigurationBuilder load() {
		Properties properties = new Properties();
		try {
			properties.load(
					new FileInputStream("/privateFile.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setOAuthConsumerKey(properties.get("OAuthConsumerKey").toString());
		cb.setOAuthConsumerSecret(properties.get("OAuthConsumerSecret").toString());
		cb.setOAuthAccessToken(properties.get("OAuthAccessToken").toString());
		cb.setOAuthAccessTokenSecret(properties.get("OAuthAccessTokenSecret").toString());

		return cb;
	}

}
