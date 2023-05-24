package it.prova.pokeronline.web_api.exception;

public class CreditoInsufficenteException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	public CreditoInsufficenteException(String message) {
		super(message);
	}
}
