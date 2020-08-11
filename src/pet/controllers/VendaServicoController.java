package pet.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pet.application.Session;
import pet.application.Util;
import pet.dao.ServicoDAO;
import pet.model.ItemVenda;
import pet.model.Servico;

@Named
@ViewScoped
public class VendaServicoController implements Serializable {

	private static final long serialVersionUID = -8413311224021825448L;

	private String descricao;
	private List<Servico> listaServico = null;

	public void pesquisar() {
		listaServico = null;
	}

	public void adicionar(int idServico) {
		ServicoDAO dao = new ServicoDAO();
		Servico servico = dao.findById(idServico);
		// verifica se existe um carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona um carrinho (de itens de venda) na sessao
			Session.getInstance().setAttribute("carrinho", new ArrayList<ItemVenda>());
		}

		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// criando um item de venda para adicionar no carrinho
		ItemVenda item = new ItemVenda();
		item.setServico(servico);
		item.setValor(servico.getPreco());

		// adicionando o item no carrinho (variavel local)
		carrinho.add(item);

		// atualizando o carrinho na sessao
		Session.getInstance().setAttribute("carrinho", carrinho);

		Util.addInfoMessage("Livro adicionado no carrinho. " + "Quantidade de Itens: " + carrinho.size());
		
	}

	public List<Servico> getListaServico() {
		if (listaServico == null) {
			ServicoDAO dao = new ServicoDAO();
			listaServico = dao.findByNome("");
			if (listaServico == null)
				listaServico = new ArrayList<Servico>();
		}
		return listaServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
