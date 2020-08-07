package pet.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.dao.ServicoDAO;
import pet.dao.UsuarioDAO;
import pet.model.Servico;

@Named
@ViewScoped
public class ConsultaServicoController implements Serializable{
	
	private static final long serialVersionUID = 5971844866316069324L;
	
	private int tipoFiltro = 1;
	private String filtro;
	private List<Servico> listaServico;
	
	public void pesquisar() {
		ServicoDAO dao = new ServicoDAO();
//		if (tipoFiltro == 1)
		listaServico = dao.findByNome(getFiltro());
	}
	
	public String novoServico() {
		return "servico.xhtml?faces-redirect=true";
	}
	
	public String editar(Servico servico) {
		ServicoDAO dao = new ServicoDAO();
		servico = dao.findById(servico.getId());
		
		Flash flash = FacesContext.getCurrentInstance().
						getExternalContext().getFlash();
		
		flash.put("flashServico", servico);
		return "servico.xhtml?faces-redirect=true";
	}

	public List<Servico> getListaServico() {
		if (listaServico == null) {
			listaServico = new ArrayList<Servico>();
			ServicoDAO dao = new ServicoDAO();
			listaServico = dao.findAll();
			pesquisar();
		}
		return listaServico;
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
