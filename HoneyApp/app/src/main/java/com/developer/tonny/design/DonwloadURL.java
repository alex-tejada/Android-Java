package com.developer.tonny.design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 24/07/17.
 */

public class DonwloadURL {

    public String readURL(String url) throws IOException {

        String data= "";
        InputStream is = null;
        HttpURLConnection ulrCon = null;

        try
        {
            URL urlObj = new URL(url);
            ulrCon = (HttpURLConnection) urlObj.openConnection();
            ulrCon.connect();

            is = ulrCon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            String line="";

            while((line = br.readLine())!=null){
                    sb.append(line);
                    }

                    data = sb.toString();
                    br.close();

                    }
                    catch (MalformedURLException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    finally
                    {
                    is.close();
                    ulrCon.disconnect();
                    }
                    return data;
                    }
}
