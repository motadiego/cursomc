package com.diegomota.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegomota.cursomc.domain.ItemPedido;
import com.diegomota.cursomc.domain.PagamentoComBoleto;
import com.diegomota.cursomc.domain.Pedido;
import com.diegomota.cursomc.domain.enums.EstadoPagamento;
import com.diegomota.cursomc.repositories.ClienteRepository;
import com.diegomota.cursomc.repositories.ItemPedidoRepository;
import com.diegomota.cursomc.repositories.PagamentoRepository;
import com.diegomota.cursomc.repositories.PedidoRepository;
import com.diegomota.cursomc.repositories.ProdutoRepository;
import com.diegomota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	/***
	 * Recupera uma instancia de MockEmailService definido nas classes TestConfig, DevConfig
	 */
	@Autowired
	private EmailService emailService;
	
	
	/***
	 * Recupera uma instancia de SmtpEmailService definido nas classes TestConfig, DevConfig
	 */
	
	@Autowired
	private EmailService smtpEmailService;
	
	
	public Pedido find(Integer id) {
		Pedido cat =  repo.findOne(id);
		
		if(cat == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
					+ ", Tipo: " + Pedido.class.getName());
		}
		
		
		return cat;
	}
	
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, obj.getInstante());
			
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.save(obj.getItens());
	
		//emailService.sendOrderConfirmationEmail(obj);
		//smtpEmailService.sendOrderConfirmationEmail(obj);
		smtpEmailService.sendOrderConfirmationHtmlEmail(obj);
		
		
		return obj;
	}
}
