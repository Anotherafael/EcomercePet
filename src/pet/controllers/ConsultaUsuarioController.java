package pet.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.dao.UsuarioDAO;
import pet.model.Usuario;

@Named
@ViewScoped
public class ConsultaUsuarioController implements Serializable{
	
	private static final long serialVersionUID = 5971844866316069324L;
	
	private int tipoFiltro = 1;
	private String filtro;
	private List<Usuario> listaUsuario;
	
	public void pesquisar() {
		UsuarioDAO dao = new UsuarioDAO();
//		if (tipoFiltro == 1)
		listaUsuario = dao.findByNome(getFiltro());
	}
	
	public String novoUsuario() {
		return "usuario.xhtml?faces-redirect=true";
	}
	
	public String editar(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		usuario = dao.findById(usuario.getId());
		
		Flash flash = FacesContext.getCurrentInstance().
						getExternalContext().getFlash();
		
		flash.put("flashUsuario", usuario);
		return "usuario.xhtml?faces-redirect=true";
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

}
