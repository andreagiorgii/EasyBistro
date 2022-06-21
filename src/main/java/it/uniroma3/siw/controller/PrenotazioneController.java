package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.PrenotazioneService;

@Controller
public class PrenotazioneController {

	@Autowired
	SessionData sessionData;

	@Autowired
	PrenotazioneValidator prenotazioneValidator;

	@Autowired
	PrenotazioneService prenotazioneService;



	//Vista Utente per la form di prenotazione 
	@GetMapping("user/prenotazione")
	public String showFormPrenotazione(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("prenotazioneForm", new Prenotazione());
		return "prenotazione_user";
	}

	//Validazione form prenotazione Utente
	@PostMapping("user/prenotazione")
	public String prenotazioneUser(@Valid @ModelAttribute("prenotazioneForm") Prenotazione prenotazione, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateAndTime,
			BindingResult prenotazioneBindingResult, 
			Model model ) {
		
		User loggedUser = sessionData.getLoggedUser();
		prenotazione.setUsers(loggedUser);
		//this.prenotazioneValidator.validate(prenotazione,prenotazioneBindingResult);

		if(!prenotazioneBindingResult.hasErrors()) {
			if(prenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				prenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			}else prenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);
			prenotazioneService.save(prenotazione);
			return "prenotazione_successful";
		}
		model.addAttribute("loggedUser", loggedUser);
		return "prenotazione_user";
	}


}
