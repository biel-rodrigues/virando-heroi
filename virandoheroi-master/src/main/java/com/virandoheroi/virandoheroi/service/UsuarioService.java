package com.virandoheroi.virandoheroi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.virandoheroi.virandoheroi.model.Role;
import com.virandoheroi.virandoheroi.model.Usuario;
import com.virandoheroi.virandoheroi.model.Vaga;
import com.virandoheroi.virandoheroi.repository.RoleRepository;
import com.virandoheroi.virandoheroi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository ur;

	@Autowired
	RoleRepository rr;

	/*
	 * método que cadastra ong
	 */
	public Usuario cadastrarOng(Usuario model) {

		/*
		 * procura se o usuário a ser cadastrado ja existe
		 */
		Usuario usuario = new Usuario();
		if (model.getId() != null) {
			usuario = buscarUsuario(model.getId());
		}

		/*
		 * setta usuario com os dados da tela, como melhoria poderia ser sobrescrito um
		 * construtor de usuario
		 */
		usuario.setNome(model.getNome());
		usuario.setTelefone(model.getTelefone());
		usuario.setUrlImg(model.getUrlImg());
		// usuario.setEmail(model.getEmail());
		/*
		 * criptografa dados(neste caso email e senha) enviados ao banco
		 */
		usuario.setEmail(new BCryptPasswordEncoder().encode(model.getEmail()));
		usuario.setSenha(new BCryptPasswordEncoder().encode(model.getSenha()));
		usuario.setEndereco(model.getEndereco());
		usuario.setDescricao(model.getDescricao());
		usuario.setTipo(2);

		/*
		 * lógica que cria roles que mais tarde serão usadas no controle de permissões
		 */
		Optional<Role> optionalRole = rr.findById("ROLE_ONG");

		List<Role> roles = (List<Role>) rr.findAll();

		List<Usuario> usuarios = (List<Usuario>) ur.findAll();

		Role role;
		if (optionalRole.isPresent()) {
			role = optionalRole.get();
			usuarios.add(usuario);
			role.setUsuarios(usuarios);
		} else {
			role = new Role();
			role.setNomeRole("ROLE_ONG");
			usuarios.add(usuario);
			role.setUsuarios(usuarios);

			if (!(roles.contains(role))) {
				roles.add(role);
			}
		}

		usuario.setRoles(roles);

		rr.save(role);

		return ur.save(usuario);
	}

	/*
	 * método que cadastra candidato
	 */
	public Usuario cadastrarCandidato(Usuario model) {

		/*
		 * procura se o usuário a ser cadastrado ja existe
		 */
		Usuario usuario = new Usuario();
		if (model.getId() != null) {
			usuario = buscarUsuario(model.getId());
		}

		/*
		 * setta usuario com os dados da tela, como melhoria poderia ser sobrescrito um
		 * construtor de usuario
		 */
		usuario.setNome(model.getNome());
		usuario.setTelefone(model.getTelefone());
		/*
		 * criptografa dados(neste caso email e senha) enviados ao banco
		 */
		usuario.setEmail(new BCryptPasswordEncoder().encode(model.getEmail()));
		usuario.setSenha(new BCryptPasswordEncoder().encode(model.getSenha()));
		usuario.setEndereco(model.getEndereco());
		usuario.setTipo(1);

		/*
		 * lógica que cria roles que mais tarde serão usadas no controle de permissões
		 */
		Optional<Role> optionalRole = rr.findById("ROLE_CANDIDATO");

		List<Role> roles = (List<Role>) rr.findAll();

		List<Usuario> usuarios = (List<Usuario>) ur.findAll();

		Role role;
		if (optionalRole.isPresent()) {
			role = optionalRole.get();
			usuarios.add(usuario);
			role.setUsuarios(usuarios);
		} else {
			role = new Role();
			role.setNomeRole("ROLE_CANDIDATO");
			usuarios.add(usuario);
			role.setUsuarios(usuarios);

			if (!(roles.contains(role))) {
				roles.add(role);
			}
		}

		usuario.setRoles(roles);

		rr.save(role);

		return ur.save(usuario);
	}

	/*
	 * método que cadastra vagas
	 */
	public Usuario cadastrarVaga(Vaga model) {

		/*
		 * usuario que está buscando as vagas
		 */
		Usuario usuario = buscarUsuario(1);

		/*
		 * verificando se o usuario há vagas cadastradas, se tiver, atribui para a lista
		 * que receberá uma nova vaga na tela
		 */
		List<Vaga> vagas = new ArrayList<Vaga>();
		if (!usuario.getVagas().isEmpty()) {
			vagas = usuario.getVagas();
		}

		Vaga vaga = new Vaga();
		vaga.setTitulo(model.getTitulo());
		vaga.setDescricao(model.getDescricao());
		vaga.setDataExpiracao(model.getDataExpiracao());
		vaga.setDataPostagem(model.getDataPostagem());
		vagas.add(vaga);
		usuario.setVagas(vagas);

		return ur.save(usuario);
	}

	public Usuario buscarUsuario(Integer id) {
		return ur.findById(id).get();
	}
}
