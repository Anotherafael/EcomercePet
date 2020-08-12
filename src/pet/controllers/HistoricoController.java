package pet.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.dao.VendaDAO;
import pet.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable {

private static final long serialVersionUID = -8585030860902916284L;
	
	private List<Venda> listaVenda = null;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			listaVenda = dao.findAll();
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
		}
		return listaVenda;
	}
	
	public String detalhes(Venda venda) {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.put("detalheCompraGeral", venda);
		
		return "detalhes.xhtml?faces-redirect=true";
	}
}
