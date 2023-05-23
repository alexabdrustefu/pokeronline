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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tavolo")
public class Tavolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;
	@Column(name = "cifraMin")
	private Double cifraMinima;
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

	public Tavolo(Long id, Integer esperienzaMin, Double cifraMinima, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, Integer esperienzaMin, Double cifraMinima, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo(Integer esperienzaMin, Double cifraMinima, String denominazione, LocalDate dataCreazione,
			Utente utenteCreazione, Set<Utente> giocatori) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utenteCreazione = utenteCreazione;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, @NotNull(message = "{esperienzaMin.notblank}") Integer esperienzaMin,
			@NotNull(message = "{cifraMinima.notblank}") Double cifraMinima,
			@NotBlank(message = "{denominazione.notblank}") String denominazione, LocalDate dataCreazione) {
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
	}

	// get e set
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienaMin) {
		this.esperienzaMin = esperienaMin;
	}

	public Double getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Double cifraMinima) {
		this.cifraMinima = cifraMinima;
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
