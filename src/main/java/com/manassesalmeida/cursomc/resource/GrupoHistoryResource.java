package com.manassesalmeida.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manassesalmeida.cursomc.domain.GrupoHistory;
import com.manassesalmeida.cursomc.service.GrupoHistoryService;

@RestController
@RequestMapping(value = "/grupos-historico")
public class GrupoHistoryResource {

	@Autowired
	private GrupoHistoryService grupoHistoryService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GrupoHistory> find(@PathVariable Integer id) {
		GrupoHistory grupoHistory = grupoHistoryService.find(id);
		return ResponseEntity.ok().body(grupoHistory);
	}

	@RequestMapping(value = "/grupo/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<GrupoHistory>> findAll(@PathVariable Integer id) {
		List<GrupoHistory> grupoHistoryList = grupoHistoryService.findAllByGrupo(id);
		return ResponseEntity.ok().body(grupoHistoryList);
	}
}