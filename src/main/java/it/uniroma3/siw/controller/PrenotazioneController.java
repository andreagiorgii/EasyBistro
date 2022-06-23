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

	// Get-Insert Utente -> Vista Utente per la form di prenotazione
	@GetMapping("user/prenotazione")
	public String showFormPrenotazione(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("prenotazioneForm", new Prenotazione());
		return "prenotazione_user";
	}

	// Post-Insert -> Validazione form prenotazione Utente
	@PostMapping("user/prenotazione")
	public String prenotazioneUser(@Valid @ModelAttribute("prenotazioneForm") Prenotazione prenotazione,
			BindingResult prenotazioneBindingResult, Model model) {

		User loggedUser = sessionData.getLoggedUser();
		prenotazione.setUsers(loggedUser);

		this.prenotazioneValidator.validate(prenotazione, prenotazioneBindingResult);

		if (!prenotazioneBindingResult.hasErrors()) {
			if (prenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				prenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			} else {
				prenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);

			}
			prenotazioneService.save(prenotazione);
			return "prenotazione_successful";
		}

		model.addAttribute("loggedUser", loggedUser);
		return "prenotazione_user";
	}

	// Delete Utente -> cancellazione di una prenotazione da parte di un Utente

	@PostMapping("user/{id}/delete")
	public String deletePrenotazioneUtente(@Valid @PathVariable Long id, Model model) {
		this.prenotazioneService.delete(id);
		return "redirect:/user/allPrenotazioni";
	}

	// GET-Update Utente -> aggiornamento di una prenotazione da parte di un Utente
	@GetMapping("user/{id}/update")
	public String updatePrenotazioneUserForm(@Valid @PathVariable Long id, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Prenotazione prenotazione = this.prenotazioneService.findById(id);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("loggedPrenotazione", prenotazione);
		return "prenotazione_user_update";
	}

	// Post-Update Utente -> aggiornamento di una prenotazione da parte di un Utente
	@PostMapping("user/{id}/update")
	public String updatePrenotazione(@Valid @ModelAttribute("loggedPrenotazione") Prenotazione updatePrenotazione,
			@PathVariable Long id, BindingResult prenotazioneBindingResult, Model model) {

		User loggedUser = sessionData.getLoggedUser();
		updatePrenotazione.setUsers(loggedUser);

		this.prenotazioneValidator.validate(updatePrenotazione, prenotazioneBindingResult);
		if (!prenotazioneBindingResult.hasErrors()) {
			if (updatePrenotazione.getLuogo().equals(Prenotazione.INTERNO_POSTO)) {
				updatePrenotazione.setLuogo(Prenotazione.INTERNO_POSTO);
			} else {
				updatePrenotazione.setLuogo(Prenotazione.ESTERNO_POSTO);
			}
			prenotazioneService.update(updatePrenotazione, id);
			return "prenotazione_update_successful";
		}
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/user/{id}/update";
	}

	// Get-Vista Utente -> lista di tutte le prenotazioni di un Utente OK
	@GetMapping("/user/allPrenotazioni")
	public String showListAllPrenotazioniUser(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni(loggedUser);
		model.addAttribute("prenotazioneList", allPrenotazioni);
		model.addAttribute("loggedUser", loggedUser);
		return "lista_prenotazioni_user";
	}

	// Get-Vista Admin -> lista di tutte le prenotazioni di un Utente selezionato OK
	@GetMapping("/admin/{id}/allPrenotazioni")
	public String showListAdminAllPrenotazioniUser(@Valid @PathVariable Long id, Model model) {
		User user = userService.getUser(id);
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni(user);
		model.addAttribute("prenotazioneList", allPrenotazioni);
		return "lista_prenotazioni_admin";
	}

	// Get-Vista Admin -> lista di tutte le prenotazioni effettuate OK
	@GetMapping("/admin/allPrenotazioni")
	public String showListAllPrenotazioni(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Prenotazione> allPrenotazioni = this.prenotazioneService.getAllPrenotazioni();
		model.addAttribute("prenotazioneList", allPrenotazioni);
		model.addAttribute("loggedUser", loggedUser);
		return "lista_prenotazioni_admin";
	}

	// Delete Admin -> cancellazione di una prenotazione di un Utente da parte
	// dell'Admin
	@PostMapping("admin/{id}/delete")
	public String deletePrenotazioneUtenteFromAdmin(@Valid @PathVariable Long id, Model model) {
		this.prenotazioneService.delete(id);
		return "redirect:/admin/allPrenotazioni";
	}

}
