package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.google.gson.Gson;

import model.PublicWifiInfo;
import model.PublicWifiInfo.Row;
import model.Wifi;

public class Api {

	private static final String KEY = "74664261736f6f393731714449467a";
	private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/";
	private static List<Wifi> wifiList;
	public static int result = 0;

	public static List<Wifi> sendGet(int start, int end) throws ClientProtocolException, IOException {
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
				result = tbPublicWifiInfo.getTbPublicWifiInfo().getList_total_count();

				wifiList = new ArrayList<>();

				for (Row row : tbPublicWifiInfo.getTbPublicWifiInfo().getRow()) {
					Wifi wifi = new Wifi();
					wifi.setManagerNumber(row.getX_SWIFI_MGR_NO());
					wifi.setWardOffice(row.getX_SWIFI_WRDOFC());
					wifiList.add(wifi);
				}

				System.out.println(result);
			} else {
				System.out.println("HTTP 요청이 실패했습니다. 상태 코드: " + statusCode);
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return wifiList;
	}

	public static void main(String[] args) throws Exception {
		List<Wifi> wifiList = sendGet(1, 2);
		for (Wifi w : wifiList) {
			System.out.println(w.getManagerNumber());
			System.out.println(w.getWardOffice());
		}
	}
}
