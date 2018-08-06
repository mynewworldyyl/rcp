package net.techgy.ui.core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;

public class HTTPClient {

	public static  String VOYAGER_FW_HOST="192.168.18.28";
	public static  String VOYAGER_FW_PORT="9999";	
	public static final String VOYAGER_FW_CONTEXT="workbench";
	
	private static final String CHARSET = "UTF-8";
	
	private Map<String,String> globalHeaders = new HashMap<String,String>();
	
	private HttpClient client = null;
	
	public HTTPClient() {
		client =  getHttpClient();
	}
	
	private void setHeader(HttpRequestBase req) {
		for(Map.Entry<String, String> e : globalHeaders.entrySet()) {
			req.setHeader(e.getKey(), e.getValue());
		}
	}
	
	private String getUrl(String path) {
		String url = "http://"+VOYAGER_FW_HOST+":" + VOYAGER_FW_PORT +"/"+VOYAGER_FW_CONTEXT+"/";
		return url+path;
	}
	/**
	 *   Server: nginx
        Connection: keep-alive
        Set-Cookie: JSESSIONID=1D60C91FF09059E5D84F770AD72E1018.tomcat1; Path=/framework-web/; HttpOnly
	 * @param response
	 */
	public void initHeader(HttpResponse response) {
		globalHeaders.clear();		
		Header h = response.getFirstHeader("Set-Cookie");
		if(null != h) {
			globalHeaders.put(h.getName(), h.getValue());
		}
		h = response.getFirstHeader("Server");
		if(null != h) {
			globalHeaders.put(h.getName(), h.getValue());
		}
		h = response.getFirstHeader("Connection");
		if(null != h) {
			globalHeaders.put(h.getName(), h.getValue());
		}
	}

	public  Response  get(String path, Map<String, String> queryParams) {
		String url = getUrl(path);
		Response result = null;
		BufferedReader reader = null;
		try {
			HttpGet request = new HttpGet();
			this.setHeader(request);
			url = this.combineQeuryParams(url, queryParams);
			request.setURI(new URI(url));
			HttpResponse response = doRequest(request);
			result = this.readResultFromInputStream(response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void post(final String path, final String body, final Map<String, String> queryParams,final ResponseListener listener) {
		final String url = getUrl(path);
        Runnable r = new Runnable(){
			public void run() {
				Response result = null;
			BufferedReader reader = null;
			try {
				HttpPost request = new HttpPost();
				setHeader(request);
				request.setURI(new URI(url));
				if(body != null) {
					queryParams.put("body", body);
				}	
				UrlEncodedFormEntity fe = combinePostParams(queryParams);
				request.setEntity(fe);
				HttpResponse response = doRequest(request);
				result = readResultFromInputStream(response);
				listener.onResponse(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
						reader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}}
		};	
	}
	
	public  void  get(final String path, final Map<String, String> queryParams,final ResponseListener listener) {
		final String url = getUrl(path);
		Runnable r = new Runnable(){
			public void run() {
				Response result = null;
				BufferedReader reader = null;
				try {
					HttpGet request = new HttpGet();
					setHeader(request);
					String u = combineQeuryParams(url, queryParams);
					request.setURI(new URI(u));
					HttpResponse response = doRequest(request);
					result = readResultFromInputStream(response);
					listener.onResponse(result);
				} catch (Exception e) {
					e.printStackTrace();
					listener.onResponse(new Response(false,"���������쳣����ȷ֤����ֻ��Ƿ������������������"));
				} finally {
					if (reader != null) {
						try {
							reader.close();
							reader = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}		
			}
		};
		
	}

	public Response post(String path,String body, Map<String, String> queryParams) {
		final String url = getUrl(path);
		Response result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			this.setHeader(request);
			request.setURI(new URI(url));
			if(body != null) {
				queryParams.put("body", body);
			}			
			UrlEncodedFormEntity fe = this.combinePostParams(queryParams);
			request.setEntity(fe);
			HttpResponse response = doRequest(request);
			result = this.readResultFromInputStream(response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private HttpResponse doRequest(HttpRequestBase request) throws ClientProtocolException,IOException{
		 try {
			return client.execute(request);
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
		}
		return null;
	}
	
	
	public static interface ResponseListener {
		public void onResponse(Response resp);
	}
	
	private UrlEncodedFormEntity combinePostParams(Map<String, String> queryParams) {
		if (queryParams == null || queryParams.isEmpty()) {
			return null;
		}
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> e : queryParams.entrySet()) {
			postParameters.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(postParameters,CHARSET);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return formEntity;
	}
	
	
	private String combineQeuryParams(String url,
			Map<String, String> queryParams) {
		if (queryParams == null || queryParams.isEmpty()) {
			return url;
		}
		StringBuffer sb = new StringBuffer(url);
		sb.append("?");
		for (Map.Entry<String, String> e : queryParams.entrySet()) {
			sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	public synchronized HttpClient getHttpClient() {
        HttpParams params =new BasicHttpParams();
        // ����һЩ��������
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
      /*  HttpProtocolParams
                .setUserAgent(
                        params,
                        "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                                +"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");*/
        // ��ʱ����
        /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
        ConnManagerParams.setTimeout(params, 1000);
        /* ���ӳ�ʱ */
        HttpConnectionParams.setConnectionTimeout(params, 1000*10);
        /* ����ʱ */
        HttpConnectionParams.setSoTimeout(params, 1000*30);
        
        // �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
       /* SchemeRegistry schReg =new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));
        */
        // ʹ���̰߳�ȫ�����ӹ���������HttpClient
        //ClientConnectionManager conMgr =new ThreadSafeClientConnManager(params, schReg);
        return new DefaultHttpClient(/*conMgr,*/ params);
    
    }
	
	private Response readResultFromInputStream(HttpResponse response) {
		Response result = null;
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			result = new Response();
			result.setSuccess(false);
			result.setMsg(response.getStatusLine().toString());
			return result;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			String v = strBuffer.toString();
			result = JsonUtils.getInstance().fromJson(v,Response.class,true,false);					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
