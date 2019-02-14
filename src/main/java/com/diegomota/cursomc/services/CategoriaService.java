package com.diegomota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.diegomota.cursomc.domain.Categoria;
import com.diegomota.cursomc.dto.CategoriaDTO;
import com.diegomota.cursomc.repositories.CategoriaRepository;
import com.diegomota.cursomc.services.exceptions.DataIntegrationException;
import com.diegomota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Categoria cat =  repo.findOne(id);
		
		if(cat == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
					+ ", Tipo: " + Categoria.class.getName());
		}
		
		
		return cat;
	}
	
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}
	
	
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrationException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	
	
	/*****
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction (ASC ou DESC)
	 * @return0
	 */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO){
		return new Categoria(categoriaDTO.getId() , categoriaDTO.getNome());
	}
}
