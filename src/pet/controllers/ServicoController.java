package pet.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.application.Util;
import pet.dao.ServicoDAO;
import pet.dao.UsuarioDAO;
import pet.model.Servico;
import pet.model.TipoUsuario;
import pet.model.Usuario;

@Named
@ViewScoped
public class ServicoController extends Controller<Servico> implements Serializable {

	private static final long serialVersionUID = 1651642114811762868L;

	private List<Servico> listaServico;

	public ServicoController() {
		super(new ServicoDAO());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashServico");
		entity = (Servico) flash.get("flashServico");
	}

	@Override
	public Servico getEntity() {
		if (entity == null)
			entity = new Servico();
		return entity;
	}

	@Override
	public void limpar() {
		super.limpar();
		listaServico = null;
	}

	public List<Servico> getListaServico() {
		if (listaServico == null) {
			ServicoDAO dao = new ServicoDAO();
			listaServico = dao.findAll();
		}
		return listaServico;
	}
}
