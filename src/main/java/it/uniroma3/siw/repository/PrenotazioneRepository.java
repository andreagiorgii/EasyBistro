package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	
	//@Query(value = "SELECT p FROM Prenotazione p WHERE p.dataPrenotazione=:dataPrenotazione")
	public List<Prenotazione> findByDataPrenotazione(LocalDate dataPrenotazione);

	public List<Prenotazione> findByUsers(User users);
	
	long count();
}
