package pet.controllers;

import java.io.Serializable;

import pet.application.Util;
import pet.dao.DAO;
import pet.model.Entity;

public abstract class Controller <T extends Entity<T>> implements Serializable {

	private static final long serialVersionUID = -2022582742025295921L;
	
	protected T entity = null;
	protected DAO<T> dao = null;
	
	public Controller(DAO<T> dao) {
		super();
		this.dao = dao;
	}
	
	public abstract T getEntity();
	
	public void setEntity(T entity) {
		this.entity = entity;
	}

	public void incluir() {
		if (validarDados()) {
			if (dao.create(getEntity())) {
				limpar();
				Util.addInfoMessage("Inclusão realizada com sucesso.");
			} else {
				Util.addErrorMessage("Erro ao incluir no banco de dados.");
			}
		}
		
	}

	public void alterar() {
		if (validarDados()) {		
			if (dao.update(getEntity())) {
				limpar();
				Util.addInfoMessage("Alteração realizada com sucesso.");
			} else {
				Util.addErrorMessage("Erro ao alterar no banco de dados.");
			}
		}
	}

	public void remover() {
		if (dao.delete(getEntity().getId())) {
			limpar();
			Util.addInfoMessage("Remoção realizada com sucesso.");
		} else {
			Util.addErrorMessage("Erro ao remover no banco de dados.");
		}
	}

	public void editar(T entity) {
		entity = dao.findById(entity.getId());
		setEntity(entity);
	}
	
	public boolean validarDados() {
		return true;
	}
	
	public void limpar() {
		entity = null;
	}
	

}