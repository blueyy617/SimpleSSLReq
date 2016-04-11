import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * 
 * @Name SamsungCalendarReq 
 * @Description  HTTPS访问绕过SSL验证，测试可用
 * @Version 
 * @Company 
 * @author Louis
 * @date 2016年1月7日 下午9:07:25
 */
public class SamsungCalendarReq {

	public static String postHttps(String urlString) throws Exception {
		SSLContext sc = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new javax.net.ssl.X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			public void checkClientTrusted(
			java.security.cert.X509Certificate[] certs, String authType) {
			}
			public void checkServerTrusted(
			java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		URL u = new URL(urlString);
		HttpsURLConnection httpsConn = (HttpsURLConnection) u
				.openConnection();
		// 取得该连接的输入流，以读取响应内容
		InputStreamReader insr = new InputStreamReader(
				httpsConn.getInputStream());
		// 读取服务器的响应内容并显示
		int respInt = insr.read();
		while (respInt != -1) {
			System.out.print((char) respInt);
			respInt = insr.read();
		}
		return null;

	}

	public static void main(String[] args) throws Exception {
		String httpUrl = "";
		postHttps(httpsUrl);
	}
}
