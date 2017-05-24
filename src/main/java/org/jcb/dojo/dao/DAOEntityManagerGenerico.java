package org.jcb.dojo.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Stateless
public abstract class DAOEntityManagerGenerico<T, ID extends Serializable> implements IDAOGenerico<T, ID>, Serializable {

	private static final long serialVersionUID = -2015707081680584794L;

	private final Class<T> persistentClass;


	@SuppressWarnings("unchecked")
	public DAOEntityManagerGenerico() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public T consultarPorID(ID id, boolean lock, EntityManager em) {
		T entity;
		if (lock) {
			entity = (T) em.find(getPersistentClass(), id, LockModeType.WRITE);
		} else {
			entity = (T) em.find(getPersistentClass(), id);
		}
		return entity;
	}

	/**
	 * Procura objeto com id informado Se objeto existir, retorna objeto, caso
	 * contrário, retorna null.
	 * 
	 * @param id
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public T procuraPorID(ID id, EntityManager em) {
		return (T) em.find(getPersistentClass(), id);
	}

	@Override
	public void excluir(T entity, EntityManager em) {
		em.remove(entity);
	}

	public void excluirPorID(ID id, EntityManager em) {
		T entity = consultarPorID(id, false, em);
		excluir(entity, em);
	}

	@Override
	public T persistir(T entity, EntityManager em) {
		em.persist(entity);
		return entity;
	}

	public void alterar(T entity, EntityManager em) {
		em.merge(entity);
	}

	/**
	 * Retorna true se objeto com id existe e false caso contrario
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean existe(ID id, EntityManager em) {
		T entity;
		entity = (T) em.find(getPersistentClass(), id);
		if (entity != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<T> listarTodos(EntityManager em) {
		return em.createQuery("select i from "+ getPersistentClass().getName() +" i ", getPersistentClass()).getResultList();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

}