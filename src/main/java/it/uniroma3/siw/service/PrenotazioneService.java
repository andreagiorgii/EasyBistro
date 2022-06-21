package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	
	@Autowired
	PrenotazioneRepository prenotazioneRepository;

	
	@Transactional
	public void save(Prenotazione prenotazione) {
		prenotazioneRepository.save(prenotazione);		
	}

	@Transactional
	public void delete(Long prenotazioneId) {
		prenotazioneRepository.deleteById(prenotazioneId);
	}
	
	public Prenotazione alreadyExists(Prenotazione prenotazione) {
		return prenotazioneRepository.findByDataPrenotazione(prenotazione.getDataPrenotazione());
	}
	
	public Prenotazione findById(Long id) {
		return prenotazioneRepository.findById(id).get();
	}
	
	
	//restituisce tutte le prenotazioni
	public List<Prenotazione> getAllPrenotazioni(){
		List<Prenotazione> prenotazione = new ArrayList<>();
		for(Prenotazione p : prenotazioneRepository.findAll()) {
			prenotazione.add(p);
		}
		return prenotazione;
	}
	
	
	//restituisce tutte le prenotazioni tramite l'Id dell'Utente
	public List<Prenotazione> getAllPrenotazioni(User loggedUser){
		Iterable<Prenotazione> iterable = this.prenotazioneRepository.findByUsers(loggedUser);
		List<Prenotazione> prenotazione = new ArrayList<>();
		for(Prenotazione p : iterable) {
			prenotazione.add(p);
		}
		return prenotazione;
	}
}
