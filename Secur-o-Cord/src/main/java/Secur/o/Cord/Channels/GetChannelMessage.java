package Secur.o.Cord.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetChannelMessage {

	@RequestMapping(value = "/api/channels/{id}/messages/{mid}", method = RequestMethod.GET)
	public ResponseEntity<String> getChannelMessage(@PathVariable("id") String id, @PathVariable("mid") String message_id, HttpServletRequest request) throws IOException {
		
		// /channels/{id}/messages/{mid}
		// Requires Authorization
		// Returns Message object
		
		String api_key = request.getHeader("Authorization");
		
		String urlString = "https://discordapp.com/api/channels/"+id+"/messages/"+message_id;
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setRequestProperty("Authorization", api_key);
		
		httpCon.connect();
        
		InputStream is = null;
		if(httpCon.getResponseCode() >=400) {
			is = httpCon.getErrorStream();
		}else {
			is = httpCon.getInputStream();
		}
		
		String api_result = new BufferedReader(new InputStreamReader(is))
				  .lines().collect(Collectors.joining("\n"));
		
		return ResponseEntity.status(HttpStatus.resolve(httpCon.getResponseCode())).body(api_result);
		
	}
	
}
