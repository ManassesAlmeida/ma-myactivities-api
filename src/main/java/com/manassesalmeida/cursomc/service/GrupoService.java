package com.manassesalmeida.cursomc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.domain.GrupoHistory;
import com.manassesalmeida.cursomc.domain.enums.Status;
import com.manassesalmeida.cursomc.dto.GrupoDTO;
import com.manassesalmeida.cursomc.repository.AtividadeRepository;
import com.manassesalmeida.cursomc.repository.GrupoHistoryRepository;
import com.manassesalmeida.cursomc.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private GrupoHistoryRepository grupoHistoryRepository;

	@Autowired
	private AtividadeService atividadeService;

	public Grupo find(Integer id) {
		try {
			return grupoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Grupo informado não existe.");
		}
	}

	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}

	public Grupo insert(GrupoDTO obj) {
		Grupo grupo = new Grupo();
		grupo.setId(null);
		updateData(grupo, obj);

		grupo = grupoRepository.save(grupo);
		insertHistory(grupo);

		return grupo;
	}

	public Grupo update(GrupoDTO obj, Integer id) {
		Grupo newObj = find(id);

		if (!newObj.getEditavel()) {
			throw new IllegalArgumentException("Não é possível atualizar um grupo não-editável.");
		}
		if (newObj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível atualizar um grupo deletado.");
		}

		updateData(newObj, obj);

		newObj = grupoRepository.save(newObj);
		insertHistory(newObj);

		return newObj;
	}

	public void delete(Integer id) {
		Status statusDeletado = Status.toEnum(2);
		Grupo obj = find(id);

		if (obj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível deletar este grupo, pois já foi deletado.");
		}

		if (!obj.getEditavel()) {
			throw new IllegalArgumentException("Grupo não pode ser excluído.");
		}

		obj.setStatus(statusDeletado);
		obj = grupoRepository.save(obj);
		insertHistory(obj);

		List<Atividade> atividades = atividadeRepository.findAtividadeByGrupo(obj);
		for (Atividade atividade : atividades) {
			if (atividade.getStatus().toString().equalsIgnoreCase("DELETADO")) {
				continue;
			}
			atividade.setStatus(statusDeletado);
			atividade = atividadeRepository.save(atividade);
			atividadeService.insertHistory(atividade);
		}
	}

	private void updateData(Grupo newObj, GrupoDTO obj) {
		newObj.setNome(obj.getNome());
		newObj.setEditavel(true);
		newObj.setHoraUltimaAlteracao(new Date(System.currentTimeMillis()));
		newObj.setStatus(Status.toEnum(1));
	}

	public void duplicate(Integer id, String type) {
		Grupo obj = find(id);
		List<Atividade> atividades = null;

		if (obj.getStatus().toString().equalsIgnoreCase("DELETADO")) {
			throw new IllegalArgumentException("Não é possível duplicar um grupo deletado.");
		}

		if (type.equalsIgnoreCase("g")) {
			atividades = new ArrayList<>();
		} else if (type.equalsIgnoreCase("ga")) {
			atividades = atividadeRepository.findAtividadeByGrupo(obj);
		} else {
			throw new IllegalArgumentException("Argumento de duplicação é inválido.");
		}

		Grupo newObj = new Grupo(null, obj.getNome(), atividades, new Date(System.currentTimeMillis()), true,
				obj.getStatus());

		newObj = grupoRepository.save(newObj);
		insertHistory(newObj);

		List<Atividade> atividadesNew = new ArrayList<>();
		for (Atividade atividade : atividades) {
			atividadesNew.add(new Atividade(null, atividade.getDescricao(), atividade.getConteudo(), newObj,
					atividade.getStatus()));
		}

		atividadesNew = atividadeRepository.saveAll(atividadesNew);

		for (Atividade atividade : atividadesNew) {
			atividadeService.insertHistory(atividade);
		}
	}

	protected void insertHistory(Grupo obj) {
		Integer versao = grupoHistoryRepository.findMaxVersao(obj) == null ? 1
				: grupoHistoryRepository.findMaxVersao(obj) + 1;

		GrupoHistory grupoHistory = new GrupoHistory(null, obj, obj.getNome(), obj.getAtividades(),
				obj.getHoraUltimaAlteracao(), obj.getEditavel(), obj.getStatus(), versao);
		grupoHistoryRepository.save(grupoHistory);
	}
}
