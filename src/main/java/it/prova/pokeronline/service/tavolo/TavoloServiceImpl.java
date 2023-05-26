package it.prova.pokeronline.service.tavolo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.SvuotaTavoloDTO;
import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.web_api.exception.CreditoInsufficenteException;
import it.prova.pokeronline.web_api.exception.CreditoMinimoInsufficienteException;
import it.prova.pokeronline.web_api.exception.EsperienzaMinimaInsufficienteException;
import it.prova.pokeronline.web_api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web_api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web_api.exception.UtenteGiocatoreGiaSedutoException;

@Service
@Transactional(readOnly = true)
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository repository;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public List<Tavolo> listAllTavoli() {
		return (List<Tavolo>) repository.findAll();
	}

	@Override
	public List<Tavolo> listAllElementsEager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tavolo> findByUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tavolo caricaSingoloTavolo(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tavolo caricaSingoloTavoloConUtenza(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		tavoloInstance.setId(utenteLoggato.getId());
		tavoloInstance.setUtenteCreazione(utenteService.findByUsername(username));
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		tavoloInstance.setUtenteCreazione(utenteService.findByUsername(username));
		tavoloInstance.setDataCreazione(LocalDate.now());
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.deleteById(idToRemove);

	}

	@Override
	public List<Tavolo> findByEsperienzaMinimaLessThan() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);
		Double esperienzaAccumulata = utenteLoggato.getEsperienzaAccumulata();
		return repository.findByEsperienzaMinLessThan(esperienzaAccumulata);
	}
	//metodo con bug
	//@Override
	/*@Transactional
	public void gioca(Long id, String username) {
		Tavolo tavoloReload = repository.findById(id).orElse(null);

		if (tavoloReload == null) {
			throw new TavoloNotFoundException("Il tavolo in cui si cerca di entrare non esiste");
		}

		Utente utenteLoggato = utenteService.findByUsername(username);

		if (tavoloReload.getCifraMinima() > utenteLoggato.getCreditoResiduo()) {
			throw new CreditoInsufficenteException("Credito insufficente Per giocare");
		}

		List<Tavolo> listaTavoli = (List<Tavolo>) repository.findAll();

		for (Tavolo elementoTavolo : listaTavoli) {
			if (elementoTavolo.getId() != id) {
				for (Utente elementoUtente : elementoTavolo.getGiocatori())
					if (elementoUtente.getId().equals(utenteLoggato.getId())) {
						throw new UtenteInAltroTavoloException("Stai giocando ad un altro tavolo non puoi unirti");
					}
			}
		}

		tavoloReload.getGiocatori().add(utenteLoggato);

		boolean maggiore = false;
		double segno = Math.random();
		Double credito = 0.0;

		if (segno >= 0.5) {
			maggiore = true;
		} else {
			maggiore = false;
		}

		if (maggiore) {
			credito = (utenteLoggato.getCreditoResiduo() + Math.random() * 1000);
		} else {
			credito = (utenteLoggato.getCreditoResiduo() - Math.random() * 1000);
		}

		if (utenteLoggato.getCreditoResiduo() <= 0) {
			utenteLoggato.setCreditoResiduo(0.0);
			throw new CreditoInsufficenteException("Credito insufficente");
		}
		
		utenteLoggato.setCreditoResiduo(credito);
		utenteRepository.save(utenteLoggato);

		repository.save(tavoloReload);
	}*/

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

	@Override
	public Page<Tavolo> findByExampleNativeWithPagination(Tavolo example, Integer pageNo, Integer pageSize,
	String sortBy) {
		 if (pageNo == null || pageNo < 0) {
	            pageNo = 0;
	        }
	        if (pageSize == null || pageSize < 1) {
	            pageSize = 10;
	        }
	        if (sortBy == null || sortBy.isEmpty()) {
	            sortBy = "id"; 
	            }
		return repository.findByExampleNativeWithPagination(example.getDenominazione(), example.getEsperienzaMin(),
		example.getCifraMinima(), example.getDataCreazione(),
		PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
	}
	@Override
	@Transactional
	public Tavolo sieditiAlTavolo(Long idTavolo) {

		Tavolo tavolo = this.caricaSingoloTavolo(idTavolo);
		if (tavolo == null) {
			throw new TavoloNotFoundException("Il tavolo che stai cercando non esiste");
		}

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);

		if (utenteLoggato.getCreditoResiduo() == null) {
			utenteLoggato.setCreditoResiduo(0D);
		}
		if (utenteLoggato.getCreditoResiduo() < tavolo.getCifraMinima()) {
			throw new CreditoMinimoInsufficienteException("Non hai credito sufficiente per sederti a questo tavolo");
		}
		if (utenteLoggato.getEsperienzaAccumulata() == null) {
			utenteLoggato.setEsperienzaAccumulata(0D);
		}
		if (utenteLoggato.getEsperienzaAccumulata() < tavolo.getEsperienzaMin()) {
			throw new EsperienzaMinimaInsufficienteException(
					"Non hai abbastanza esperienza per sederti a questo tavolo");
		}
		if (tavolo.getGiocatori().contains(utenteLoggato)) {
			throw new UtenteGiocatoreGiaSedutoException("Attenzione! Sei già seduto a questo tavolo");
		}

		List<Tavolo> listaTavoli = this.listAllTavoli();
		for (Tavolo tavoloItem : listaTavoli) {
			if (tavoloItem.getGiocatori().contains(utenteLoggato)) {
				throw new UtenteGiocatoreGiaSedutoException("Attenzione! Sei già seduto ad un altro tavolo");
			}
		}

		tavolo.getGiocatori().add(utenteLoggato);
		return tavolo;

	}
	@Override
	@Transactional
	public void gioca(Long idTavolo, String username) {

		Tavolo tavolo = repository.findById(idTavolo).orElse(null);

		if (tavolo == null)
			throw new IdNotNullForInsertException("");

		username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione = utenteService.findByUsername(username);

		if (!tavolo.getGiocatori().contains(utenteInSessione))
			throw new TavoloNotFoundException("Eh bro");

		if (utenteInSessione.getCreditoResiduo() == null || utenteInSessione.getCreditoResiduo() == 0d) {
			throw new CreditoInsufficenteException("Poveraccio");
		}

		double segno = Math.random();
		if (segno < 0.5)
			segno = segno * -1;
		int somma = (int) (Math.random() * 500);
		int totale = (int) (segno * somma);

		Integer esperienzaGuadagnata = 0;

		if (totale > 0) {
			esperienzaGuadagnata += 5;
			if (totale > 200)
				esperienzaGuadagnata += 6;
			if (totale > 400)
				esperienzaGuadagnata += 7;
			if (totale > 499)
				esperienzaGuadagnata += 8;
		}

		if (totale <= 0) {
			esperienzaGuadagnata += 4;
			if (totale < -200)
				esperienzaGuadagnata += 3;
			if (totale < -400)
				esperienzaGuadagnata += 2;
			if (totale < -499)
				esperienzaGuadagnata += 1;
		}

		utenteInSessione.setEsperienzaAccumulata(esperienzaGuadagnata + utenteInSessione.getEsperienzaAccumulata());

		Double creditoDaInserire = utenteInSessione.getCreditoResiduo() + totale;

		if (creditoDaInserire < 0) {
			creditoDaInserire = 0D;
		}

		utenteInSessione.setCreditoResiduo(creditoDaInserire);


	}
	@Override
	@Transactional(readOnly = true)
	public List<TavoloDTO> listaTavoliConSogliaEsperienzaGiocatore(Integer soglia) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione = utenteService.findByUsername(username);

		return TavoloDTO.createTavoloDTOListFromModelList(
				repository.estraiTavoliConAlmenoUnUtenteAlDiSopraDiSoglia(utenteInSessione.getId(), soglia));
	}

	@Override
	public TavoloDTO trovaTavoloConEsperienzaMassima() {
		return TavoloDTO.buildTavoloDTOFromModel(repository.trovaTavoloConMassimaEsperienzaGiocatori());
	}

	@Override
	public String svotaUtenti(List<SvuotaTavoloDTO> tavoli) {

		repository.svuotaTavoliCreatiDaUtenti(SvuotaTavoloDTO.createListStringToDTO(tavoli));

		return "fatto";
	}

}