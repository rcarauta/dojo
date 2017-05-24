package org.jcb.dojo.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jcb.dojo.dominio.Cliente;
import org.jcb.dojo.dominio.Endereco;
import org.jcb.dojo.dominio.PessoaFisica;
import org.jcb.dojo.dominio.PessoaJuridica;
import org.jcb.dojo.ejb.ClienteEJB;
import org.jcb.dojo.ejb.EnderecoEJB;

@ManagedBean(name = "clienteController")
@RequestScoped
public class ClienteController implements Serializable {
	
	private static final Integer CPF = 1;
	
	private static final Integer CNPJ = 2;
	
	@EJB
	private ClienteEJB clienteEJB;

	@EJB
	private EnderecoEJB enderecoEJB;

	private List<Endereco> listaEndereco;

	private List<Endereco> listaEnderecoSelecionados;
	
	private List<Cliente> listaCliente;

	private Cliente cliente;
	
	private PessoaFisica pessoaFisica;
	
	private PessoaJuridica pessoaJuridica;
	
	private Endereco endereco;
	
	private Integer cpfCnpj = 0;

	@PostConstruct
	public void init() {
		listaEndereco = enderecoEJB.recuperarTodos();
		listaEnderecoSelecionados = new ArrayList<>();
	}

	public void salvarCliente() {
		if(cpfCnpj == CPF){
			atribuirValoresParaPessoaFisica(cliente);
		 	clienteEJB.salvarPessoaFisica(pessoaFisica);
		} else if(cpfCnpj == CNPJ) {
			atribuirValoresPessoaJuridica(cliente);
			 clienteEJB.salvarPessoaJuridica(pessoaJuridica);
		} else{
			clienteEJB.salvar(cliente, endereco);
		}
		 FacesContext.getCurrentInstance().addMessage("clientes", new FacesMessage("Cliente Gravado com sucesso!!"));
		 limparCampos();
	}
	

	private void limparCampos() {
		cliente = new Cliente();
		pessoaFisica = new PessoaFisica();
		pessoaJuridica = new PessoaJuridica();
		endereco = new Endereco();
		
	}

	private void atribuirValoresPessoaJuridica(Cliente cliente) {
			getPessoaJuridica().setNome(cliente.getNome());
			getPessoaJuridica().setEndereco(cliente.getEndereco());
		
	}

	private void atribuirValoresParaPessoaFisica(Cliente cliente) {
		getPessoaFisica().setNome(cliente.getNome());
		getPessoaFisica().setEndereco(cliente.getEndereco());	
	}

	public ClienteEJB getClienteEJB() {
		return clienteEJB;
	}

	public void setClienteEJB(ClienteEJB clienteEJB) {
		this.clienteEJB = clienteEJB;
	}

	public EnderecoEJB getEnderecoEJB() {
		return enderecoEJB;
	}

	public void setEnderecoEJB(EnderecoEJB enderecoEJB) {
		this.enderecoEJB = enderecoEJB;
	}

	public List<Endereco> getListaEndereco() {
		return listaEndereco;
	}

	public void setListaEndereco(List<Endereco> listaEndereco) {
		this.listaEndereco = listaEndereco;
	}

	public List<Endereco> getListaEnderecoSelecionados() {
		return listaEnderecoSelecionados;
	}

	public void setListaEnderecoSelecionados(List<Endereco> listaEnderecoSelecionados) {
		this.listaEnderecoSelecionados = listaEnderecoSelecionados;
	}

	public Cliente getCliente() {
		if(cliente == null){
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		if(endereco == null){
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Integer getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(Integer cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public PessoaFisica getPessoaFisica() {
		if(pessoaFisica == null){
			pessoaFisica = new PessoaFisica();
		}
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public PessoaJuridica getPessoaJuridica() {
		if(pessoaJuridica == null){
			pessoaJuridica = new PessoaJuridica();
		}
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<Cliente> getListaCliente() {
		listaCliente = clienteEJB.listarTodos();
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
	

}
