package com.manassesalmeida.cursomc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.AtividadeHistory;
import com.manassesalmeida.cursomc.domain.enums.Status;
import com.manassesalmeida.cursomc.dto.AtividadeDTO;
import com.manassesalmeida.cursomc.repository.AtividadeHistoryRepository;
import com.manassesalmeida.cursomc.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private AtividadeHistoryRepository atividadeHistoryRepository;

	public Atividade find(Integer id) {
		try {
			return atividadeRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Atividade informada não existe.");
		}
	}

	public List<Atividade> findAll() {
		return atividadeRepository.findAll();
	}

	public Atividade insert(AtividadeDTO obj) {
		Atividade newObj = new Atividade();
		newObj.setId(null);
		updateData(newObj, obj);

		newObj = atividadeRepository.save(newObj);
		insertHistory(newObj);

		return newObj;
	}

	public Atividade update(AtividadeDTO obj, Integer id) {
		Atividade newObj = find(id);

		if (newObj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível atualizar uma atividade deletada.");
		}

		updateData(newObj, obj);

		newObj = atividadeRepository.save(newObj);
		insertHistory(newObj);

		return newObj;
	}

	public void delete(Integer id) {
		Atividade obj = find(id);

		if (obj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível deletar esta atividade, pois já foi deletada.");
		}

		obj.setStatus(Status.toEnum(2));

		obj = atividadeRepository.save(obj);
		insertHistory(obj);
	}

	private void updateData(Atividade newObj, AtividadeDTO obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setConteudo(obj.getConteudo());
		newObj.setGrupo(grupoService.find(obj.getGrupo()));
		newObj.setStatus(Status.toEnum(1));
	}

	public void duplicate(Integer id) {
		Atividade obj = find(id);

		if (obj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível duplicar uma atividade deletada.");
		}

		Atividade newObj = new Atividade(null, obj.getDescricao(), obj.getConteudo(), obj.getGrupo(), obj.getStatus());
		newObj = atividadeRepository.save(newObj);
		insertHistory(newObj);
	}

	protected void insertHistory(Atividade obj) {
		Integer versao = atividadeHistoryRepository.findMaxVersao(obj) == null ? 1
				: atividadeHistoryRepository.findMaxVersao(obj) + 1;

		AtividadeHistory atividadeHistory = new AtividadeHistory(null, obj, obj.getDescricao(), obj.getConteudo(),
				obj.getGrupo(), obj.getStatus(), versao);
		atividadeHistoryRepository.save(atividadeHistory);
	}
}
