package com.virandoheroi.virandoheroi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virandoheroi.virandoheroi.model.Usuario;
import com.virandoheroi.virandoheroi.model.Vaga;
import com.virandoheroi.virandoheroi.service.UsuarioService;

@Controller
@RequestMapping({ "/usuario", "/usuario/" })
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping({ "/entrar" })
	public String entrar() {
		return "entrar";
	}

	@GetMapping({ "/cadastro_candidato" })
	public ModelAndView cadastrarCandidato() {
		return new ModelAndView("cadastro_candidato");
	}

	@ResponseBody
	@PostMapping({ "/cadastro_candidato" })
	public String cadastrarCandidato(@ModelAttribute Usuario model) {
		Usuario usuario = usuarioService.cadastrarCandidato(model);
		return "Cadastrado com Sucesso! " + usuario.getNome();
	}

	@GetMapping({ "/cadastro_ong" })
	public ModelAndView cadastrarOng() {
		return new ModelAndView("cadastro_ong");
	}

	@ResponseBody
	@PostMapping({ "/cadastro_ong" })
	public String cadastrarOng(@ModelAttribute Usuario model) {
		Usuario usuario = usuarioService.cadastrarOng(model);
		return "Cadastrado com Sucesso! " + usuario.getNome();
	}

	@GetMapping({ "/{id}", "/{id}/" })
	public ModelAndView buscarUsuario(@PathVariable("id") Integer id) {

		Usuario usuario = usuarioService.buscarUsuario(id);

		if (usuario.getTipo() == 1) {
			return new ModelAndView("cadastro_candidato").addObject("usuario", usuario);
		} else {
			return new ModelAndView("cadastro_ong").addObject("usuario", usuario).addObject("countVaga",
					usuario.getVagas().size() + 1);
		}

	}

	@GetMapping({ "/cadastro_vaga" })
	public ModelAndView cadastrarVaga() {
		return new ModelAndView("cadastro_vaga");
	}

	@ResponseBody
	@PostMapping({ "/cadastro_vaga" })
	public String cadastrarVaga(@ModelAttribute Vaga model) {
		Usuario usuario = usuarioService.cadastrarVaga(model);
		return "teste" + usuario.getNome() + usuario.getVagas().size();
	}

}