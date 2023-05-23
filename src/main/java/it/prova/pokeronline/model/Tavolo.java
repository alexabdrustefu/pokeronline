package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienaMin")
	private Integer esperienaMin;
	@Column(name = "cifraMin")
	private Double cifraMin;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private LocalDate dataCreazione;
	// perche di default eager
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable=false)
	private Utente utenteCreazione;
	@OneToMany(fetch = FetchType.LAZY,mappedBy="tavolo")
	private Set<Utente> giocatori = new HashSet<>();

	// costruttori

	public Tavolo() {
	}

	public Tavolo(Long id, Integer esperienaMin, Double cifraMin, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.esperienaMin = esperienaMin;
		this.cifraMin = cifraMin;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, Integer esperienaMin, Double cifraMin, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienaMin = esperienaMin;
		this.cifraMin = cifraMin;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo(Integer esperienaMin, Double cifraMin, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione, Set<Utente> giocatori) {
		super();
		this.esperienaMin = esperienaMin;
		this.cifraMin = cifraMin;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
		this.giocatori = giocatori;
	}

	// get e set
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienaMin() {
		return esperienaMin;
	}

	public void setEsperienaMin(Integer esperienaMin) {
		this.esperienaMin = esperienaMin;
	}

	public Double getCifraMin() {
		return cifraMin;
	}

	public void setCifraMin(Double cifraMin) {
		this.cifraMin = cifraMin;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	public Set<Utente> getUtenti() {
		return giocatori;
	}

	public void setUtenti(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

}
