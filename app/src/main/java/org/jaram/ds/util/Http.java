package org.jaram.ds.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kjydiary on 15. 9. 6..
 */
public class Http {

    public static String get() {
        return "";
    }

    public static String post() {
        return "";
    }

    public static String put() {
        return "";
    }

    public static String delete() {
        return "";
    }

    public static String request(String method, String addr) {
        String result = "";
        try {
            URL url = new URL(addr);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod(method);
            httpCon.setConnectTimeout(5000);
            if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader (new InputStreamReader(httpCon.getInputStream()));
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    result += line;
                }
                br.close();
            }
            result = "{\"result\":\"error\", \"error\":\""+httpCon.getResponseCode()+"\"}";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            result = "{\"result\":\"error\", \"error\":\"데이터를 가져오는 도중 오류가 발생했습니다.\"}";
        }
        return result;
    }
}
