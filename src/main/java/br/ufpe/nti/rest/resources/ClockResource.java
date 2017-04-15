package br.ufpe.nti.rest.resources;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpe.nti.controller.repository.ClockHistoryRepository;
import br.ufpe.nti.model.Clock;
import br.ufpe.nti.util.AngleCalculator;
import br.ufpe.nti.util.ToLocalTime;

@Controller
@RequestMapping("/clock")
public class ClockResource {

	@Autowired(required = true)
	private HttpServletRequest request;

	// responde a questao 1 e 2
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Clock getClock(@RequestParam("date") String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date data = null;
		try {
			data = sdf.parse(date);
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

		System.out.println(c.getCreatedAt().getTime());

		ClockHistoryRepository repository = new ClockHistoryRepository();
		//TODO com problema para salvar
		//repository.save(c);

		return c;
	}

	// Responde a questao 3
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody FormatedHour postClock(@RequestParam("date") String date) {
		FormatedHour hour = new FormatedHour(date);
		return hour;
	}

}
