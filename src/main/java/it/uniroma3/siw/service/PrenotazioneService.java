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

	@Transactional
	public void update(Prenotazione prenotazione, Long id) {
		prenotazione.setId(id);
		prenotazioneRepository.save(prenotazione);		
	}

	
	
	public boolean alreadyExists(Prenotazione prenotazione, User loggedUser) {

		List<Prenotazione> lista = this.prenotazioneRepository.findByDataPrenotazione(prenotazione.getDataPrenotazione());
		if(lista != null) {
			for(Prenotazione p : lista) {
				if(p.getUsers().getId()== loggedUser.getId()) {
					return true;
				}
			}
		}
		return false;
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
	public List<Prenotazione> getAllPrenotazioni(User user){
		Iterable<Prenotazione> iterable = this.prenotazioneRepository.findByUsers(user);
		List<Prenotazione> prenotazione = new ArrayList<>();
		for(Prenotazione p : iterable) {
			prenotazione.add(p);
		}
		return prenotazione;
	}
}
