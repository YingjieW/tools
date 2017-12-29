package com.tools.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {

	private static RequestConfig requestConfig = null;

	static {
		requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
	}

	private HttpClient() {

	}

	public static com.tools.util.HttpClient getInstance() {
		return new com.tools.util.HttpClient();
	}

	public Map<String, String> doPost(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		HttpUriRequest request = RequestBuilder.post().setUri(url).setConfig(requestConfig)
				.setEntity(buildEntity(params)).build();

		return execute(request);
	}
	
	public Map<String, String> doPost(String url, String json)
			throws ClientProtocolException, IOException {
		HttpUriRequest request = RequestBuilder.post().setUri(url).setConfig(requestConfig)
				.setEntity(buildEntity(json)).build();

		return execute(request);
	}

	public Map<String, String> doGet(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		HttpUriRequest request = RequestBuilder.get().setUri(url).setConfig(requestConfig)
				.setEntity(buildEntity(params)).build();

		return execute(request);
	}

	private HttpEntity buildEntity(Map<String, String> params) {
		try {
			List<NameValuePair> kvList = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				NameValuePair pair = new BasicNameValuePair(key, params.get(key));
				kvList.add(pair);
			}

			return new UrlEncodedFormEntity(kvList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private HttpEntity buildEntity(String json) {
		StringEntity entity = new StringEntity(json, "utf-8");
		entity.setContentType("application/json");
		return entity;
	}

	private Map<String, String> execute(HttpUriRequest request) throws ClientProtocolException, IOException {
		Map<String, String> result = new HashMap<String, String>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(request);
		result.put("statusCode", Integer.toString(response.getStatusLine().getStatusCode()));
		result.put("protocolVersion", response.getStatusLine().getProtocolVersion().toString());
		result.put("entity", EntityUtils.toString(response.getEntity(), "utf-8"));
		return result;
	}
	
	public void downloadFile(String url, String filePath) {
		org.apache.http.client.HttpClient httpClient = null;
		BufferedOutputStream bw = null;
		try {
			if (url.startsWith("https://")) {
				httpClient = new SSLClient();
			} else if (url.startsWith("http://")) {
				httpClient = new DefaultHttpClient();
			} else {
				return;
			}
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				// 创建文件对象
				File f = new File(filePath);
				// 创建文件路径
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				// 写入文件
				bw = new BufferedOutputStream(new FileOutputStream(f));
				bw.write(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	class SSLClient extends DefaultHttpClient{
		 public SSLClient() throws Exception{  
		        super();  
		        SSLContext ctx = SSLContext.getInstance("TLS");  
		        X509TrustManager tm = new X509TrustManager() {  
		                @Override  
		                public void checkClientTrusted(X509Certificate[] chain,  
		                        String authType) throws CertificateException {  
		                }  
		                @Override  
		                public void checkServerTrusted(X509Certificate[] chain,  
		                        String authType) throws CertificateException {  
		                }  
		                @Override  
		                public X509Certificate[] getAcceptedIssuers() {  
		                    return null;  
		                }  
		        };  
		        ctx.init(null, new TrustManager[]{tm}, null);  
		        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
		        ClientConnectionManager ccm = this.getConnectionManager();  
		        SchemeRegistry sr = ccm.getSchemeRegistry();  
		        sr.register(new Scheme("https", 443, ssf));  
		    }  
	}
}
