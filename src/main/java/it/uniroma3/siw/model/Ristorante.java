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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Ristorante {
	
	public static final Integer CAPIENZA = 30;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String capienza;
	
	@NotBlank
	private String sede;

	@NotNull
	@Min(0)
	@Max(15)
	private Integer telefono;
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCapienza() {
		return capienza;
	}

	public void setCapienza(String capienza) {
		this.capienza = capienza;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
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
