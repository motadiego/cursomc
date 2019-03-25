package com.diegomota.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diegomota.cursomc.domain.Cidade;
import com.diegomota.cursomc.dto.CidadeDTO;
import com.diegomota.cursomc.services.CidadeService;

@RestController
@RequestMapping(value="/estados")
public class CidadeResource {
	
	@Autowired
	private CidadeService service;
	
	
	@RequestMapping(value="/{idEstado}/cidades",method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer idEstado) {
		List<Cidade> list = service.findCidades(idEstado);
		List<CidadeDTO> listDTO = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
 	}
	
	

}