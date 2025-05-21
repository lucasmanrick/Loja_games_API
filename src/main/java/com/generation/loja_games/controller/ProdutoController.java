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

import com.generation.loja_games.model.Produto;
import com.generation.loja_games.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/produtos")
public class ProdutoController {

	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping() 
	private ResponseEntity<List<Produto>> getAllProducts () {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}") 
	private ResponseEntity<Produto> getProductById (@PathVariable long id) {
		if(produtoRepository.existsById(id)) {
			return ResponseEntity.ok(produtoRepository.findById(id).get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("nome/{nomeJogo}") 
	private ResponseEntity<List<Produto>> getAllProductsByName (@PathVariable String nomeJogo) {
		return ResponseEntity.ok(produtoRepository.findAllProductsByNomeContainsIgnoreCase(nomeJogo));
	}
	
	
	@PostMapping()
	private ResponseEntity<Produto> create (@Valid @RequestBody Produto produto) {
		if(produto.getId() == null) {
			return ResponseEntity.ok(produtoRepository.save(produto));
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	
	@PutMapping()
	private ResponseEntity<Produto> put (@Valid @RequestBody Produto produto) {
		if(produto.getId() != null) {
			if(produtoRepository.existsById(produto.getId())) {
				return ResponseEntity.ok(produtoRepository.save(produto));
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void delete (@PathVariable long id) {
		if(produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			return;
		}
			
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
}
