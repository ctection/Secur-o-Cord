package Secur.o.Cord.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetReactions {
	
	@RequestMapping(value = "/api/channels/{id}/messages/{mid}/reactions/{emoji}", method = RequestMethod.GET)
	public ResponseEntity<String> getReactions(@PathVariable("id") String id, @PathVariable("mid") String message_id, @PathVariable("emoji") String emoji, HttpServletRequest request) throws IOException {
		
		// /channels/{id}
		// Requires Authorization
		// Returns Channel object from Channel ID
		
		String api_key = request.getHeader("Authorization");
		
		String urlString = "https://discordapp.com/api/channels/"+id+"/messages/"+message_id+"/reactions/"+URLEncoder.encode(emoji).replace("+", "%20");
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
