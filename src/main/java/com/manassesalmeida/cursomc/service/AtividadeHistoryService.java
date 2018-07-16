package com.manassesalmeida.cursomc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.AtividadeHistory;
import com.manassesalmeida.cursomc.repository.AtividadeHistoryRepository;

@Service
public class AtividadeHistoryService {

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private AtividadeHistoryRepository atividadeHistoryRepository;

	public AtividadeHistory find(Integer id) {
		try {
			return atividadeHistoryRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Histórico informado não existe.");
		}
	}

	public List<AtividadeHistory> findAllByAtividade(Integer id) {
		Atividade atividade = atividadeService.find(id);
		List<AtividadeHistory> atividadeHistoryList = atividadeHistoryRepository.findByAtividade(atividade);

		if (atividadeHistoryList == null || atividadeHistoryList.isEmpty()) {
			throw new IllegalArgumentException("Não há histórico para a atividade informada.");
		}

		return atividadeHistoryList;
	}

}
