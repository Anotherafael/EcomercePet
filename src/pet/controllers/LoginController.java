package pet.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pet.application.Session;
import pet.application.Util;
import pet.dao.UsuarioDAO;
import pet.model.TipoUsuario;
import pet.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;

	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.verificarLoginSenha(getUsuario().getLogin(), Util.hashSHA256(getUsuario().getSenha()));

		if (usuario != null) {
			// adicionando um ussuario na sessao
			Session.getInstance().setAttribute("usuarioLogado", usuario);
			// redirecionando para o template
			if (usuario.getTipoUsuario().equals(TipoUsuario.CLIENTE))
				return "loja.xhtml?faces-redirect=true";
			if (usuario.getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR))
				return "loja.xhtml?faces-redirect=true";

		}
		Util.addErrorMessage("Login ou senha inválido.");
		return "";
	}

	public void limpar() {
		usuario = null;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
