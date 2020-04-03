package com.example.cursomodelagemconceitual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomodelagemconceitual.domain.Categoria;
import com.example.cursomodelagemconceitual.dto.CategoriaDTO;
import com.example.cursomodelagemconceitual.repositories.CategoriaRepository;
import com.example.cursomodelagemconceitual.services.exception.DataIntegretyException;
import com.example.cursomodelagemconceitual.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria newObj =  find(categoria.getId());
		updateData(newObj, categoria);
		return categoriaRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
		categoriaRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possível excluir uma categoria que tenha produtos");
		}
	}

	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria categoria) {
		newObj.setNome(categoria.getNome());
		
	}
}