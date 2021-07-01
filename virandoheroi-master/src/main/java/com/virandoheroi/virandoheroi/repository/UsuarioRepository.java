package com.virandoheroi.virandoheroi.repository;

import org.springframework.data.repository.CrudRepository;

import com.virandoheroi.virandoheroi.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	Usuario findByEmail(String email);
	
}