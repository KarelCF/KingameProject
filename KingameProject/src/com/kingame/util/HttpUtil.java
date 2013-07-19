package com.kingame.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// ����URL,ע��˴���Ϊģ����ʵ�飬ip����Ϊlocalhost����ҪΪ��ҪΪ10.0.2.2
	public static final String BASE_URL = "http://172.16.2.137:8080/OAServer/";

	// ���Get�������request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// ���Post�������request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		// ����������ͨ�ų�ʱΪ10��
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);
		
		HttpResponse response = httpClient.execute(request);
		return response;
	}

	// ����Post���󣬻����Ӧ��ѯ���
	public static String queryStringForPost(String url, String codeInfo,
			byte[] codeImgBuffer) {
		// ����url���HttpPost����
		HttpPost request = HttpUtil.getHttpPost(url);
		// װ������Ϣ(����)
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("codeInfo", codeInfo));
		
		// ���ֽ�����תΪString, ʹ��Post����
		byte[] codeImgBytes = codeImgBuffer;
		StringBuffer codeImgBufferString = new StringBuffer();
		for (int i = 0; i < codeImgBuffer.length; i++) {
			codeImgBufferString.append(codeImgBuffer[i] + ";");
		}
		// װ������Ϣ(ͼ��)
		params.add(new BasicNameValuePair("codeImgBufferString", codeImgBufferString.toString()));
		params.add(new BasicNameValuePair("codeImgBufferLength", codeImgBytes.length + ""));
		String result = null;
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// �����Ӧ����
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity(), "gbk");
				// ��ʽ�ͷ���Դ
				response.getEntity().consumeContent();
				return result;
			} 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		}
		return null;
	}

	// ����Get���󣬻����Ӧ��ѯ���
	public static String queryStringForGet(String url) {
		// ���HttpGet����
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity(), "gbk");
				response.getEntity().consumeContent();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		}
		return null;
	}
	
}
