package com.diegomota.cursomc.services;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diegomota.cursomc.domain.Categoria;
import com.diegomota.cursomc.domain.Cidade;
import com.diegomota.cursomc.domain.Cliente;
import com.diegomota.cursomc.domain.Endereco;
import com.diegomota.cursomc.domain.Estado;
import com.diegomota.cursomc.domain.ItemPedido;
import com.diegomota.cursomc.domain.Pagamento;
import com.diegomota.cursomc.domain.PagamentoComBoleto;
import com.diegomota.cursomc.domain.PagamentoComCartao;
import com.diegomota.cursomc.domain.Pedido;
import com.diegomota.cursomc.domain.Produto;
import com.diegomota.cursomc.domain.enums.EstadoPagamento;
import com.diegomota.cursomc.domain.enums.Perfil;
import com.diegomota.cursomc.domain.enums.TipoCliente;
import com.diegomota.cursomc.repositories.CategoriaRepository;
import com.diegomota.cursomc.repositories.CidadeRepository;
import com.diegomota.cursomc.repositories.ClienteRepository;
import com.diegomota.cursomc.repositories.EnderecoRepository;
import com.diegomota.cursomc.repositories.EstadoRepository;
import com.diegomota.cursomc.repositories.ItemPedidoRepository;
import com.diegomota.cursomc.repositories.PagamentoRepository;
import com.diegomota.cursomc.repositories.PedidoRepository;
import com.diegomota.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public void instantiateTestDataBase() {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Categoria 3");
		Categoria cat4 = new Categoria(null, "Categoria 4");
		Categoria cat5 = new Categoria(null, "Categoria 5");
		Categoria cat6 = new Categoria(null, "Categoria 6");
		Categoria cat7 = new Categoria(null, "Categoria 7");
		
		
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2=  new Produto(null, "Impressora", 800.00);
		Produto p3=  new Produto(null, "Mouse", 80.00);
		Produto p4=  new Produto(null, "Mesa de Escritório", 300.00);
		Produto p5=  new Produto(null, "Toalha", 50.00);
		Produto p6=  new Produto(null, "Cocha", 200.00);
		Produto p7=  new Produto(null, "Tv true color", 1280.00);
		Produto p8=  new Produto(null, "Roçadeira", 800.00);
		Produto p9=  new Produto(null, "Abajour", 100.00);
		Produto p10=  new Produto(null, "Pendente", 180.00);
		Produto p11=  new Produto(null, "Shampoo", 90.00);
			
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2,p3,p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriaRepository.save(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.save(Arrays.asList(p1,p2,p3, p4, p5, p6, p7, p8, p9, p10, p11));
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(cid1, cid2, cid3));
		
		
		Cliente cli1 = new Cliente(null, "Maria silva", "diegoalves123@gmail.com", "06097453535", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("23242567" , "91118989"));
		
		Cliente cli2 = new Cliente(null, "Ana Costa", "diegoalves1234@gmail.com", "54833564033", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("87774455" , "91119090"));

		
		Endereco e1 = new Endereco(null, "Rua flores", "300","Apto 303", "Jardim","38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105","Sala 800", "Centro","98797697699", cli1, cid2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106",null, "Centro","59087435", cli2, cid2);
		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2)); 
		cli2.getEnderecos().addAll(Arrays.asList(e3)); 
		
		clienteRepository.save(Arrays.asList(cli1,cli2));
		enderecoRepository.save(Arrays.asList(e1,e2,e3));
		
		
		
		Pedido ped1 = new Pedido(null, new Date(), cli1, e1);
		Pedido ped2 = new Pedido(null, new Date(), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, new Date(), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		
		pedidoRepository.save(Arrays.asList(ped1,ped2));
		pagamentoRepository.save(Arrays.asList(pagto1,pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 1000.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		
		itemPedidoRepository.save(Arrays.asList(ip1,ip2,ip3));

	}
	
}	
