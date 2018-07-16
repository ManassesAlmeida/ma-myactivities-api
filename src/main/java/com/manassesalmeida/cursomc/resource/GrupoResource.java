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

import com.manassesalmeida.cursomc.domain.Grupo;
import com.manassesalmeida.cursomc.dto.GrupoDTO;
import com.manassesalmeida.cursomc.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoResource {

	@Autowired
	private GrupoService grupoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Grupo> find(@PathVariable Integer id){
		Grupo grupo = grupoService.find(id);
		return ResponseEntity.ok().body(grupo);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Grupo>> findAll(){
		List<Grupo> grupos = grupoService.findAll();
		return ResponseEntity.ok().body(grupos);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody GrupoDTO obj){
		Grupo grupo = grupoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(grupo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody GrupoDTO obj, @PathVariable Integer id){
		grupoService.update(obj, id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Grupo> delete(@PathVariable Integer id){
		grupoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/duplicate/{id}/{type}", method = RequestMethod.POST)
	public ResponseEntity<Grupo> duplicate(@PathVariable Integer id, @PathVariable String type){
		grupoService.duplicate(id, type);
		return ResponseEntity.noContent().build();
	}
	
}
