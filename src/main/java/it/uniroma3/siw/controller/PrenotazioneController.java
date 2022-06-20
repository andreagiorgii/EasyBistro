package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
		return "";
	}

	//Validazione form prenotazione Utente
	@PostMapping("user/prenotazione")
	public String prenotazioneUser(@Valid @ModelAttribute("prenotazioneForm") Prenotazione prenotazione,
			BindingResult prenotazioneBindingResult, 
			Model model ) {
		
		User loggedUser = sessionData.getLoggedUser();

		this.prenotazioneValidator.validate(prenotazione,prenotazioneBindingResult);

		if(!prenotazioneBindingResult.hasErrors()) {
			prenotazione.setUsers(loggedUser);
			if(prenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				prenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			}else prenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);
			
			prenotazioneService.save(prenotazione);
			
			model.addAttribute("loggedUser", loggedUser);
			return "prenotazioneSuccessful";
		}


		return "La stessa vista che ritorna il metodo sopra";
	}


}
