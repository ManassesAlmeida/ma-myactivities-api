package com.manassesalmeida.cursomc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manassesalmeida.cursomc.domain.enums.Status;

@Entity
public class Atividade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Descrição é obrigatória.")
	@Column(columnDefinition = "LONGVARCHAR")
	private String descricao;

	@Column(columnDefinition = "LONGVARCHAR")
	private String conteudo;

	@JsonIgnore
	@ManyToOne
	@NotNull(message = "Grupo da atividade deve ser informado.")
	private Grupo grupo;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date horaUltimaAlteracao;

	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "Status é obrigatório")
	private Status status;

	public Atividade() {
	}

	public Atividade(Integer id, String descricao, String conteudo, Grupo grupo, Date horaUltimaAlteracao,
			Status status) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.conteudo = conteudo;
		this.grupo = grupo;
		this.horaUltimaAlteracao = horaUltimaAlteracao;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getHoraUltimaAlteracao() {
		return horaUltimaAlteracao;
	}

	public void setHoraUltimaAlteracao(Date horaUltimaAlteracao) {
		this.horaUltimaAlteracao = horaUltimaAlteracao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		Atividade other = (Atividade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
