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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteMessageReaction {
	
	@RequestMapping(value = "/api/channels/{id}/messages/{mid}/reactions/{emoji}/{target}", method = RequestMethod.DELETE)
	public ResponseEntity<String> sendMessage(@PathVariable("id") String id,@PathVariable("mid") String message_id,@PathVariable("target") String target,@PathVariable("emoji") String emoji, HttpServletRequest request) throws IOException {
		
		// DELETE /channels/{id}/messages/{mid}/reactions/{emoji}/{target}
		// TARGET either @me or User ID
		// Requires Authorization
		// Returns 204 Emtpy Success
		
		String api_key = request.getHeader("Authorization");
		
		System.out.println(emoji);
		
		String urlString = "https://discordapp.com/api/channels/"+id+"/messages/"+message_id+"/reactions/"+URLEncoder.encode(emoji).replace("+", "%20")+"/"+target; //WORKING AROUND SPACE ENCODING BUG
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setRequestProperty("Authorization", api_key);
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
		
		System.out.println(URLEncoder.encode(emoji).replace("+", "%20"));
		
        
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
