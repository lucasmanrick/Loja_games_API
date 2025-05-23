package com.generation.loja_games.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja_games.model.Usuario;
import com.generation.loja_games.model.UsuarioLogin;
import com.generation.loja_games.repository.UsuarioRepository;
import com.generation.loja_games.security.JwtService;

import jakarta.validation.Valid;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired 
	private AuthenticationManager authenticationManager; //aki temos acesso aos usuarios que estão autenticados no sistema 
	
	
	public List<Usuario> getAllUsers () {
		return usuarioRepository.findAll();
	}
	
	public Optional<Usuario> cadastrarUsuario (Usuario usuario) {
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
			return Optional.empty();
		}
	
		
		
		if (Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears() >= 18) {
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário deve ser maior de 18 anos");
		
		
		
	}
	
	
	public Optional<Usuario> atualizarUsuario (@Valid @RequestBody Usuario usuario) {
		
		Optional<Usuario> newUserValidate = usuarioRepository.findByUsuario(usuario.getUsuario()); 
		
		if(newUserValidate.isPresent()) { //se o usuario existir no banco de dados
			
			if(newUserValidate.get().getId() == usuario.getId()) { //e seu id for igual ao id que esta solicitando a atualização de dados, permitimos a persistencia dos dados.
				usuario.setSenha(criptografarSenha(usuario.getSenha()));
				
				return Optional.ofNullable(usuarioRepository.save(usuario));
			}
		}else {
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		
		
		return Optional.empty();
		
		
	}
	
	
	public Optional<UsuarioLogin> autenticarUsuario (Optional<UsuarioLogin> usuarioLogin) {
		
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()); // primeiramente ele instancia uma classe com o usuario e senha e na linha de baixo valida o usuario, ou seja, verifica se esse usuario e senha existem e estao corretos
		
		Authentication authentication = authenticationManager.authenticate(credenciais); 
		
		
		if(authentication.isAuthenticated()) { // se a autenticação deu certo ele faz o que esta dentro desse if
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); //encontra o registro desse usuario logado
			
				if(usuario.isPresent()) { //instancia a classe usuarioLogin com os dados necessários (sem senha).
					usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setData_nascimento(usuario.get().getDataNascimento());
					usuarioLogin.get().setSenha("");
					usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
					
					return usuarioLogin;
				}
		}
		
		return Optional.empty();
	}
	
	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}
	
	
	
	public String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
}
