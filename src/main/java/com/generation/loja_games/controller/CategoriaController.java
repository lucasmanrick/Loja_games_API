package com.generation.loja_games.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja_games.model.Categoria;
import com.generation.loja_games.model.Produto;
import com.generation.loja_games.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/categorias")
public class CategoriaController {

	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@GetMapping() 
	private ResponseEntity<List<Categoria>> getAllCategorias () {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}") 
	private ResponseEntity<Categoria> getCategoriaById (@PathVariable long id) {
		if(categoriaRepository.existsById(id)) {
			return ResponseEntity.ok(categoriaRepository.findById(id).get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("descricao/{descricao}") 
	private ResponseEntity<List<Categoria>> getAllCategoriasByName (@PathVariable String descricao) {
		return ResponseEntity.ok(categoriaRepository.findAllProductsByDescricaoContainsIgnoreCase(descricao));
	}
	
	
	@PostMapping()
	private ResponseEntity<Categoria> create (@Valid @RequestBody Categoria categoria) {
		if(categoria.getId() == null) {
			return ResponseEntity.ok(categoriaRepository.save(categoria));
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	
	@PutMapping()
	private ResponseEntity<Categoria> put (@Valid @RequestBody Categoria categoria) {
		if(categoria.getId() != null) {
			if(categoriaRepository.existsById(categoria.getId())) {
				return ResponseEntity.ok(categoriaRepository.save(categoria));
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void delete (@PathVariable long id) {
		if(categoriaRepository.existsById(id)) {
			categoriaRepository.deleteById(id);
			return;
		}
			
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
