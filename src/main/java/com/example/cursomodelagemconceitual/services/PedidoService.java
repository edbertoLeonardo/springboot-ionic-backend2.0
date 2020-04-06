package com.example.cursomodelagemconceitual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cursomodelagemconceitual.domain.ItemPedido;
import com.example.cursomodelagemconceitual.domain.PagamentoComBoleto;
import com.example.cursomodelagemconceitual.domain.Pedido;
import com.example.cursomodelagemconceitual.domain.enums.EstadoPagamento;
import com.example.cursomodelagemconceitual.repositories.ItemPedidoRepository;
import com.example.cursomodelagemconceitual.repositories.PagamentoRepository;
import com.example.cursomodelagemconceitual.repositories.PedidoRepository;
import com.example.cursomodelagemconceitual.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoService.find(itemPedido.getpProduto().getId()));
			itemPedido.setPreco(itemPedido.getpProduto().getPreco());
			itemPedido.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}
}
