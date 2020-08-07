package pet.model;

import java.time.LocalDate;
import java.util.List;

public class Venda extends Entity<Venda> {

	private LocalDate data;
	private Usuario usuario;
	private List<ItemVenda> listaItemVenda;

	private Float totalVenda = 0.0f;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

	public Float getTotalVenda() {
		Float resultado = 0.0f;	
		for (ItemVenda itemVenda : getListaItemVenda()) {
			resultado += itemVenda.getValor();
		}
		
		return resultado;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}

}
