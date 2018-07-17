package com.manassesalmeida.cursomc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class GrupoHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@NotNull(message = "Obrigatório informar o grupo.")
	@ManyToOne
	private Grupo grupo;

	@Column(columnDefinition = "LONGVARCHAR")
	private String nome;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date horaUltimaAlteracao;

	private Boolean editavel;

	@Enumerated(value = EnumType.STRING)
	private Status status;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@NotNull(message = "Versão é obrigatória.")
	private Integer versao;

	public GrupoHistory() {
	}

	public GrupoHistory(Integer id, Grupo grupo, String nome, List<Atividade> atividades, Date horaUltimaAlteracao,
			Boolean editavel, Status status, Integer versao) {
		super();
		this.id = id;
		this.grupo = grupo;
		this.nome = nome;
		this.horaUltimaAlteracao = horaUltimaAlteracao;
		this.editavel = editavel;
		this.status = status;
		this.versao = versao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getHoraUltimaAlteracao() {
		return horaUltimaAlteracao;
	}

	public void setHoraUltimaAlteracao(Date horaUltimaAlteracao) {
		this.horaUltimaAlteracao = horaUltimaAlteracao;
	}

	public Boolean getEditavel() {
		return editavel;
	}

	public void setEditavel(Boolean editavel) {
		this.editavel = editavel;
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
		GrupoHistory other = (GrupoHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
