package com.diegomota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.diegomota.cursomc.domain.Cliente;
import com.diegomota.cursomc.dto.ClienteDTO;
import com.diegomota.cursomc.repositories.ClienteRepository;
import com.diegomota.cursomc.services.exceptions.DataIntegrationException;
import com.diegomota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente cat =  repo.findOne(id);
		
		if(cat == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
					+ ", Tipo: " + Cliente.class.getName());
		}
		
		
		return cat;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newObj = find(cliente.getId());
		updateData(newObj , cliente);
		return repo.save(newObj);
	}
	

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrationException("Não é possível excluir um cliente porque há entidades relacionadas");
		}
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	
	
	/*****
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction (ASC ou DESC)
	 * @return0
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO clienteDTO){
	  return new Cliente(clienteDTO.getId() , clienteDTO.getNome(), clienteDTO.getEmail() , null , null) ;
	}
	
	private void updateData(Cliente newObj, Cliente cliente) {
		newObj.setNome(cliente.getNome());
		newObj.setEmail(cliente.getEmail());
	}
}
