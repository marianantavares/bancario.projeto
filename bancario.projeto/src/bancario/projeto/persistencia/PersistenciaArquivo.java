package bancario.projeto.persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;

public class PersistenciaArquivo implements Serializable{
	/*serializable - processo de converter um obj em uma sequencia de bytes, afim de
	 *  realizar uma transmissao via rede ou salvar em um arquivo de persistencia
	 */
	
	private static final long serialVersionUID = 1L;
	//Identificador de versao da classe para garantir compatibilidade durante serializacao.
	
	private ArrayList<Cliente> clientes; // Lista de clientes para persistência.

	public PersistenciaArquivo() {
		clientes = new ArrayList<Cliente>();//inicializa a lista de clientes
		carregarArquivo();//tenta carregar os clientes do arquivo txt
	}
	public void addCliente(Cliente c) {
		if (clientes.contains(c)) {
			System.out.println("cliente ja cadastrada");
		} else {
			clientes.add(c);
			System.out.println("cliente cadastrada com sucesso");
			salvarArquivo();
		}
	}


	public void removerCliente(Cliente c) {
		if (clientes.contains(c)) {
			clientes.remove(c);
			System.out.println("cliente removido com sucesso");
		} else
			System.out.println("cliente nao localizado");
	}

	public Cliente localizarClientePorCpf(String cpf) {
		Cliente temp = new Cliente();// Cria um cliente temporário para busca.
		temp.setCpf(cpf);
		if (clientes.contains(temp)) {//verifica se o temp esta na lista
			int index = clientes.indexOf(temp);
			temp = clientes.get(index);//retorna o cliente encontrado
			return temp;
		} else
			return null;
	}

	public void atualizarCliente(Cliente c) {
		if (clientes.contains(c)) {
			int index = clientes.indexOf(c);
			clientes.set(index, c);// Atualiza os dados.
			salvarArquivo();
			System.out.println("cliente autalizado com sucesso!");
		} else
			System.out.println("cliente n�o encontrado");
	}
	
	private void salvarArquivo() {
		/* salva a lista de clientes em um arquivo.Em outras palavras, 
		 * ele serializa a lista e a escreve em arquivos.txt.
		 */
		try {
			FileOutputStream fos = new FileOutputStream("arquivos.txt");//cria fluxo dos dados para o txt
			ObjectOutputStream oos = new ObjectOutputStream(fos);//permite escrever objetos em um fluxo de saída.
			oos.writeObject(clientes);// Serializa a lista de clientes.
			oos.close();//Fecha os fluxos
			fos.close();
		} catch (Exception e) {
		e.getMessage();
		}
	}
	
	private void carregarArquivo() {
		/* lê o arquivo arquivos.txt e carrega a lista de clientes,
		 * ou seja, desserializa os dados e restaura o objeto original.
		 */
		try {
			FileInputStream fis = new FileInputStream("arquivos.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			clientes = (ArrayList<Cliente>) ois.readObject();// Desserializa a lista.
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void listarClientes() {
	    if (clientes.isEmpty()) {
	        System.out.println("Nenhum cliente cadastrado.");
	    } else {
	    /* percorre a lista e imprime cada cliente. O método toString da
	     * classe Cliente será chamado automaticamente para exibir os detalhes.
	     */
	        for (Cliente c : clientes) {
	            System.out.println(c);
	        }
	    }
	}
	
	public void listarContasDoCliente(String cpf) {
	    Cliente cliente = localizarClientePorCpf(cpf);// Localiza o cliente.
	    if (cliente == null) {
	        System.out.println("Cliente não encontrado.");
	    } else {
	        if (cliente.getContas().isEmpty()) {// Verifica se o cliente possui contas.
	            System.out.println("O cliente não possui contas cadastradas.");
	        } else {
	            for (ContaBancaria conta : cliente.getContas()) {
	                System.out.println(conta);
	            }
	        }
	    }
	}
		
}
