package com.manassesalmeida.cursomc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.domain.enums.Status;
import com.manassesalmeida.cursomc.dto.GrupoDTO;
import com.manassesalmeida.cursomc.repository.AtividadeRepository;
import com.manassesalmeida.cursomc.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	public Grupo find(Integer id) {
		try{
			return grupoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Grupo informado não existe.");
		}
	}
	
	public List<Grupo> findAll(){
		return grupoRepository.findAll();
	}
	
	public Grupo insert(GrupoDTO obj) {
		Grupo grupo = new Grupo();
		grupo.setId(null);
		updateData(grupo, obj);
		return grupoRepository.save(grupo);
	}
	
	public Grupo update(GrupoDTO obj, Integer id) {
		Grupo newObj = find(id);
		
		if(!newObj.getEditavel()) {
			throw new IllegalArgumentException("Não é possível atualizar um grupo não-editável.");
		}
		
		updateData(newObj, obj);
		return grupoRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		Status statusDeletado = Status.toEnum(2);
		Grupo obj = find(id);
		obj.setStatus(statusDeletado);
		
		if(!obj.getEditavel()) {
			throw new IllegalArgumentException("Grupo não pode ser excluído.");
		}
		grupoRepository.save(obj);
		
		List<Atividade> atividades = atividadeRepository.findAtividadeByGrupo(obj);
		for (Atividade atividade : atividades) {
			atividade.setStatus(statusDeletado);
			atividadeRepository.save(atividade);
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
		
		if(type.equalsIgnoreCase("g")) {
			atividades = new ArrayList<>();
		} else if (type.equalsIgnoreCase("ga")) {
			atividades = atividadeRepository.findAtividadeByGrupo(obj);
		} else {
			throw new IllegalArgumentException("Argumento de duplicação é inválido.");
		}
		
		Grupo newObj = new Grupo(null, obj.getNome(), atividades, new Date(System.currentTimeMillis()), true, obj.getStatus());
		newObj = grupoRepository.save(newObj);
		
		List<Atividade> atividadesNew = new ArrayList<>();
		for (Atividade atividade : atividades) {
			atividadesNew.add(new Atividade(null, atividade.getDescricao(), atividade.getConteudo(), newObj, atividade.getStatus()));
		}
		
		atividadeRepository.saveAll(atividadesNew);
	}
}
