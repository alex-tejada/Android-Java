package com.example.root.aplicacionbasedatos;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by root on 19/07/17.
 */

public class Conexion
{

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

    public String recordsSELECT(String url, String user)
    {
        try
        {
            HttpClient cliente = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> datos = new ArrayList<NameValuePair>();
            datos.add(new BasicNameValuePair("user",user));

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

    public String recordsINSERT(String url, String user,String date,String stepAmount,String calorieAmount,String travelledAmount)
    {
        try
        {
            HttpClient cliente = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> datos = new ArrayList<NameValuePair>();
            datos.add(new BasicNameValuePair("user",user));
            datos.add(new BasicNameValuePair("date",date));
            datos.add(new BasicNameValuePair("stepAmount",stepAmount));
            datos.add(new BasicNameValuePair("calorieAmount",calorieAmount));
            datos.add(new BasicNameValuePair("travelledAmount",travelledAmount));

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

    public String userInfoUPDATE(String url, String user,String pass)
    {
        try
        {
            HttpClient cliente = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> datos = new ArrayList<NameValuePair>();
            datos.add(new BasicNameValuePair("user",user));
            datos.add(new BasicNameValuePair("pass",pass));

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

    public Document getXML(String xmlURL)
    {
        try
        {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(xmlURL);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(response));
            Document doc = db.parse(is);

            return doc;
        }
        catch (Exception e)
        {
            Log.d("ERROR:  ", e.toString());
            return null;
        }
    }
}

