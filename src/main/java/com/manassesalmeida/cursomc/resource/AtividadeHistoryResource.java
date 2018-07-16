package com.manassesalmeida.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manassesalmeida.cursomc.domain.AtividadeHistory;
import com.manassesalmeida.cursomc.service.AtividadeHistoryService;

@RestController
@RequestMapping(value = "/atividades-historico")
public class AtividadeHistoryResource {

	@Autowired
	private AtividadeHistoryService atividadeHistoryService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AtividadeHistory> find(@PathVariable Integer id) {
		AtividadeHistory atividadeHistory = atividadeHistoryService.find(id);
		return ResponseEntity.ok().body(atividadeHistory);
	}

	@RequestMapping(value = "/atividade/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<AtividadeHistory>> findAll(@PathVariable Integer id) {
		List<AtividadeHistory> atividadeHistoryList = atividadeHistoryService.findAllByAtividade(id);
		return ResponseEntity.ok().body(atividadeHistoryList);
	}
}