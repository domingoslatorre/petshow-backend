package hyve.petshow.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtils {
	
	public static String getRequest(String url) {
		var client = new OkHttpClient();
		var request = new Request.Builder()
				.url(url)
				.build();
		
		try (var response = client.newCall(request).execute()) {
			return response.body().string();
		} catch (Exception e) {
			return null;
		}
	}
	
}
