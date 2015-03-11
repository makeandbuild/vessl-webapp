package com.makeandbuild.vessl.sample.rest;

import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.makeandbuild.vessl.fixture.Fixture;
import com.makeandbuild.vessl.sample.persistence.EventDao;

@Test(groups = {"integration"})
public class EventResourceTest extends BaseResourceTest {

    static String eventId;

    @Autowired
    EventDao eventDao;
    @Autowired
    Fixture fixture;

    @BeforeMethod
    public void buildUp() throws Exception {
        setUp();
    }

    @AfterMethod
    public void tearDown() throws RemoteException {
        if(eventId != null) {
            eventDao.deleteById(eventId);
        }
    }

    @Test
    @SuppressWarnings("unused")
    public void getEvents() throws Exception {

        HttpGet request = new HttpGet(urlUtil.toUrl("/rest/events"));
        setHeaders(request);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        JSONObject responsePayload = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(entity.getContent())));
        Assert.assertNotNull(responsePayload);

    }

    @Test
    public void getEventForId1() throws Exception {

        JSONObject responsePayload = getRequest(urlUtil.toUrl("/rest/events/1231231231-12312312-12-3123123"));

        System.out.println(responsePayload.toString(2));
        Assert.assertNotNull(responsePayload);

        String type = responsePayload.getString("type");
        Assert.assertNotNull(type);
    }

    @Test
    public void getDraftEventsForUser1() throws Exception {

        JSONObject responsePayload = getRequest(urlUtil.toUrl("/rest/events?type=user.loggedin"));

        System.out.println(responsePayload.toString(2));
        Assert.assertNotNull(responsePayload);
        JSONArray itemsArray = responsePayload.getJSONArray("items");
        int count = itemsArray.length();
        for(int x = 0; x < count; x++)
        {
            JSONObject item = itemsArray.getJSONObject(x);
            String type = item.getString("type");
            Assert.assertEquals(type, "user.loggedin");
        }

    }

	@Test(enabled = true)
	public void insertEventTest() throws Exception {

		JSONObject responsePayload = createEvent();
		//System.out.println(responsePayload.toString(2));
		Assert.assertNotNull(responsePayload);

		String id = responsePayload.getString("id");
		Assert.assertEquals(id, eventId);
		String type = responsePayload.getString("type");
		Assert.assertEquals(type, "user.loggedout");
	}

	public JSONObject createEvent() throws Exception {
		HttpPost request = new HttpPost(urlUtil.toUrl("/rest/events"));
		setHeaders(request);

        String id = UUID.randomUUID().toString();
        String payloadString = "{id:\""+id+"\"," + 
        						"\"type\" : \"user.loggedout\"}";

		JSONObject payload = new JSONObject(payloadString);

		StringEntity requestPayload = new StringEntity(payload.toString());
		request.setEntity(requestPayload);

		HttpResponse response = httpclient.execute(request, localContext);

		StatusLine status = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		JSONObject responsePayload = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(entity.getContent())));
		Assert.assertNotNull(responsePayload);
		//logger.info("Insert event response: " + responsePayload.toString(2));
		Assert.assertEquals(status.getStatusCode(), 201);
		Assert.assertNotNull(responsePayload);

		eventId = responsePayload.getString("id");
		Assert.assertNotNull(eventId);

		// verify the location header has the url to the newly created instance
		Header locationHeader = response.getFirstHeader("Location");
		String locationUrl = locationHeader.getValue();

		responsePayload = getRequest(locationUrl);

		return responsePayload;
	}

    @Test
    public void errorInsertingEventTest() throws Exception {

        String url = urlUtil.toUrl("/rest/events");
        HttpPost request = new HttpPost(url);
        setHeaders(request);

        String id = UUID.randomUUID().toString();
        // missing type
        String payloadString = "{id:\""+id+"\"}";

        JSONObject payload = new JSONObject(payloadString);

        StringEntity requestPayload = new StringEntity(payload.toString());
        request.setEntity(requestPayload);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        JSONObject responsePayload = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(entity.getContent())));
        Assert.assertNotNull(responsePayload);
        logger.info("Insert event response: " + responsePayload.toString(2));
        Assert.assertEquals(status.getStatusCode(), 400);
        Assert.assertNotNull(responsePayload);
        Assert.assertNotNull(responsePayload.getString("message"));
    }

}
