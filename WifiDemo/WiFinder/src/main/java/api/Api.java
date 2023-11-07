package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.google.gson.Gson;

import model.PublicWifiInfo;
import model.PublicWifiInfo.Row;
import model.PublicWifiInfo.TbPublicWifiInfo;
import model.Wifi;

public class Api {

	private static final String KEY = "74664261736f6f393731714449467a";
	private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/";
	public static int count = 0;

	public static int getTotalCount() throws IOException, ParseException {
	    int start = 1;
	    int end = 1;
	    final int MAX_RETRIES = 5;
	    int retries = 0;

	    String url = BASE_URL + start + "/" + end;

	    while (retries < MAX_RETRIES) {
	        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	            HttpGet httpGet = new HttpGet(url);
	            return executeRequestForCount(httpClient, httpGet);
	        } catch (ConnectException e) {
	            retries++;
	            if (retries >= MAX_RETRIES) {
	                throw new IOException("서버에 연결할 수 없습니다. 최대 재시도 횟수를 초과했습니다.", e);
	            }
	            try {
	                Thread.sleep(1000 * retries);
	            } catch (InterruptedException ie) {
	                Thread.currentThread().interrupt();
	                throw new IOException("재시도 대기 중 인터럽트 발생", ie);
	            }
	        }
	    }
	    throw new IOException("서버에 연결할 수 없습니다. 최대 재시도 횟수를 초과했습니다.");
	}


	
	private static int executeRequestForCount(CloseableHttpClient httpClient, HttpGet httpGet) throws IOException, ParseException {
		try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
			int statusCode = httpResponse.getCode();
			if (statusCode >= 200 && statusCode < 300) {
				String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
				Gson gson = new Gson();
				PublicWifiInfo publicWifiInfo = gson.fromJson(jsonResponse, PublicWifiInfo.class);
				return publicWifiInfo.getTbPublicWifiInfo().getList_total_count();
			} else {
				throw new IOException("HTTP 요청에 실패했습니다. 상태코드 : " + statusCode);
			}
		}
	}

	public static PublicWifiInfo getWifiInfo(int start, int end) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(BASE_URL + start + "/" + end);
		PublicWifiInfo tbPublicWifiInfo = new PublicWifiInfo();

		try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
			int statusCode = httpResponse.getCode();
			System.out.println(statusCode);
			if (statusCode >= 200 && statusCode < 300) {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent())
				);

				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine).append(System.lineSeparator());
				}
				
				reader.close();
				
				Gson gson = new Gson();
				String jsonResponse = response.toString();
				tbPublicWifiInfo = gson.fromJson(jsonResponse, PublicWifiInfo.class);
			} else {
				System.out.println("HTTP 요청이 실패했습니다. 상태 코드: " + statusCode);
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return tbPublicWifiInfo;
	}
}
