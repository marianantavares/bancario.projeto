package bancario.projeto.app;
import java.util.Scanner;
import bancario.projeto.persistencia.PersistenciaArquivo;
import bancario.projeto.model.ContaBancaria;
import bancario.projeto.model.Cliente;

public class Programa {

	public static void main(String[] args) {
		
		PersistenciaArquivo p = new PersistenciaArquivo();
		Scanner sc = new Scanner(System.in);
		boolean sair = true;
		Integer opcao = 0;
		
		while(sair) {
			System.out.println("\nDigite a opcao desejada:\n"
					+ "1 - Cadastro de Cliente\n"
					+ "2 - Remover Cliente\n"
					+ "3 - Listar Clientes\n"
					+ "4 - Opcoes de Clientes\n"
					+ "5 - Para Sair\n");
			
			opcao = sc.nextInt();
			
			switch(opcao) {
			case 1:{
				String cpf;
				String nome;
				System.out.println("insira seu cpf");
				cpf = sc.next();
				System.out.println("insira seu nome");
				nome = sc.next();
				p.addCliente(new Cliente(cpf,nome));
				break;
			}
			case 2:{
				Cliente temp;
				String cpf;
				System.out.println("insira seu cpf");
				cpf = sc.next();
				temp = new Cliente();
				temp.setCpf(cpf);
				p.removerCliente(temp);
				break;
			}
			
			case 3:{
				p.listarClientes();
				break;
			}
			
			case 4:{
				menuCliente(p, sc);
				break;
			}
			case 5:{
				sair = false;
				System.out.println("Saindo do sistema...");
				break;
			}
			default:
			
				throw new IllegalArgumentException("Unexpected value:" +opcao);
			}
		}
	}
	
//-----------------menu secundario p/ opcoes de clientes-------------------------------	
	//para realizar transacoes, remover, add, verificar saldo e balanco, verificar contas e 
	private static void menuCliente(PersistenciaArquivo p, Scanner sc) {
      	
		ContaBancaria conta = new ContaBancaria();
		System.out.println("Insira o CPF do cliente:");
        String cpf = sc.next();
        Cliente cliente = p.localizarClientePorCpf(cpf);
        System.out.println(cliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        boolean voltar = false;
        while (!voltar) {//sair do while qnd voltar for true
            System.out.println("\nEscolha a opção desejada:\n"
                    + "1 - Criar Conta\n"
                    + "2 - Listar Contas\n"
                    + "3 - Remover Conta\n"
                    + "4 - Realizar Depósito\n"
                    + "5 - Realizar Saque\n"
                    + "6 - Transferir entre Contas\n"
                    + "7 - Consultar Saldo\n"
                    + "8 - Consultar Balanço Total\n"
                    + "9 - Voltar\n");

            int opcao = sc.nextInt();
            
            switch(opcao) {
            case 1:{
            	System.out.println("Digite o numero da conta:\n");
            	Integer numero = sc.nextInt();
            	conta.setNumeroConta(numero);
            	cliente.addConta(conta);
            	p.atualizarCliente(cliente);
            	break;
            }
            case 2:{
            	p.listarContasDoCliente(cpf);
            	break;
            }
            case 3:{
            	System.out.println("Digite o numero da conta para ser removida:\n");
            	Integer numero = sc.nextInt();
            	conta.setNumeroConta(numero);
            	cliente.removerConta(conta);
            	p.atualizarCliente(cliente);
            	break;
            }
            case 4:{
            	System.out.println("Insira o numero da conta desejada: \n");
            	Integer numero = sc.nextInt();
            	conta = cliente.localizarContaPorNumero(numero);
            	if(conta != null) {
            		System.out.println("digite o valor a ser depositado:\n");
                	float quantia = sc.nextFloat();
                	conta.depositar(quantia);
                	cliente.atualizarConta(conta);
                	p.atualizarCliente(cliente);
            	}
            	System.out.println("conta não encontrada");            	
            	break;
            }
            case 5:{
            	System.out.println("Insira o numero da conta desejada: \n");
            	Integer numero = sc.nextInt();
            	conta = cliente.localizarContaPorNumero(numero);
            	if(conta != null) {
            		System.out.println("digite o valor a ser sacado:\n");
                	float quantia = sc.nextFloat();
                	conta.sacar(quantia);
                	cliente.atualizarConta(conta);
                	p.atualizarCliente(cliente);
            	}
            	System.out.println("conta não encontrada");
            	break;
            }
            case 6:{
            	System.out.println("Insira o numero da sua conta (origem): \n");
            	Integer numero = sc.nextInt();
            	conta = cliente.localizarContaPorNumero(numero);
            	if(conta != null) {
            		System.out.println("Digite o numero da conta de destino:\n");
            		Integer numeroDestino = sc.nextInt();
            		System.out.println("digite o valor da transferencia:\n");
            		float quantia = sc.nextFloat();
            		ContaBancaria contaDestino = cliente.localizarContaPorNumero(numeroDestino);
            		if(contaDestino == null) {
            			return;
            		}
            		conta.transferir(contaDestino, quantia);
            		cliente.atualizarConta(conta);
            		p.atualizarCliente(cliente);
            	}
            }
            case 7:{
            	cliente.consultarSaldo(conta);
            	break;
            }
            case 8:{
            	cliente.balancoEntreContas();
            	break;
            }
            case 9:{
				voltar = true;
				System.out.println("Voltando para o menu principal...");
				break;
			}
            }
          
        }
	}     
}
