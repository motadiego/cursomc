package com.diegomota.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.diegomota.cursomc.domain.Categoria;
import com.diegomota.cursomc.domain.Cidade;
import com.diegomota.cursomc.domain.Cliente;
import com.diegomota.cursomc.domain.Endereco;
import com.diegomota.cursomc.domain.Estado;
import com.diegomota.cursomc.domain.Produto;
import com.diegomota.cursomc.domain.enums.TipoCliente;
import com.diegomota.cursomc.repositories.CategoriaRepository;
import com.diegomota.cursomc.repositories.CidadeRepository;
import com.diegomota.cursomc.repositories.ClienteRepository;
import com.diegomota.cursomc.repositories.EnderecoRepository;
import com.diegomota.cursomc.repositories.EstadoRepository;
import com.diegomota.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2=  new Produto(null, "Impressora", 800.00);
		Produto p3=  new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2)); 	
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1, cat2));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(cid1, cid2, cid3));
		
		
		Cliente cli1 = new Cliente(null, "Maria silva", "maria@gmail.com", "06097453535", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("23242567" , "91118989"));
		
		
		Endereco e1 = new Endereco(null, "Rua flores", "300","Apto 303", "Jardim","38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105","Sala 800", "Centro","98797697699", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2)); 
		
		clienteRepository.save(cli1);
		enderecoRepository.save(Arrays.asList(e1,e2));
		
	}

}

