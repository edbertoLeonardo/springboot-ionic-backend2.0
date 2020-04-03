package com.example.cursomodelagemconceitual.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomodelagemconceitual.domain.Pedido;
import com.example.cursomodelagemconceitual.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoSservice;
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido categoria = pedidoSservice.find(id);
		return ResponseEntity.ok().body(categoria);
		
		
	}

}
