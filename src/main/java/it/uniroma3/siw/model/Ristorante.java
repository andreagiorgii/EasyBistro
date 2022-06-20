package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Ristorante {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "prenotazione_id")
	private List<Prenotazione> prenotazioni;
	
	@OneToMany(mappedBy = "ristorante")
	private List<User> titolari;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Menu menu;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Tavolo tavolo;
	
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public List<User> getTitolari() {
		return titolari;
	}

	public void setTitolari(List<User> titolari) {
		this.titolari = titolari;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}


	
	
	
	
}
