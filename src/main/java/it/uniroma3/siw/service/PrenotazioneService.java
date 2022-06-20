package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.repository.PrenotazioneRepository;

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
	
}
