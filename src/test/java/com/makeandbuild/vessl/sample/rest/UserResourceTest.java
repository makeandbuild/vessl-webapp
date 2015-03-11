package com.makeandbuild.vessl.sample.rest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.makeandbuild.vessl.fixture.Fixture;
import com.makeandbuild.vessl.sample.domain.User;
import com.makeandbuild.vessl.sample.domain.UserType;
import com.makeandbuild.vessl.sample.persistence.UserDao;


@Test(groups = { "integration" })
public class UserResourceTest extends BaseResourceTest {
    @Autowired
    Fixture fixture;

    @Autowired
    UserDao userDao;

    @BeforeMethod
    public void setup() throws Exception{
        super.setUp();
    }

    @Test
    public void testValidationException() throws ClientProtocolException, IOException {
        userDao.deleteById(9999L);
        User user= new User();
        user.setId(9999L);
        user.setCreatedAt(new Date());
        user.setLatitude(33.801078);
        user.setLongitude(-84.436287);
        user.setLoginCount(1);
        user.setUsername("azuercher");
        user.setUserType(UserType.simple);
        
        HttpPost request = new HttpPost(urlUtil.toUrl("/rest/users"));
        setHeaders(request);

        String json = new ObjectMapper().writeValueAsString(user);
        StringEntity requestPayload = new StringEntity(json);
        request.setEntity(requestPayload);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        Assert.assertEquals(status.getStatusCode(), 400);
        ObjectNode responseNode = (ObjectNode) new ObjectMapper().readTree(response.getEntity().getContent());
        assertNotNull(responseNode);
        ArrayNode errors = (ArrayNode)responseNode.get("errors");
        assertNotNull(errors);
        ObjectNode error = (ObjectNode)errors.get(0);
        assertNotNull(error);
        assertEquals(error.get("defaultMessage").asText(), "Username already taken");
    }
    @Test
    public void testNoValidationException() throws ClientProtocolException, IOException {
        userDao.deleteById(9999L);
        User user = new User();
        user.setId(9999L);
        user.setCreatedAt(new Date());
        user.setLatitude(33.801078);
        user.setLongitude(-84.436287);
        user.setLoginCount(1);
        user.setUsername("azuercher9999");
        user.setUserType(UserType.simple);

        HttpPost request = new HttpPost(urlUtil.toUrl("/rest/users"));
        setHeaders(request);

        String json = new ObjectMapper().writeValueAsString(user);
        StringEntity requestPayload = new StringEntity(json);
        request.setEntity(requestPayload);

        HttpResponse response = httpclient.execute(request, localContext);

        StatusLine status = response.getStatusLine();
        Assert.assertEquals(status.getStatusCode(), 201);
        ObjectNode responseNode = (ObjectNode) new ObjectMapper().readTree(response.getEntity().getContent());
        String link = response.getFirstHeader("Location").getValue();
        assertEquals(link, urlUtil.toUrl("/rest/users/9999"));
        assertEquals(9999L, responseNode.get("id").asLong());
        assertEquals("azuercher9999", responseNode.get("username").asText());
        userDao.deleteById(9999L);
    }
}
