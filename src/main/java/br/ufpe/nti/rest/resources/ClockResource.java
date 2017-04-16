package br.ufpe.nti.rest.resources;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpe.nti.controller.repository.ClockHistoryRepository;
import br.ufpe.nti.model.Clock;
import br.ufpe.nti.util.AngleCalculator;
import br.ufpe.nti.util.ToLocalTime;

@Controller
public class ClockResource {

	@Autowired
	private ClockHistoryRepository history;

	// responde a questao 1 e 2
	@RequestMapping(value = "/clock", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Clock getClock() {

		Date d = new Date(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		String s = d.getHours() + ":" + d.getMinutes();
		Clock clock = createClockByHour(s);
		return clock;
	}

	// Responde a questao 3, 4 e 5
	@RequestMapping(value = "/clock", method = RequestMethod.POST)
	public @ResponseBody Clock postClock(@RequestParam("time") String hour) {
		Clock clock = createClockByHour(hour);
		history.save(clock);
		return clock;
	}

	// Responde a questao 6 e 7
	@RequestMapping(value = "/clockhistory", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Clock> getClocks() {
		List<Clock> all = history.listAll();
		return all;
	}

	// Responde a questao 8 e 9
	@RequestMapping(value = "/clockhistoy/{idHistoryClock}", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public Clock getClockById(@PathVariable("id") Long id) {
		Clock find = history.find(id);
		return find;
	}

	
	
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String verifyRESTService() {
		String result = "RESTService Successfully started..";
		return result;
	}

	private Clock createClockByHour(String hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date data = null;
		try {
			data = sdf.parse(hour);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Clock c = new Clock();
		// horario da requisicao cliente
		c.setTime(ToLocalTime.fromDate(data));
		// calcula angulo
		c.setAngle(AngleCalculator.execute(c.getTime()));
		// horario da criacao do recurso
		c.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		return c;
	}
}
