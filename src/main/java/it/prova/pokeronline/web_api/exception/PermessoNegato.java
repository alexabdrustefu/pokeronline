package it.prova.pokeronline.web_api.exception;

public class PermessoNegato extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PermessoNegato(String message) {
		super(message);
	}
}
