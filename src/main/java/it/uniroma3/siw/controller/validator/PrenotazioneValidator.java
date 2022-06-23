package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.PrenotazioneService;


@Component
public class PrenotazioneValidator implements Validator {

	@Autowired
	PrenotazioneService prenotazioneService;

	@Autowired
	SessionData sessionData;

	final int MAX_ORARIO_PRANZO = 14;
	final int MIN_ORARIO_PRANZO = 12;
	
	final int MAX_ORARIO_CENA = 21;
	final int MIN_ORARIO_CENA = 18;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Prenotazione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Prenotazione prenotazione = (Prenotazione) target;
		//		String luogo = prenotazione.getLuogo();
		//		Integer numeroPersone = prenotazione.getNumeroPersone();
		User loggedUser = sessionData.getLoggedUser();
		System.out.println(prenotazione.getOrario());
		
		if(prenotazione.getOrario()==null)
			errors.rejectValue("orario", "prenotazione.orario");

		
		if(!((prenotazione.getOrario().getHour() <= MAX_ORARIO_PRANZO && prenotazione.getOrario().getHour() >= MIN_ORARIO_PRANZO) 
				|| (prenotazione.getOrario().getHour() <= MAX_ORARIO_CENA && prenotazione.getOrario().getHour() >= MIN_ORARIO_CENA))) {

			errors.rejectValue("orario", "prenotazione.orario");
		}
		
		if(this.prenotazioneService.alreadyExists(prenotazione, loggedUser))
			errors.rejectValue("dataPrenotazione","prenotazione.duplicato");

	}
	//		if(prenotazione.getUsers().getId().equals(loggedUser.getId()) && this.prenotazioneService.alreadyExists(prenotazione) != null) {
	//		System.out.println(prenotazione.getUsers().getId().equals(loggedUser.getId()));
	//		errors.rejectValue("dataPrenotazione","prenotazione.duplicato");
	//		}

	//		if(luogo.equals(Prenotazione.INTERNO_POSTO) && !prenotazione.isPrenotabileInterno(numeroPersone)) {
	//		errors.rejectValue("numeroPersone","prenotazione.postiNonDisponibili");
	//		}
	//
	//		if(luogo.equals(Prenotazione.ESTERNO_POSTO) && !prenotazione.isPrenotabileEsterno(numeroPersone)) {
	//		errors.rejectValue("numeroPersone","prenotazione.postiNonDisponibili");
	//		}

}
