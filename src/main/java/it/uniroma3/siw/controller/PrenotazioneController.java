package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.UserService;

@Controller
public class PrenotazioneController {

	@Autowired
	SessionData sessionData;

	@Autowired
	PrenotazioneValidator prenotazioneValidator;

	@Autowired
	PrenotazioneService prenotazioneService;


	@Autowired
	UserService userService;


	//Get-Insert Utente -> Vista Utente per la form di prenotazione 

	@GetMapping("user/prenotazione")
	public String showFormPrenotazione(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("prenotazioneForm", new Prenotazione());
		return "prenotazione_user";
	}

	//Post-Insert Utente -> Validazione form prenotazione Utente
	@PostMapping("user/prenotazione")
	public String prenotazioneUser(@Valid @ModelAttribute("prenotazioneForm") Prenotazione prenotazione,
			BindingResult prenotazioneBindingResult, Model model) {

		User loggedUser = sessionData.getLoggedUser();
		prenotazione.setUsers(loggedUser);

		//this.prenotazioneValidator.validate(prenotazione, prenotazioneBindingResult);

		if (!prenotazioneBindingResult.hasErrors()) {
			if (prenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				prenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			} else
				prenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);
			prenotazioneService.save(prenotazione);
			return "prenotazione_successful";
		}
		model.addAttribute("loggedUser", loggedUser);
		return "prenotazione_user";
	}



	//Delete Utente -> cancellazione di una prenotazione da parte di un Utente

	@PostMapping("user/{id}/delete")
	public String deletePrenotazioneUtente(@Valid @PathVariable Long id, Model model) {
		this.prenotazioneService.delete(id);
		return "Ritorna alla stessa pagina";
	}

  
	//GET-Update Utente -> aggiornamento di una prenotazione da parte di un Utente
	@GetMapping("user/{id}/update")
	public String updatePrenotazioneUserForm(@Valid @PathVariable Long id, Model model){
		User loggedUser = sessionData.getLoggedUser();
		Prenotazione prenotazione = this.prenotazioneService.findById(id);
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("loggedPrenotazione", prenotazione);
		model.addAttribute("newPrenotazione", new Prenotazione());
		return "updatePrenotazioneUserForm";
	}

	
	//Post-Update Utente -> aggiornamento di una prenotazione da parte di un Utente
	@PostMapping("user/{id}/update")
	public String updatePrenotazione(@Valid @ModelAttribute("newPrenotazione") Prenotazione newPrenotazione,@PathVariable Long id,
			BindingResult prenotazioneBindingResult, 
			Model model ) {
		
		User loggedUser = sessionData.getLoggedUser();
		newPrenotazione.setUsers(loggedUser);

		this.prenotazioneValidator.validate(newPrenotazione,prenotazioneBindingResult);
		
		if(!prenotazioneBindingResult.hasErrors()) {
			if(newPrenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				newPrenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			}else newPrenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);
			prenotazioneService.update(newPrenotazione, id);
			return "prenotazione_update_successful";
		}
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:user/{id}/update";
	}
	
	
	//Get-Vista Utente -> lista di tutte le prenotazioni di un Utente 
	@GetMapping("/user/allPrenotazioni")
	public String showListAllPrenotazioniUser(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni(loggedUser);
		model.addAttribute("prenotazioneList", allPrenotazioni);
		return "listAllPrenotazioniUser";	
	}
	
	//Get-Vista Admin -> lista di tutte le prenotazioni di un Utente selezionato
	@GetMapping("/admin/{id}/allPrenotazioni")
	public String showListAdminAllPrenotazioniUser(@Valid @PathVariable Long id,Model model) {
		User user = userService.getUser(id);
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni(user);
		model.addAttribute("prenotazioneList", allPrenotazioni);
		return "listAdminAllPrenotazioniUser";	
	}
	
	//Get-Vista Admin -> lista di tutte le prenotazioni effettuate
	@GetMapping("/admin/allPrenotazioni")
	public String showListAllPrenotazioni(@Valid Model model) {
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni();
		model.addAttribute("prenotazioneList", allPrenotazioni);
		return "allPrenotazioniAdmin";
	}
	
	
	//Delete Admin -> cancellazione di una prenotazione di un Utente da parte dell'Admin
	@PostMapping("user/{id}/delete")
	public String deletePrenotazioneUtenteFromAdmin(@Valid @PathVariable Long id, Model model) {
		this.prenotazioneService.delete(id);
		return "Ritorna alla stessa pagina";
	}
	


}
