package it.uniroma3.siw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Tavolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@NotNull
	@Min(0)
	@Max(10)
	private Integer numeroTavolo;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getNumeroTavolo() {
		return numeroTavolo;
	}


	public void setNumeroTavolo(Integer numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}
	

}
