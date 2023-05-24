package it.prova.pokeronline.web_api.exception;

public class UtenteInNessunTavoloException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	public UtenteInNessunTavoloException(String message) {
		super(message);
	}

}
