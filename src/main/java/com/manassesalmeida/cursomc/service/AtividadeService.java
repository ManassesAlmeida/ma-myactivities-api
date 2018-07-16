package com.manassesalmeida.cursomc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.enums.Status;
import com.manassesalmeida.cursomc.dto.AtividadeDTO;
import com.manassesalmeida.cursomc.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private GrupoService grupoService;

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

		return atividadeRepository.save(newObj);
	}

	public Atividade update(AtividadeDTO obj, Integer id) {
		Atividade newObj = find(id);
		updateData(newObj, obj);
		
		return atividadeRepository.save(newObj);
	}

	public void delete(Integer id) {
		Atividade obj = find(id);
		obj.setStatus(Status.toEnum(2));
		atividadeRepository.save(obj);
	}

	private void updateData(Atividade newObj, AtividadeDTO obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setConteudo(obj.getConteudo());
		newObj.setGrupo(grupoService.find(obj.getGrupo()));
		newObj.setStatus(Status.toEnum(1));
	}

	public void duplicate(Integer id) {
		Atividade obj = find(id);
		Atividade newObj = new Atividade(null, obj.getDescricao(), obj.getConteudo(), obj.getGrupo(), obj.getStatus());
		newObj = atividadeRepository.save(newObj);
	}
}
