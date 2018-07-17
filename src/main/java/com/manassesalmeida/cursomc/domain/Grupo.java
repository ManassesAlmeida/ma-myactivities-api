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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.manassesalmeida.cursomc.domain.enums.Status;

@Entity
public class Grupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Nome do grupo é obrigatório")
	@Column(columnDefinition = "LONGVARCHAR")
	private String nome;

	@OneToMany(mappedBy = "grupo")
	private List<Atividade> atividades;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date horaUltimaAlteracao;

	@NotNull(message = "Campo editável é obrigatório")
	private Boolean editavel;

	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "Status é obrigatório")
	private Status status;

	public Grupo() {
	}

	public Grupo(Integer id, String nome, List<Atividade> atividades, Date horaUltimaAlteracao, Boolean editavel,
			Status status) {
		super();
		this.id = id;
		this.nome = nome;
		this.atividades = atividades;
		this.horaUltimaAlteracao = horaUltimaAlteracao;
		this.editavel = editavel;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
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
		Grupo other = (Grupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
