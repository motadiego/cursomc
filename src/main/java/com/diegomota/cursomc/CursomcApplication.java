package com.diegomota.cursomc;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	
	
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
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 1, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 1000.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		
		itemPedidoRepository.save(Arrays.asList(ip1,ip2,ip3));
		
	}

}

