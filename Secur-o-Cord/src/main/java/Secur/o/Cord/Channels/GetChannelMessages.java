package Secur.o.Cord.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
public class GetChannelMessages {
	
	@RequestMapping(value = "/api/channels/{id}/messages", method = RequestMethod.GET)
	public ResponseEntity<String> getChannelMessages(@PathVariable("id") String id, HttpServletRequest request, @RequestParam(value = "around", defaultValue = "null") String around, @RequestParam(value = "before", defaultValue = "null") String before, @RequestParam(value = "after", defaultValue = "null") String after, @RequestParam(value = "limit", defaultValue = "50") int limit) throws IOException {
		
		// /channels/{id}/messages
		// Requires Authorization
		// Returns an array of Message objects
		
		String api_key = request.getHeader("Authorization");
		
		String argument_builder = "";
		
		if(!around.equals("null")) {
			argument_builder = argument_builder + "around="+around+"&";
		}
		if(!before.equals("null")) {
			argument_builder = argument_builder + "before="+before+"&";
		}
		if(!after.equals("null")) {
			argument_builder = argument_builder + "after="+after+"&";
		}
		
		argument_builder = argument_builder + "limit="+limit;
		
		String urlString = "https://discordapp.com/api/channels/"+id+"/messages?"+argument_builder;
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
