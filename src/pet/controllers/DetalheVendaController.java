package pet.controllers;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.model.Venda;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable{

	private static final long serialVersionUID = -6719949836747729139L;

	private Venda venda;
	
	public DetalheVendaController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("detalheCompra");
		venda = (Venda) flash.get("detalheCompra");
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
