package it.prova.pokeronline.dto;

import java.util.List;
import java.util.stream.Collectors;

public class SvuotaTavoloDTO {
	String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SvuotaTavoloDTO(String username) {
		super();
		this.username = username;
	}

	public SvuotaTavoloDTO() {
		super();
	}

	public static List<String> createListStringToDTO(List<SvuotaTavoloDTO> instance) {
		return instance.stream().map(entity -> entity.getUsername()).collect(Collectors.toList());
	}


}
