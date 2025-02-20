package bancario.projeto.persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import bancario.projeto.model.Cliente;
import bancario.projeto.model.IContaBancaria;

public class PersistenciaArquivo implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Cliente> clientes;

    public PersistenciaArquivo() {
        clientes = new ArrayList<>();
        carregarArquivo();
    }
    public void addCliente(Cliente c) {
        if (clientes.contains(c)) {
            System.out.println("Cliente já cadastrada");
        } else {
            clientes.add(c);
            System.out.println("Cliente cadastrada com sucesso");
            salvarArquivo();
        }
    }

    public void removerCliente(Cliente c) {
        if (clientes.contains(c)) {
            clientes.remove(c);
            System.out.println("Cliente removido com sucesso");
            salvarArquivo();
        } else {
            System.out.println("Cliente não localizado");
        }
    }

    public Cliente localizarClientePorCpf(String cpf) {
        Cliente temp = new Cliente();
        temp.setCpf(cpf);
        if (clientes.contains(temp)) {
            int index = clientes.indexOf(temp);
            temp = clientes.get(index);
            return temp;
        } else
            return null;
    }

    public void atualizarCliente(Cliente c) {
        if (clientes.contains(c)) {
            int index = clientes.indexOf(c);
            clientes.set(index, c);
            salvarArquivo();
            System.out.println("Cliente atualizado com sucesso!");
        } else
            System.out.println("Cliente não encontrado");
    }

    private void salvarArquivo() {
        try {
            FileOutputStream fos = new FileOutputStream("arquivos.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clientes);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarArquivo() {
        try {
            FileInputStream fis = new FileInputStream("arquivos.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clientes = (ArrayList<Cliente>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            // Pode ser que o arquivo não exista na primeira execução
        }
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    public void listarContasDoCliente(String cpf) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            if (cliente.getContas().isEmpty()) {
                System.out.println("O cliente não possui contas cadastradas.");
            } else {
                for (IContaBancaria conta : cliente.getContas()) {
                    System.out.println(conta);
                }
            }
        }
    }
    
    public ArrayList<Cliente> getClientes(){
        return clientes;
    }
}
