package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.google.gson.Gson;

import model.PublicWifiInfo;
import model.PublicWifiInfo.Row;
import model.PublicWifiInfo.TbPublicWifiInfo;
import model.Wifi;

public class Api {

	private static final String KEY = "74664261736f6f393731714449467a";
	private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/";
	private static int count = 0;

	public static int getTotalCount() throws ClientProtocolException, IOException {
		int start = 1;
		int end = 1;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(BASE_URL + start + "/" + end);

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
				PublicWifiInfo tbPublicWifiInfo = gson.fromJson(jsonResponse, PublicWifiInfo.class);
				count = tbPublicWifiInfo.getTbPublicWifiInfo().getList_total_count();
			} else {
				System.out.println("HTTP 요청이 실패했습니다. 상태 코드: " + statusCode);
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return count;
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
				System.out.println(tbPublicWifiInfo.getTbPublicWifiInfo().getRow());
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

	public static void main(String[] args) throws Exception {
		int count = Api.getTotalCount();
		System.out.println(count + "count");
		PublicWifiInfo sInfo = Api.getWifiInfo(100, 300);
		List<Row> list = sInfo.getTbPublicWifiInfo().getRow();
		for (Row row : list) {
			System.out.println(row.getX_SWIFI_ADRES1());
		}
	}
}
