package com.generation.loja_games.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255)
	@NotBlank
	@Size(min = 2, max = 255,  message = "A categoria deve conter no minimo 2 digitos e no maximo 255")
	private String descricao;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.REMOVE) // aki definimos que o campo/atributo dessa tabela tema, fetch sendo lazy ele só carrega os dados desse campo caso requisitemos ele, se não requisitar-mos ele não puxa os dados, já se colocassemos o tipo EAGER ele ja traria os dados. O mappedBy referencia ao campo/atributo "TEMA" da tabela POSTAGEM e por fim o cascade faz com que se um registro for deletado ele deleta das demais tabelas que tem essa linkagem/relacionamento.
	@JsonIgnoreProperties("categoria") // ignora recursividade ao fazer um get, ou seja ao fazer um get nessa tabela ele nao fica referenciando a tabela postagem e a tabela postagem voltando a referenciar a esta infinitamente.
	private List<Produto> produto;
  
	
	
	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
