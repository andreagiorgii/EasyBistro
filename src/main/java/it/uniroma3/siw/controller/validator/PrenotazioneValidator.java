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

	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Prenotazione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Prenotazione prenotazione = (Prenotazione) target;
		String luogo = prenotazione.getLuogo();
		Integer numeroPersone = prenotazione.getNumeroPersone();
		User loggedUser = sessionData.getLoggedUser();
		
		if(prenotazione.getUsers().equals(loggedUser) && this.prenotazioneService.alreadyExists(prenotazione) != null);
		errors.rejectValue("dataPrenotazione","prenotazione.duplicato");
		
		
		if(luogo.equals(Prenotazione.INTERNO_POSTO) && !prenotazione.isPrenotabileInterno(numeroPersone)) {
		errors.rejectValue("numeroPersone","prenotazione.postiNonDisponibili");
		}
		
		if(luogo.equals(Prenotazione.ESTERNO_POSTO) && !prenotazione.isPrenotabileEsterno(numeroPersone)) {
		errors.rejectValue("numeroPersone","prenotazione.postiNonDisponibili");
		}
		
	}
}
