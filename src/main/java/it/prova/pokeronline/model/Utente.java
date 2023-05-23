package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "dateCreated")
	private LocalDate dateCreated;
	@Column(name = "esperienzaAccumulata")
	private Integer esperienzaAccumulata;
	@Column(name = "creditoAccumulato")
	private Integer creditoAccumulato;	

	@Enumerated(EnumType.STRING)
	private StatoUtente stato;
	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
	@ManyToOne
	@JoinColumn(name="tavolo_id")
	private Tavolo tavolo;
	//costruttori

	public Utente() {

	}
	
	
	public Utente(Long id, String nome, String cognome, String username, String password, LocalDate dateCreated,
			Integer esperienzaAccumulata, Integer creditoAccumulato, StatoUtente stato, Set<Ruolo> ruoli,
			Tavolo tavolo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
		this.stato = stato;
		this.ruoli = ruoli;
		this.tavolo = tavolo;
	}


	public Utente(Long id, String nome, String cognome, String username, String password, LocalDate dateCreated,
			StatoUtente stato, Set<Ruolo> ruoli) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
	}

	//metodi get e set

	public Utente(Long id, @NotBlank(message = "{nome.notblank}") String nome,
			@NotBlank(message = "{cognome.notblank}") String cognome,
			@NotBlank(message = "{username.notblank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{password.notblank}") @Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri") String password,
			@NotNull LocalDate dateCreated, StatoUtente stato) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}
	
	
	
	public Integer getEsperienzaAccumulata() {
		return esperienzaAccumulata;
	}


	public void setEsperienzaAccumulata(Integer esperienzaAccumulata) {
		this.esperienzaAccumulata = esperienzaAccumulata;
	}


	public Integer getCreditoAccumulato() {
		return creditoAccumulato;
	}


	public void setCreditoAccumulato(Integer creditoAccumulato) {
		this.creditoAccumulato = creditoAccumulato;
	}


	public Tavolo getTavolo() {
		return tavolo;
	}


	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}


	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
	public boolean isAdmin() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}
}
