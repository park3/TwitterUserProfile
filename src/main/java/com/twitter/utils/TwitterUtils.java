package com.twitter.utils;

import com.ftl.main.*;
import java.net.URI;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterUtils {
	final static Logger log = Logger.getLogger(TwitterUtils.class);
	Twitter twitter;
	RequestToken requestToken;
	public URI uri;
	AccessToken accessToken;
	User user;
	FtlConfig ftlConfig;
	public TwitterUtils(){
		try {
			twitter = new TwitterFactory().getInstance();
			requestToken = twitter.getOAuthRequestToken();
			uri = new URI(requestToken.getAuthenticationURL()); 	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		ftlConfig=new FtlConfig();
	}
	public void getAccessToken(String oauthVerifier) throws Exception{
		log.debug("Calling twitter4j api method for getting accesstoken");
		try {
			accessToken=twitter.getOAuthAccessToken(requestToken,oauthVerifier);	
		} 
		catch (TwitterException e) {
			e.printStackTrace();
		}
		getUserDetails(accessToken.getScreenName());
	}
	public void getUserDetails(String screenName) {
		log.debug("Calling twitter4j api method for getting user profile details using screen name of user");
		try {
			user = twitter.showUser(accessToken.getScreenName());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		ftlConfig.ftlConfigurationAndSettingUserDataToFtlPage(user);
	}
}