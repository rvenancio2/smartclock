package br.ufpe.nti.rest.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatedHour {
	String time = null;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@SuppressWarnings("deprecation")
	public FormatedHour(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date data = null;

		try {
			data = sdf.parse(date);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(data.getHours());
			stringBuilder.append(":");
			stringBuilder.append(data.getMinutes());
			time = stringBuilder.toString();
			
		} catch (ParseException e) {
			e.printStackTrace();
			
		}

	}

}
