package Secur.o.Cord.Guilds;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditLogs {
	
	@RequestMapping("/api/guilds/{id}/audit-logs")
	public ResponseEntity<String> getAuditLogs(@PathVariable("id") String id, @RequestParam(value = "user_id", defaultValue = "null") String user_id, @RequestParam(value = "action_type", defaultValue = "null") String action_type, @RequestParam(value = "before", defaultValue = "null") String before, @RequestParam(value = "limit", defaultValue = "50") int limit, HttpServletRequest request) throws IOException {
		
		// /guilds/{id}/audit-logs
		// Requires Authorization
		// PARAMETERS:
		// String: user_id
		// int: action_type
		// String: before
		// int: limit
		
		String api_key = request.getHeader("Authorization");
		
		String arg_builder = "";
		if(!user_id.trim().equals("null")) {
			arg_builder = arg_builder + "user_id="+URLEncoder.encode(user_id)+"&";
		}
		if(!action_type.trim().equals("null")) {
			arg_builder = arg_builder + "action_type="+URLEncoder.encode(action_type)+"&";
		}
		if(!before.trim().equals("null")) {
			arg_builder = arg_builder + "before="+URLEncoder.encode(before)+"&";
		}
		arg_builder = arg_builder + "limit="+limit;
		String urlString = "https://discordapp.com/api/guilds/554675435309629451/audit-logs?"+arg_builder;
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
