package com.manassesalmeida.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.manassesalmeida.cursomc.domain.enums.Status;

@Entity
public class AtividadeHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Obrigatório informar a atividade.")
	@ManyToOne
	private Atividade atividade;

	private String descricao;

	private String conteudo;

	@OneToOne
	private Grupo grupo;

	@Enumerated(value = EnumType.STRING)
	private Status status;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@NotNull(message = "Versão é obrigatória.")
	private Integer versao;

	public AtividadeHistory() {
	}

	public AtividadeHistory(Integer id, Atividade atividade, String descricao, String conteudo, Grupo grupo,
			Status status, Integer versao) {
		super();
		this.id = id;
		this.atividade = atividade;
		this.descricao = descricao;
		this.conteudo = conteudo;
		this.grupo = grupo;
		this.status = status;
		this.versao = versao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtividadeHistory other = (AtividadeHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
