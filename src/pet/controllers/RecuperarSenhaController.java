package pet.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

import pet.application.Util;
import pet.dao.UsuarioDAO;
import pet.model.Usuario;

@Named
@RequestScoped
public class RecuperarSenhaController extends Controller<Usuario> implements Serializable {

	public RecuperarSenhaController() {
		super(new UsuarioDAO());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashUsuario");
		entity = (Usuario) flash.get("flashUsuario");
	}

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private String senha;
	private String email;
	private boolean emailValido = false;

	public void recuperar() {
		
		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		listaUsuario = dao.findAll();
		
		for (Usuario usuario2 : listaUsuario) {
			if (usuario2.getEmail().equals(getEmail())) {
				usuario = usuario2;
				editar(usuario);
				this.emailValido = true;
				return;
			}
		}
		Util.addErrorMessage("Email não existe cadastrado.");
	}
	
	public void alterarSenha () {
		System.out.println("Alterou a senha");
		alterar();
	}
	
	public boolean isEmailValido() {
		return emailValido;
	}
	
	public void limpar() {
		usuario = null;
	}
	
	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public String getEmail() {
		return email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean validarDados() {
		if (getEntity().getNome().isBlank()) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
		// gerando o hash da senha
		String senha = Util.hashSHA256(getEntity().getSenha());
		getEntity().setSenha(senha);
		
		return true;
	}
	
	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}
}
