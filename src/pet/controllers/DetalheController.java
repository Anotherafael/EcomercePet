package pet.controllers;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.model.Venda;

@Named
@ViewScoped
public class DetalheController implements Serializable{

	private static final long serialVersionUID = -6719949836747729139L;

	private Venda venda;
	
	public DetalheController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("detalheCompraGeral");
		venda = (Venda) flash.get("detalheCompraGeral");
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
