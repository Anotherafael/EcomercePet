package pet.controllers;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.application.Session;
import pet.application.Util;
import pet.dao.VendaDAO;
import pet.model.ItemVenda;
import pet.model.Usuario;
import pet.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = 780477496476930858L;

	private Venda venda;
	private int tamanho = 0;

	public int getTamanho() {
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		if (carrinho != null) {
			return carrinho.size();
		}
		return 0;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();

		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// adicionando os itens do carrinho na venda
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		venda.setListaItemVenda(carrinho);

		return venda;
	}

	public String remover(int idProduto) {

		// Obtendo o carrinho da sessão.

		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		int cont = 0;
		for (ItemVenda itemVenda : carrinho) {
			if (itemVenda.getServico().getId() == idProduto) {
				carrinho.remove(cont);
				break;
			}
			cont++;
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "carrinho.xhtml?faces-redirect=true";
	}

	public String finalizar() {
		Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		// montar a venda

		if (venda.getListaItemVenda().size() == 0) {
			Util.addErrorMessage("Carrinho está vazio");
			return "";
		}

		Venda venda = new Venda();
		venda.setData(LocalDate.now());
		venda.setUsuario(usuario);
		venda.setListaItemVenda(carrinho);

		// salvar no banco
		VendaDAO dao = new VendaDAO();
		if (dao.create(venda)) {
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
			Util.addInfoMessage("Venda realizada com sucesso");
			return "carrinho.xhtml?faces-redirect=true";
		} else {
			Util.addErrorMessage("Erro ao finalizar a Venda.");
		}
		return "";
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
