package com.manassesalmeida.cursomc.service;

import java.util.Date;
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

	@Autowired
	private AtividadeHistoryService atividadeHistoryService;
	

	
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
		newObj.setHoraUltimaAlteracao(new Date(System.currentTimeMillis()));
		newObj.setStatus(Status.toEnum(1));
	}

	public void duplicate(Integer id) {
		Atividade obj = find(id);

		if (obj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível duplicar uma atividade deletada.");
		}

		Atividade newObj = new Atividade(null, obj.getDescricao(), obj.getConteudo(), obj.getGrupo(),
				new Date(System.currentTimeMillis()), obj.getStatus());
		newObj = atividadeRepository.save(newObj);
		insertHistory(newObj);
	}
	
	public void restoreVersion(Integer id) {
		AtividadeHistory atividadeHistory = atividadeHistoryService.find(id);
		Atividade atividade = find(atividadeHistory.getAtividade().getId());
		restoreVersionEditAtividade(atividadeHistory, atividade);

		atividade = atividadeRepository.save(atividade);
		insertHistory(atividade);
	}
	
	private void restoreVersionEditAtividade(AtividadeHistory objOld, Atividade objNew) {
		objNew.setStatus(objOld.getStatus());
		objNew.setConteudo(objOld.getConteudo());
		objNew.setDescricao(objOld.getDescricao());
		objNew.setGrupo(objOld.getGrupo());
		objNew.setHoraUltimaAlteracao(new Date(System.currentTimeMillis()));
	}
	
	protected void insertHistory(Atividade obj) {
		Integer versao = atividadeHistoryRepository.findMaxVersao(obj) == null ? 1
				: atividadeHistoryRepository.findMaxVersao(obj) + 1;

		AtividadeHistory atividadeHistory = new AtividadeHistory(null, obj, obj.getDescricao(), obj.getConteudo(),
				obj.getGrupo(), obj.getHoraUltimaAlteracao(), obj.getStatus(), versao);
		atividadeHistoryRepository.save(atividadeHistory);
	}
}
