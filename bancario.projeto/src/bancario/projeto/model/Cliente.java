package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable{
	/*serializable - processo de converter um obj em uma sequencia de bytes, afim de
	 *  realizar uma transmissao via rede ou salvar em um arquivo de persistencia 
	 */
	
	private static final long serialVersionUID = 1L;
	//Identificador de versao da classe para garantir compatibilidade durante serializacao.
	
	private String cpf;
	private String nome;
	
	private ArrayList<ContaBancaria> contas;//lista de contas do cliente
	
	public Cliente() {
		// Construtor padrao.
	}
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		contas = new ArrayList<>(); // Inicializa a lista de contas.
	}
	//funcoes com o arraylist
	public void addConta(ContaBancaria c){
		if(contas.contains(c)) {// Verifica se a conta ja esta associada ao cliente.
			System.out.println("conta ja cadastrada");
		}else {
			contas.add(c);//caso contrario, a conta eh add a lista
			System.out.println("conta cadastrada com sucesso");
		}
	}
	
	public void removerConta(ContaBancaria c) {
		if(contas.contains(c)){
			contas.remove(c);
			System.out.println("conta removida com sucesso");
		}else
			System.out.println("conta nao localizada");
	}
	
	
	public ContaBancaria localizarContaPorNumero(Integer numeroConta) {
		 // Cria um objeto temporario para buscar.
		ContaBancaria temp = new ContaBancaria();
		temp.setNumeroConta(numeroConta);
		if(contas.contains(temp)) {// Verifica se a conta temp existe.
			int index = contas.indexOf(temp);// pega o indice da conta temp.
			temp = contas.get(index);
			return temp;
		}
		return null;
	}
	
	public void atualizarConta(ContaBancaria c) {
		if(contas.contains(c)) {
			int index = contas.indexOf(c);// pega o indice da conta c.
			contas.set(index, c);//atualiza
			System.out.println("conta autalizada com sucesso!");
		}else
			System.out.println("conta nao encontrada");
	}
	
    public float balancoEntreContas() {
    	
		float ValorSaldo = 0;
		for (int i = 0; i < contas.size(); i++) {
			ContaBancaria c = contas.get(i);
			ValorSaldo += c.getSaldo();// Soma os saldos de todas as contas.
		}

		System.out.print("Balanco entre contas: RS" + ValorSaldo );
		return ValorSaldo;
    }
    
    public void consultarSaldo(ContaBancaria c) {
	    System.out.println("Saldo atual: R$ " +c.getSaldo());
	}
	
    
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<ContaBancaria> getContas() {
		return contas;
	}
	public void setContas(ArrayList<ContaBancaria> contas) {
		this.contas = contas;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	@Override
	public String toString() {
		return "\nCliente: \ncpf=" + cpf + "\nnome=" + nome + "\ncontas=" + contas;
	}
	
}