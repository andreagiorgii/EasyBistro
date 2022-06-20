package it.uniroma3.siw.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	
	public Prenotazione findByDataPrenotazione(LocalDateTime dataPrenotazione);


}
