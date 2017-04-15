package br.ufpe.nti.controller.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodrigo.venancio
 *
 * @param <T>
 */
public interface IGenericDAO<T> extends Serializable{
	
	
	/**
	 * Recupera em uma lista, todas as instancias dessa classe no banco
	 * @return
	 */
	public List<T> findAll();	
	/**
	 * Nao funciona: Recupera um objeto no banco pelo id
	 * @return
	 */
	public T findByID(Object id);
	/**
	 * Insere a instancia de um objeto no banco
	 * @return
	 */
	public void insert (T classe);
	/**
	 * Remove a instancia de um objeto do banco
	 * @return
	 */
	public void delete(T classe);	
	/**
	 * Atualiza a instancia de um objeto no banco
	 * @return
	 */
	public void update (T classe);
	
	
		
}