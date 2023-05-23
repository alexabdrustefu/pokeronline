package it.prova.pokeronline.service.tavolo;

import java.util.List;
import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	public List<Tavolo> listAll();

	List<Tavolo> listAllElementsEager();

	List<Tavolo> findByUsername();

	Tavolo caricaSingoloTavolo(Long id);

	Tavolo caricaSingoloTavoloConUtenza(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Long idToRemove);

	List<Tavolo> findByEsperienzaMinimaLessThan();

	void gioca(Long id, String username);

	public List<Tavolo> findByExample(Tavolo example);

}
