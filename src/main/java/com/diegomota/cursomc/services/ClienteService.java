package com.diegomota.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegomota.cursomc.domain.Cliente;
import com.diegomota.cursomc.repositories.ClienteRepository;
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
	
}
