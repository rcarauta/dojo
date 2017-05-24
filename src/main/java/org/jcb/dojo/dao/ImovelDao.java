package org.jcb.dojo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.jcb.dojo.dominio.Imovel;

public class ImovelDao extends DAOEntityManagerGenerico<Imovel, Long> {	
	
	public ImovelDao(){
		
	}	
	
	public List<Imovel> recuperarTodosFetch(EntityManager em) {
		return em.createNamedQuery("Imovel.recuperarTodosFetch").getResultList();
	}
}
