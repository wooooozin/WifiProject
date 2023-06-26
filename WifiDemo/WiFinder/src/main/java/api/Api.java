package api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.jws.soap.SOAPBinding;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

public class Api {
    final static String KEY = "74664261736f6f393731714449467a";
    final static String BASE_URL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/";

    public static void getWifiInfo(int s, int e) {
        
    }

    public static void main(String[] args) {

        getWifiInfo(1, 10);
    }
}
