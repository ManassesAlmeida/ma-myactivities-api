package com.manassesalmeida.cursomc.domain.enums;

public enum Status {

	VIGENTE(1, "VIGENTE"), 
	DELETADO(2, "DELETADO");

	private int cod;
	private String descricao;

	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
		return descricao;
	}

	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Status x : Status.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Status inválido: " + cod);

	}
}
