package br.ufpe.nti.rest.resources;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requestheaders")
public class RequestHeaders {

	@RequestMapping(headers = { "User-Agent", "Date" }, method = RequestMethod.GET)
	public void requestHTTPHeaders(@RequestHeader("User-Agent") String userAgent, @RequestHeader("date") String date) {
		String msg = "Trade request : " + userAgent + ", " + date;
		System.out.println(msg);
	}

}
