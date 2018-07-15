package com.manassesalmeida.cursomc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.Grupo;
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
		grupo.setNome(obj.getNome());
		grupo.setEditavel(true);
		grupo.setHoraUltimaAlteracao(new Date(System.currentTimeMillis()));
		return grupoRepository.save(grupo);
	}
	
	public Grupo update(GrupoDTO obj, Integer id) {
		Grupo newObj = find(id);
		
		if(!newObj.getEditavel()) {
			throw new IllegalArgumentException("Não é possível atualizar um grupo não-editável.");
		}
		
		newObj.setEditavel(true);
		updateData(newObj, obj);
		return grupoRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		Grupo obj = find(id);
		
		if(!obj.getEditavel()) {
			throw new IllegalArgumentException("Grupo não pode ser excluído.");
		}
		grupoRepository.delete(obj);
	}
	
	private void updateData(Grupo newObj, GrupoDTO obj) {
		newObj.setNome(obj.getNome());
		newObj.setHoraUltimaAlteracao(new Date(System.currentTimeMillis()));
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
		
		Grupo newObj = new Grupo(null, obj.getNome(), atividades, new Date(System.currentTimeMillis()), true);
		newObj = grupoRepository.save(newObj);
		
		List<Atividade> atividadesNew = new ArrayList<>();
		for (Atividade atividade : atividades) {
			atividadesNew.add(new Atividade(null, atividade.getDescricao(), atividade.getConteudo(), newObj));
		}
		
		atividadeRepository.saveAll(atividadesNew);
	}
}
