package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.google.gson.Gson;

import model.TbPublicWifiInfo;
import model.TbPublicWifiInfo.Row;

public class Api {
	
	private static final String KEY = "74664261736f6f393731714449467a";
	private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/";
	
	/**
	 * @param start
	 * @param end
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void sendGet(int start, int end) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(BASE_URL + start + "/" + end);
        try (@SuppressWarnings("deprecation")
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
			System.out.println(httpResponse.getCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
 
			String inputLine;
			StringBuilder response = new StringBuilder();
 
			while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine).append(System.lineSeparator());
	        }
			
			reader.close();
//			System.out.println(response.toString());
			
			Gson gson = new Gson();
			String jsonResponse = response.toString();
			TbPublicWifiInfo tbPublicWifiInfo = gson.fromJson(jsonResponse, TbPublicWifiInfo.class);
			
			for (Row list : tbPublicWifiInfo.getTbPublicWifiInfo().getRow()) {
				System.out.println(list.getLAT());
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} finally {
	        httpClient.close();
		}
	}
	
    public static void main(String[] args) throws Exception {
    	sendGet(1, 10);
	}
}
