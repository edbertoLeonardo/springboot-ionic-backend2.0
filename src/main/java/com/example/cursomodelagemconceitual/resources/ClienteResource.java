package com.example.cursomodelagemconceitual.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.cursomodelagemconceitual.domain.Cliente;
import com.example.cursomodelagemconceitual.dto.ClienteDTO;
import com.example.cursomodelagemconceitual.dto.ClienteNewDTO;
import com.example.cursomodelagemconceitual.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService categoriaService;
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente categoria = categoriaService.find(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = categoriaService.fromDTO(objDto);
		obj = categoriaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value= "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaDto, @PathVariable Integer id){
		Cliente obj = categoriaService.fromDTO(categoriaDto);
		obj.setId(id);
		obj = categoriaService.update(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = categoriaService.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value= "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		Page<Cliente> list = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	

}
