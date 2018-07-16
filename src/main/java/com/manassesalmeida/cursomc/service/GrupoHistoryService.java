package com.manassesalmeida.cursomc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.domain.GrupoHistory;
import com.manassesalmeida.cursomc.repository.GrupoHistoryRepository;

@Service
public class GrupoHistoryService {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoHistoryRepository grupoHistoryRepository;

	public GrupoHistory find(Integer id) {
		try {
			return grupoHistoryRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Histórico informado não existe.");
		}
	}

	public List<GrupoHistory> findAllByGrupo(Integer id) {
		Grupo grupo = grupoService.find(id);
		List<GrupoHistory> grupoHistoryList = grupoHistoryRepository.findByGrupo(grupo);

		if (grupoHistoryList == null || grupoHistoryList.isEmpty()) {
			throw new IllegalArgumentException("Não há histórico para o grupo informado.");
		}

		return grupoHistoryList;
	}

}
