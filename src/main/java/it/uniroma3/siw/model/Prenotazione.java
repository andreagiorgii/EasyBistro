package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;

@Entity
public class Prenotazione {
	
	public static final String ESTERNO_POSTO ="ESTERNO";
	public static final String INTERNO_POSTO ="INTERNO";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Min(0)
	@Max(50)
	private Integer numeroPersone;
	
	@NotNull(message = "data di prenotazione richiesta")
	private LocalDateTime dataPrenotazione;
	
	
	 @OneToOne
	 private User user;
	
	@NotBlank
	private String luogo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(Integer numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public LocalDateTime getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	 

}
