package br.ufpe.nti.rest.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datetime")
final class DateTimeController {

	@RequestMapping(method = RequestMethod.GET)
	public void processDateTime(
			@RequestParam("date") String date) {
		System.out.println(date);
	}
}
