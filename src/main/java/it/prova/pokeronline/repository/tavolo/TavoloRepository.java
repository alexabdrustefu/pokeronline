package it.prova.pokeronline.repository.tavolo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository
		extends PagingAndSortingRepository<Tavolo, Long>, JpaSpecificationExecutor<Tavolo>, CustomTavoloRepository {

	@Query("from Tavolo t left join fetch t.giocatori left join fetch t.utenteCreazione where t.id = :id")
	Tavolo findByIdEager(@Param("id") Long idTavolo);

	List<Tavolo> findByEsperienzaMinLessThan(Double esperienza);

	Tavolo findByGiocatoriId(Long id);

	@Query(value = "SELECT t.* " + "FROM tavolo t "
			+ "WHERE ((:denominazione IS NULL OR LOWER(t.denominazione) LIKE %:denominazione%)  "
			+ "AND (:esperienzaminima IS NULL OR esperienzaMin > :esperienzaminima) "
			+ "AND (:ciframinima IS NULL OR t.ciframinima >= :ciframinima) "
			+ "AND (:datacreazione IS NULL OR t.datacreazione >= :datacreazione)) "
			+ "ORDER BY t.id ASC", countQuery = "SELECT COUNT(*) " + "FROM tavolo t "
					+ "WHERE ((:denominazione IS NULL OR LOWER(t.denominazione) LIKE %:denominazione%)  "
					+ "AND (:esperienzaminima IS NULL OR esperienzaMin > :esperienzaminima) "
					+ "AND (:ciframinima IS NULL OR t.ciframinima >= :ciframinima) "
					+ "AND (:datacreazione IS NULL OR t.datacreazione >= :datacreazione))", nativeQuery = true)
	Page<Tavolo> findByExampleNativeWithPagination(@Param("denominazione") String denominazione,
			@Param("esperienzaminima") Double esperienzaMinima, @Param("ciframinima") Double cifraMinima,
			@Param("datacreazione") LocalDate dataCreazione, Pageable pageable);

	@Query(value = "select t.* from tavolo t " + "where t.utente_id = :idInSessione and exists"
			+ "(select * from tavolo_giocatori p inner join utente u " + "on p.giocatori_id = u.id where "
			+ "p.tavolo_id = t.id and u.esperienzaaccumulata >= :soglia)", nativeQuery = true)
	List<Tavolo> estraiTavoliConAlmenoUnUtenteAlDiSopraDiSoglia(@Param("idInSessione") Long idInSessione,
			@Param("soglia") Integer soglia);

	@Query(value = "SELECT t.* " + "	FROM tavolo AS t " + "	JOIN tavolo_giocatori AS tg ON t.id = tg.tavolo_id "
			+ "	JOIN utente AS u ON tg.giocatori_id = u.id " + "	GROUP BY t.id "
			+ "	ORDER BY SUM(u.esperienzaaccumulata) DESC " + "	LIMIT 1", nativeQuery = true)
	Tavolo trovaTavoloConMassimaEsperienzaGiocatori();

	@Modifying
	@Query(value = "delete g.* from tavolo_giocatori g inner join tavolo t on t.id = g.Tavolo_id inner join utente u on u.id = g.giocatori_id\r\n"
			+ "where g.Tavolo_id in (select u.id from utente u where u.username in :listaUsername);", nativeQuery = true)
	public void svuotaTavoliCreatiDaUtenti(List<String> listaUsername);
}