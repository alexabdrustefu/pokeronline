package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository{
	
	@Query("from Tavolo t left join fetch t.giocatori left join fetch t.utenteCreazione where t.id=?1")
	Tavolo findByIdEager(Long idTavolo);
	
	List<Tavolo> findByEsperienzaMinLessThan(Integer esperienza);
	
	Tavolo findByGiocatoriId(Long id);
	
	
	
	
	

}