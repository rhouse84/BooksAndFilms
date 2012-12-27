package booksandfilms.server.utils;

import java.util.Properties;

public class AuthenticationProvider {

	public static Integer GOOGLE=1, TWITTER=2, FACEBOOK=3;

	private static Properties props = System.getProperties();

	public static String fb_api_key(){
		String key = getProp("fb_api_key");
		return key;
	}
	  
	public static String getProp(String param){
		String skey = props.getProperty(param);
		return skey;
	}
	  
}
