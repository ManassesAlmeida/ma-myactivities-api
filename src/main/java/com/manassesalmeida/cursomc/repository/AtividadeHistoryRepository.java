package com.manassesalmeida.cursomc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.AtividadeHistory;

public interface AtividadeHistoryRepository extends JpaRepository<AtividadeHistory, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT MAX (obj.versao) FROM AtividadeHistory obj INNER JOIN obj.atividade a WHERE a = :atividade")
	Integer findMaxVersao(@Param("atividade") Atividade atividade);

	@Transactional(readOnly = true)
	List<AtividadeHistory> findByAtividade(Atividade atividade);

}
