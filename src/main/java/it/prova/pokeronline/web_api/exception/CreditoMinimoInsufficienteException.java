package it.prova.pokeronline.web_api.exception;

public class CreditoMinimoInsufficienteException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CreditoMinimoInsufficienteException(String message) {
		super(message);
	}
}
