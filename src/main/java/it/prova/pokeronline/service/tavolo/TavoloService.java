package it.prova.pokeronline.service.tavolo;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	
	List<Tavolo> listAllTavoli();
	
	List<Tavolo> listAllElementsEager();
	
	List<Tavolo>findByUsername();

	Tavolo caricaSingoloTavolo(Long id);
	
	Tavolo caricaSingoloTavoloConUtenza(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Long idToRemove);
	
	List<Tavolo> findByEsperienzaMinimaLessThan();
	
	public Tavolo sieditiAlTavolo(Long id);
	
	
	public List<Tavolo> findByExample(Tavolo example);

	Page<Tavolo> findByExampleNativeWithPagination(Tavolo example, Integer pageNo, Integer pageSize,
			String sortBy);
	
	void gioca(Long id, String username);
	
}
