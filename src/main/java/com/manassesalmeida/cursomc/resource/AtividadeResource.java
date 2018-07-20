package com.manassesalmeida.cursomc.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.manassesalmeida.cursomc.domain.Atividade;
import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.dto.AtividadeDTO;
import com.manassesalmeida.cursomc.service.AtividadeService;

@RestController
@RequestMapping(value = "/atividades")
public class AtividadeResource {

	@Autowired
	private AtividadeService atividadeService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Atividade> find(@PathVariable Integer id){
		Atividade atividade = atividadeService.find(id);
		return ResponseEntity.ok().body(atividade);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Atividade>> findAll(){
		List<Atividade> atividades = atividadeService.findAll();
		return ResponseEntity.ok().body(atividades);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AtividadeDTO obj){
		Atividade atividade = atividadeService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(atividade.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AtividadeDTO obj, @PathVariable Integer id){
		atividadeService.update(obj, id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Atividade> delete(@PathVariable Integer id){
		atividadeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/duplicate/{id}", method = RequestMethod.POST)
	public ResponseEntity<Grupo> duplicate(@PathVariable Integer id){
		atividadeService.duplicate(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/restore/{id}", method = RequestMethod.POST)
	public ResponseEntity<Grupo> restore(@PathVariable Integer id){
		atividadeService.restoreVersion(id);
		return ResponseEntity.noContent().build();
	}
	
}
