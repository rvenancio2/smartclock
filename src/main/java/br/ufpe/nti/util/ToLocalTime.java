package br.ufpe.nti.util;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ToLocalTime {

	@SuppressWarnings("deprecation")
	public static LocalTime fromDate(Date object) {
		return LocalTime.of(object.getHours(), object.getMinutes());
	}

	@SuppressWarnings("deprecation")
	public static LocalTime fromCalendar(Calendar object) {
		return LocalTime.of(object.getTime().getHours(), object.getTime().getMinutes());
	}

}
