package it.uniroma3.siw.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Prenotazione {

	public static final String ESTERNO_POSTO = "ESTERNO";
	public static final String INTERNO_POSTO = "INTERNO";

	public static Integer CAPIENZA_MAX_ESTERNA = 30;
	public static Integer CAPIENZA_MAX_INTERNA = 30;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Min(0)
	@Max(30)
	private Integer numeroPersone;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private LocalDateTime dataPrenotazione;

	@NotBlank
	private String luogo;

	@ManyToOne
	private User users;


	@NotNull
	//@Min(0)
	//@Max(15)
	private Integer cellulare;

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

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Integer getCellulare() {
		return cellulare;
	}

	public void setCellulare(Integer cellulare) {
		this.cellulare = cellulare;
	}

	public boolean isPrenotabileInterno(Integer numeroPrenotati) {
		if (CAPIENZA_MAX_INTERNA - numeroPrenotati < 0)
			return false;

		else
			CAPIENZA_MAX_INTERNA = CAPIENZA_MAX_INTERNA - numeroPrenotati;

		return true;
	}

	public boolean isPrenotabileEsterno(Integer numeroPrenotati) {
		if (CAPIENZA_MAX_ESTERNA - numeroPrenotati < 0)
			return false;

		else
			CAPIENZA_MAX_ESTERNA = CAPIENZA_MAX_ESTERNA - numeroPrenotati;

		return true;
	}
}
