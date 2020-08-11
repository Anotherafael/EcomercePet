package pet.controllers;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.application.Session;
import pet.model.TipoUsuario;
import pet.model.Usuario;

@Named
@ViewScoped
public class MenuController implements Serializable {

	private static final long serialVersionUID = -925765300233216226L;

	private Usuario usuarioLogado = null;

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (Usuario) Session.getInstance().getAttribute("usuarioLogado");			
		return usuarioLogado;
	}
	
	public boolean isAdmin() {
		
		getUsuarioLogado();
		if (usuarioLogado == null) { 
			return false;
		} if (usuarioLogado.getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR)) {
			return true;
		}
		return false;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}

}
