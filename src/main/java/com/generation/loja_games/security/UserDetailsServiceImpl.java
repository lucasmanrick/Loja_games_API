package com.generation.loja_games.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja_games.model.Usuario;
import com.generation.loja_games.repository.UsuarioRepository;

@Service //indica que é uma classe de serviço
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // vai receber o usuario e validar no banco de dados

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(username);

		if (usuario.isPresent())// se o usuario existir
			return new UserDetailsImpl(usuario.get()); // chama o construtor da classe userDetails e instancia com o usuario.
		else
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erro ao Autenticar o Usuário");  //se o usuario nao existir retorna erro.
			
	}
}