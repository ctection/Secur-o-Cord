package Secur.o.Cord.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.lang.ClassLoader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PatchChannel {
	
	@RequestMapping(value = "/api/channels/{id}", method = RequestMethod.PATCH, consumes = "application/json")
	public ResponseEntity<String> getChannels(@PathVariable("id") String id, HttpServletRequest request, @RequestBody String postPayload) throws IOException {
		
		// PATCH /channels/{id}
		// Requires Authorization
		// Returns Channel object
		
		// REQUIRES APACHE HTTPCLIENT DUE TO STOCK HTTPURLCLIENT NOT SUPPORTING PATCH METHOD
		
		String api_key = request.getHeader("Authorization");
		
        HttpPatch client = new HttpPatch("https://discord.com/api/channels/"+id);
        
        StringEntity requestEntity = new StringEntity(
        	    postPayload,
        	    ContentType.APPLICATION_JSON);
        
        client.addHeader("content-type", "application/json");
        client.addHeader("Authorization", api_key);
        
        client.setEntity(requestEntity);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        HttpResponse rawResponse = httpclient.execute(client);
        
		String api_result = new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()))
				  .lines().collect(Collectors.joining("\n"));
		
		return ResponseEntity.status(HttpStatus.resolve(rawResponse.getStatusLine().getStatusCode())).body(api_result);
		
	}
	

	
}
