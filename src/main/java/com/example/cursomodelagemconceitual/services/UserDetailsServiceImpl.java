package com.example.cursomodelagemconceitual.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cursomodelagemconceitual.domain.Cliente;
import com.example.cursomodelagemconceitual.repositories.ClienteRepository;
import com.example.cursomodelagemconceitual.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cliente.getId(),cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
