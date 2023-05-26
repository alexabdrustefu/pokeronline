package it.prova.pokeronline.web_api;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.SvuotaTavoloDTO;
import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.web_api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web_api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web_api.exception.ValoreEsperienzaException;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {
	@Autowired
	private TavoloService tavoloService;

	@GetMapping
	public List<TavoloDTO> getAll() {
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoli());
	}

	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {

		Tavolo tavolo = tavoloService.caricaSingoloTavolo(id);

		if (tavolo == null) {
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);
		}

		return TavoloDTO.buildTavoloDTOFromModel(tavolo);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
		if (tavoloInput.getId() != null) {
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		}

		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildFromModel());

		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito);
	}

	@PutMapping("/{id}")
	public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloTavolo(id);

		if (tavolo == null) {
			throw new TavoloNotFoundException("Tavolo not found con id " + id);
		}

		tavoloInput.setId(id);

		Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildFromModel());

		return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		tavoloService.rimuovi(id);
	}

	@PostMapping("/search")
	public List<TavoloDTO> search(@RequestBody TavoloDTO example) {
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(example.buildFromModel()));
	}

	@PostMapping("/searchNativeWithPagination")
	public ResponseEntity<Page<TavoloDTO>> searchNativePaginated(@RequestBody TavoloDTO example,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "0") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {

		Page<Tavolo> entityPageResults = tavoloService.findByExampleNativeWithPagination(example.buildFromModel(),
				pageNo, pageSize, sortBy);
		

		return new ResponseEntity<Page<TavoloDTO>>(TavoloDTO.fromModelPageToDTOPage(entityPageResults), HttpStatus.OK);
	}
	// MEV 1
		@GetMapping("/specialGuest/listaTavoliConGiocatoreConSogliaEsperienza")
		public List<TavoloDTO> listaTavoliConGiocatoreConSogliaEsperienza(
				@Valid @RequestBody Map<String, Integer> rawValue) {

			if (rawValue.get("sogliaMinima") == null || rawValue.get("sogliaMinima") <= 0)
				throw new ValoreEsperienzaException("Inserire una cifra maggiore di 0");

			return tavoloService.listaTavoliConSogliaEsperienzaGiocatore(rawValue.get("sogliaMinima"));

		}

		// MEV 2
		@GetMapping("/admin/trovaTavoloConMassimaEsperienzaGiocatori")
		public TavoloDTO trovaTavoloConMassimaEsperienzaGiocatori() {
			return tavoloService.trovaTavoloConEsperienzaMassima();
		}

		// MEV 4
		@PostMapping("/admin/svuotaTavoli")
		public String svuotaTavoli(@RequestBody List<SvuotaTavoloDTO> tavoli) {

			tavoloService.svotaUtenti(tavoli);

			return "fatto";

		}

}