package org.jcb.dojo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jcb.dojo.dao.ClienteDao;
import org.jcb.dojo.dominio.Cliente;
import org.jcb.dojo.dominio.Endereco;
import org.jcb.dojo.dominio.PessoaFisica;
import org.jcb.dojo.dominio.PessoaJuridica;

@Stateless
public class ClienteEJB {

	private ClienteDao clienteDao;
	
	private List<Endereco> listaEndereco;
	
	@PersistenceContext(unitName="dojoPUH2")
	private EntityManager em;
	
	public ClienteEJB() {
		clienteDao = new ClienteDao();
	}
	
	
	public Cliente salvar(Cliente cliente, Endereco endereco) {
		if(endereco.getId() == null){
			endereco = em.merge(endereco);
		}
		
		getListaEndereco().add(endereco);
		cliente.setEndereco(getListaEndereco());
		
		return clienteDao.persistir(cliente, em);
	}
	
	public List<Cliente> listarTodos(){
		return clienteDao.listarTodos(em);
	}


	public Cliente salvarPessoaFisica(PessoaFisica pessoaFisica) {
		 em.persist(pessoaFisica);	 
		 return pessoaFisica;	
	}


	public Cliente salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		em.persist(pessoaJuridica);
		return pessoaJuridica;
	}


	public List<Endereco> getListaEndereco() {
		if(listaEndereco == null){
			listaEndereco = new ArrayList<>();
		}
		return listaEndereco;
	}


	public void setListaEndereco(List<Endereco> listaEndereco) {
		this.listaEndereco = listaEndereco;
	}
}
