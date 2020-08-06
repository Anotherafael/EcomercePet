package pet.controllers;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.dao.ServicoDAO;
import pet.model.Servico;

@Named
@ViewScoped
public class ServicoController extends Controller<Servico> implements Serializable{

private static final long serialVersionUID = 1651642114811762868L;
	
	public ServicoController() {
		super(new ServicoDAO());
		Flash flash = FacesContext.getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("flashServico");
		entity = (Servico) flash.get("flashServico");
	}
	
	@Override
	public Servico getEntity() {
		if (entity == null)
			entity = new Servico();
		return entity;
	}
}
