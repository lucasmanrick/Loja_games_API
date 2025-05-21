package com.generation.loja_games.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produto")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 300)
	@NotBlank
	@Size(min = 2, max = 300,  message = "o nome do produto deve conter no minimo 2 digitos e no maximo 300")
	private String nome;
	
	@Column(length = 50)
	@NotBlank
	@Size(min = 1, max = 50, message = "A plataforma compativel com o jogo deve conter no minimo 1 e no maximo 50 caracteres")
	private String compatibilidade;
	
	@Max(2025)
	@NotNull(message = "o ano que o jogo foi fabricado não pode ser nullo")
	private int ano;
	
	@Column(length = 5000)
	@Size(min = 0, max = 5000, message = "o link que referencia a imagem do produto deve conter entre 0 e 5000 caracteres")
	private String imagem;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCompatibilidade() {
		return compatibilidade;
	}

	public void setCompatibilidade(String compatibilidade) {
		this.compatibilidade = compatibilidade;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
