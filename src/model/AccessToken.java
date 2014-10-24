package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public class AccessToken {
	private static final String clientId = "757e6e586b395831";
	private static final String clientSecret = "bad3722a9e56980ed4679c6346c3c137";
	public static String accessToken;
	public static long expiresIn;
	public static long updateTime;
	public static String refreshToken;
	public static String tokenType;
	public static String state;
	public static JSONObject jsonAccessToken;
	
	static {
		accessToken = "";
		expiresIn = 0L;
		updateTime = 0L;
		tokenType = "bearer";
		refreshToken = "";
		state = "";
		jsonAccessToken = null;		
	}
	
//	public AccessToken() {
//		accessToken = "";
//		expiresIn = 0L;
//		updateTime = 0L;
//		tokenType = "bearer";
//		refreshToken = "";
//	}
	
	public static void update(File file) {
		if(file.exists()){
//			System.out.println("static " + file.getName() + " exist!");
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String json = "", line;
				while((line = br.readLine()) != null){
					json += line;
				}
				JSONObject at = null;
				if(json != null) {
					at = new JSONObject(json);
					AccessToken.accessToken = at.getString("access_token");
					AccessToken.expiresIn = at.getLong("expires_in");
					AccessToken.updateTime = at.getLong("update_time");
					AccessToken.tokenType = at.getString("token_type");
					AccessToken.state = at.getString("state");
					if(at.has("refresh_key")) AccessToken.refreshToken = at.getString("refresh_token");
					AccessToken.jsonAccessToken = at;
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void update(String accessToken, long expiresIn, long updateTime, String tokenType, JSONObject jsonAccessToken) {
		AccessToken.accessToken = accessToken;
		AccessToken.expiresIn = expiresIn;
		AccessToken.updateTime = updateTime;
		AccessToken.tokenType = tokenType;
		AccessToken.jsonAccessToken = jsonAccessToken;
	}
	
	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		AccessToken.accessToken = accessToken;
	}

	public static long getExpiresIn() {
		return expiresIn;
	}

	public static void setExpiresIn(long expiresIn) {
		AccessToken.expiresIn = expiresIn;
	}

	public static long getUpdateTime() {
		return updateTime;
	}

	public static void setUpdateTime(long updateTime) {
		AccessToken.updateTime = updateTime;
	}

	public static String getRefreshToken() {
		return refreshToken;
	}

	public static void setRefreshToken(String refreshToken) {
		AccessToken.refreshToken = refreshToken;
	}

	public static String getTokenType() {
		return tokenType;
	}

	public static void setTokenType(String tokenType) {
		AccessToken.tokenType = tokenType;
	}

	public static String getClientid() {
		return clientId;
	}

	public static String getClientsecret() {
		return clientSecret;
	}
	
	
}
