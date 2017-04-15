package br.ufpe.nti.util;

import java.time.LocalTime;

public class AngleCalculator {

	public static double execute(LocalTime c) {
		// calcula o angulo da hora do cliente
		// Primeira parte é a movimentação das horas e
		// a segunda a movimentação do ponteiro da hora em função dos minutos
		int horaModificada;

		if (c.getHour() > 12) {
			horaModificada = c.getHour() - 12;
		} else {
			horaModificada = c.getHour();
		}

		double ang1P1 = (horaModificada * 30) + ((c.getMinute() * 30) / 60);
		System.out.println(ang1P1);

		// calcula movimentaçao do ponteiro dos minutos
		double ang1P2 = 360 * c.getMinute() / 60;
		System.out.println(ang1P2);

		// calcula o complemento da diferença entre o ponteiro da hora - minutos
		double angulo = (360 - (ang1P2 - ang1P1));
		return angulo;
	}

}
