package com.developer.tonny.design;

/**
 * Created by root on 21/07/17.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;

public class Conexion {

    public String loginSELECT(String url, String user,String password)
    {
        try
        {
            HttpClient cliente = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> datos = new ArrayList<NameValuePair>();
            datos.add(new BasicNameValuePair("user",user));
            datos.add(new BasicNameValuePair("pass",password));

            httppost.setEntity(new UrlEncodedFormEntity(datos));
            HttpResponse response = cliente.execute(httppost);

            String responseString= EntityUtils.toString(response.getEntity());
            return responseString;
        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

}
