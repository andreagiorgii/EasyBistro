package it.uniroma3.siw.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	
	public Prenotazione findByDataPrenotazione(LocalDateTime dataPrenotazione);

	public List<Prenotazione> findByUsers(User users);


}