package it.prova.pokeronline.web_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.service.utente.UtenteService;

@RestController
@RequestMapping("/api/gioco")
public class GiocoController {
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private TavoloService tavoloService;
	//ricaricaCredito
	@GetMapping("/compraCredito/{denaro}")
	public UtenteDTO addCredito(@PathVariable(value = "denaro", required = true) Double denaro) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);
		//se sono qui sono loggato
		Double creditoDaAggiungere = utenteLoggato.getCreditoResiduo() + denaro;
		
		utenteLoggato.setCreditoResiduo(creditoDaAggiungere);
		utenteService.aggiorna(utenteLoggato);
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteLoggato);
	}
	@PostMapping("/ricerca")
	public List<TavoloDTO> tavoliConEsperienza(){
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByEsperienzaMinimaLessThan());
	}
}
