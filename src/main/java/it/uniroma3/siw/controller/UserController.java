package it.uniroma3.siw.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrenotazioneService;

@Controller
public class UserController {

	@Autowired
	CredentialsService credentialsService;

	@Autowired
	PrenotazioneService prenotazioneService;

	@Autowired
	CredentialsValidator credentialsValidator;

	@Autowired
	SessionData sessionData;

// 1)  METODI VALIDI SIA PER ADMIN CHE USER

	// vista home in base se si è admin o user
	@GetMapping("/home")
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials loggedCredentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser", loggedUser);
		// model.addAttribute("loggedCredentials", loggedCredentials);
		if (loggedCredentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
			String dataFormattata = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date());
			Long totalePrenotazioni = prenotazioneService.countAllPrenotazioni();
			model.addAttribute("prenotazioniTotali",totalePrenotazioni);
			model.addAttribute("data", dataFormattata);
			return "dashboard_admin";
		}
		return "dashboard_user";
	}

	// Vista profilo loggato admin o user
	@GetMapping("/user/me")
	public String profile(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials loggedCredentials = sessionData.getLoggedCredentials();
		// System.out.println(loggedCredentials.getPassword());
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("loggedCredentials", loggedCredentials);
		return "profilo";
	}

	// Vista modifica profilo loggato admin o user
	@GetMapping("/user/update")
	public String updateForm(Model model) {
		model.addAttribute("newCredentials", new Credentials());
		return "modifica_profilo";
	}

	// updateUser effettua il controllo dei dati inseriti nei campi di registrazione
	// che si vogliono aggiornare
	// NB:lo username non puó essere uguale al precedente
	@PostMapping("/user/update")
	public String updateUser(@Valid @ModelAttribute("newCredentials") Credentials newCredentials,
			BindingResult credentialsBindingResult, Model model) {

		User loggedUser = sessionData.getLoggedUser();
		Credentials cred = credentialsService.getCredentials(sessionData.getLoggedCredentials().getId());

		this.credentialsValidator.validate(newCredentials, credentialsBindingResult);
		if (!credentialsBindingResult.hasErrors()) {

			cred.setUsername(newCredentials.getUsername());
			cred.setPassword(newCredentials.getPassword());
			credentialsService.update(cred);

			sessionData.setCredentials(cred);

			model.addAttribute("user", loggedUser);
			return "user_update_successful";
		}
		return "redirect:/user/update";
	}

// 2)  //OPERAZIONI SULLA PAGINA DELL'ADMIN

	// vista admin con tutti gli utenti loggati
	@GetMapping("/admin/allUsers")
	public String userList(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		return "lista_utenti_admin";
	}

	// admin delete utenti loggati
	@PostMapping("/admin/allUsers/{username}/delete")
	public String removeUser(@Valid Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		return "redirect:/admin/allUsers";
	}

}
