package com.diegomota.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diegomota.cursomc.domain.Categoria;
import com.diegomota.cursomc.dto.CategoriaDTO;
import com.diegomota.cursomc.services.CategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@ApiOperation(value="Busca por id") // usado para gerar uma descrição do método na documentação do SWAGGER
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
 	}
	
	
	@ApiOperation(value="Insere uma categoria") 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method= RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Atualiza uma categoria") 
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value="/{id}" , method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO , @PathVariable Integer id){
		categoriaDTO.setId(id);
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria = service.update(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@ApiOperation(value="Remove uma categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente") })
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value="/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@ApiOperation(value="Retorna todas as categorias") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
 	}
	
	
	@ApiOperation(value="Retorna todas as categorias com paginação") 
	@RequestMapping(value="/page" , method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page" ,  defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" ,  defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy" ,  defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" ,  defaultValue="ASC") String direction) {
			
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
 	}	
}