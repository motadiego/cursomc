package com.diegomota.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diegomota.cursomc.domain.Cidade;
import com.diegomota.cursomc.domain.Cliente;
import com.diegomota.cursomc.domain.Endereco;
import com.diegomota.cursomc.domain.enums.Perfil;
import com.diegomota.cursomc.domain.enums.TipoCliente;
import com.diegomota.cursomc.dto.ClienteDTO;
import com.diegomota.cursomc.dto.ClienteNewDTO;
import com.diegomota.cursomc.repositories.CidadeRepository;
import com.diegomota.cursomc.repositories.ClienteRepository;
import com.diegomota.cursomc.repositories.EnderecoRepository;
import com.diegomota.cursomc.security.UserSS;
import com.diegomota.cursomc.services.exceptions.AuthorizationException;
import com.diegomota.cursomc.services.exceptions.DataIntegrationException;
import com.diegomota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente cli =  repo.findOne(id);
		
		if(cli == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
					+ ", Tipo: " + Cliente.class.getName());
		}
		
		
		return cli;
	}
	

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}
	
	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj , obj);
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
	
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
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
	
	
	public Cliente fromDTO(ClienteDTO objDTO){
	  return new Cliente(objDTO.getId() , objDTO.getNome(), objDTO.getEmail() , null , null, null) ;
	}
	

	public Cliente fromDTO(ClienteNewDTO objDTO){
		Cliente cli =  new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfCnpj(), TipoCliente.toEnum(objDTO.getTipo()), bCryptPasswordEncoder.encode( objDTO.getSenha()));
		Cidade cid  = cidadeRepository.findOne(objDTO.getCidadeId());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
	 
		
	
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg")  , fileName, "image");
		
		/* Caso queira salvar o caminho da imagem em um banco de dados do cliente
		URI uri =  s3Service.uploadFile(multipartFile);
		Cliente cli = repo.findOne(user.getId()); cli.
		setImageUrl(uri.toString());
		repo.save(cli);
		return uri;
		 */
	}
	
}
