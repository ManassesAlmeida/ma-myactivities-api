package com.manassesalmeida.cursomc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.domain.GrupoHistory;

public interface GrupoHistoryRepository extends JpaRepository<GrupoHistory, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT MAX (obj.versao) FROM GrupoHistory obj INNER JOIN obj.grupo g WHERE g = :grupo")
	Integer findMaxVersao(@Param("grupo") Grupo grupo);
	
	@Transactional(readOnly = true)
	List<GrupoHistory> findByGrupo(Grupo grupo);
	
}
