package it.prova.pokeronline.service.tavolo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.web.exception.CreditoInsufficenteException;
import it.prova.pokeronline.web.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.exception.UtenteInAltroTavoloException;

@Service
public class TavoloServiceImpl implements TavoloService {
	@Autowired
	TavoloRepository repository;
	@Autowired
	UtenteRepository utenteRepository;
	@Autowired
	UtenteService utenteService;

	@Override
	public List<Tavolo> listAll() {
		return (List<Tavolo>) repository.findAll();// cast in una lista di tavoli
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
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		tavoloInstance.setId(utenteLoggato.getId());
		tavoloInstance.setUtenteCreazione(utenteService.findByUsername(username));
		return repository.save(tavoloInstance);
	}

	@Override
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		repository.deleteById(idToRemove);

	}

	@Override
	public List<Tavolo> findByEsperienzaMinimaLessThan() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);
		Integer esperienzaAccumulata = utenteLoggato.getEsperienzaAccumulata();

		return repository.findByEsperienzaMinLessThan(esperienzaAccumulata);
	}

	@Override
	@Transactional
	public void gioca(Long id, String username) {
		Tavolo tavoloReload = repository.findById(id).orElse(null);

		if (tavoloReload == null) {
			throw new TavoloNotFoundException("tavolo non esistente");
		}

		Utente utenteLoggato = utenteService.findByUsername(username);

		if (tavoloReload.getCifraMinima() > utenteLoggato.getCreditoAccumulato()) {
			throw new CreditoInsufficenteException("Credito insufficente");
		}

		List<Tavolo> listaTavoli = (List<Tavolo>) repository.findAll();

		for (Tavolo elementoTavolo : listaTavoli) {
			if (elementoTavolo.getId() != id) {
				for (Utente elementoUtente : elementoTavolo.getUtenti())
					if (elementoUtente.getId().equals(utenteLoggato.getId())) {
						throw new UtenteInAltroTavoloException("sei presente in un altro tavolo non puoi entrare");
					}
			}
		}

		tavoloReload.getUtenti().add(utenteLoggato);

		boolean maggiore = false;
		double segno = Math.random();
		Integer credito = 0;

		if (segno >= 0.5) {
			maggiore = true;
		} else {
			maggiore = false;
		}

		if (maggiore) {
			credito = (int) (utenteLoggato.getCreditoAccumulato() + Math.random() * 1000);
		} else {
			credito = (int) (utenteLoggato.getCreditoAccumulato() - Math.random() * 1000);
		}

		if (utenteLoggato.getCreditoAccumulato() <= 0) {
			utenteLoggato.setCreditoAccumulato(0);
			throw new CreditoInsufficenteException("Credito insufficente");
		}

		utenteLoggato.setCreditoAccumulato(credito);
		utenteRepository.save(utenteLoggato);

		repository.save(tavoloReload);
	}

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

}
