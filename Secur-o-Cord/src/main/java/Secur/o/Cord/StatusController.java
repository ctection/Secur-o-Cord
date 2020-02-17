package Secur.o.Cord;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
	
	@GetMapping("/status")
	public String getStatus(@RequestParam(value = "echo", defaultValue = "") String echo) {
		
		if(!echo.equals("")) {
			return echo;
		}
		return "Hello World";
		
	}
}
