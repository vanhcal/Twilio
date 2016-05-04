
package utils;

import java.io.*;
import java.net.*;
import java.util.*;

public class GA  {
    public static void sendToGa(String page, String title) {

        try {

            URL url = new URL("https://www.google-analytics.com/batch");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("v", "1");
            params.put("tid", new Credentials().getGaToken());
            params.put("cid", "555");
            params.put("t", "pageview");
            params.put("dh", "mydemo.com");
            params.put("dp", page);
            params.put("dt", title);


            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for ( int c = in.read(); c != -1; c = in.read() )
                System.out.print((char)c);
        }
        catch (Exception e) {e.printStackTrace();
        }
    }
}
