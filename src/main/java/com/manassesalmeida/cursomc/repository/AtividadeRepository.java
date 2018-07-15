package com.manassesalmeida.cursomc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.Grupo;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {

	public List<Atividade> findAtividadeByGrupo(Grupo grupo);
	
}
