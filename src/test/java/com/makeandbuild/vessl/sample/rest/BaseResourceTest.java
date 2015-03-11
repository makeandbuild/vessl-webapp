package com.makeandbuild.vessl.sample.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.FileCopyUtils;

import com.makeandbuild.vessl.sample.util.UrlUtil;

@SuppressWarnings("deprecation")
@ContextConfiguration(locations={"classpath*:applicationContext*.xml"})

public class BaseResourceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    protected UrlUtil urlUtil;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected HttpClient httpclient;
    protected CookieStore cookieStore;
    protected HttpContext localContext;

    public void setUp() throws Exception {
        cookieStore = new BasicCookieStore();
        cookieStore.clear();
        localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        httpclient = new DefaultHttpClient();
    }


    protected void setHeaders(HttpRequestBase request) {
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "*/*");
    }

    

    protected JSONObject getRequest(String url) throws Exception {

        System.out.println("\nSending 'GET' request to URL : " + url);
        HttpGet request = new HttpGet(url);
        setHeaders(request);

        HttpResponse response = httpclient.execute(request, localContext);

        HttpEntity entity = response.getEntity();
        String json = FileCopyUtils.copyToString(new InputStreamReader(entity.getContent()));
        JSONObject responsePayload = new JSONObject(json);

        return responsePayload;
    }

    protected JSONObject postRequest(String url, String payloadString, String token) throws Exception {
        HttpResponse response = postRequestAsResponse(url, payloadString, token);
        return getPayload(response);
    }
    protected HttpResponse postRequestAsResponse(String url, String payloadString, String token) throws Exception {

        System.out.println("\nSending 'POST' request to URL : " + url);
        HttpPost request = new HttpPost(url);
        setHeaders(request);

        JSONObject payload = new JSONObject(payloadString);

        StringEntity requestPayload = new StringEntity(payload.toString());
        request.setEntity(requestPayload);

        HttpResponse response = httpclient.execute(request, localContext);
        return response;
    }
    protected JSONObject getPayload(HttpResponse response) throws JSONException, IllegalStateException, IOException{
        HttpEntity entity = response.getEntity();
        JSONObject responsePayload = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(entity.getContent())));
        return responsePayload;
    }

    protected int putRequest(String url, String payloadString) throws Exception {

        System.out.println("\nSending 'PUT' request to URL : " + url);
        HttpPut request = new HttpPut(url);
        setHeaders(request);

        JSONObject payload = new JSONObject(payloadString);

        StringEntity requestPayload = new StringEntity(payload.toString());
        request.setEntity(requestPayload);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        System.out.println("response: " + status);

        return status.getStatusCode();
    }

    protected int deleteRequest(String url) throws Exception {

        System.out.println("\nSending 'DELETE' request to URL : " + url);
        HttpDelete request = new HttpDelete(url);
        setHeaders(request);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        System.out.println("response: " + status);

        return status.getStatusCode();
    }

    protected JSONObject uploadFile(String url, File file, String fileType) throws Exception {

        System.out.println("\nUploading file: " + file.getName() + " to URL : " + url);
        HttpPost request = new HttpPost(url);
        MultipartEntity multipartEntity = new MultipartEntity();
        FileBody fileBody = new FileBody(file, fileType);
        multipartEntity.addPart("files[]", fileBody);
        request.setEntity(multipartEntity);

        HttpResponse response = httpclient.execute(request, localContext);

        HttpEntity entity = response.getEntity();
        System.out.println("response: " + response);
        JSONObject responsePayload = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(entity.getContent())));

        return responsePayload;
    }
}
