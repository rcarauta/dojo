package org.jcb.dojo.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jcb.dojo.dao.EnderecoDao;
import org.jcb.dojo.dominio.Endereco;

@Stateless
public class EnderecoEJB {
	
	private EnderecoDao enderecoDao;
	
	@PersistenceContext(unitName="dojoPUH2")
	private EntityManager em;
	
	public EnderecoEJB() {
		enderecoDao = new EnderecoDao();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void criar(Endereco endereco) throws MinhaException {
		enderecoDao.persistir(endereco,em);
		//throw new MinhaException("erro no cadastro");
	}
	
	public List<Endereco> recuperarTodos(){
		return enderecoDao.listarTodos(em);
	}
	
	
}
