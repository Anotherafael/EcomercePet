package pet.controllers;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.application.Session;
import pet.model.Usuario;

@Named
@ViewScoped
public class PerfilController implements Serializable {

	private static final long serialVersionUID = 1L;

	Usuario usuario = null;
	
	public Usuario getUsuario() {
		
		if (usuario == null) {
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			this.usuario = usuario;
		}
		return usuario;
	}
}
