package bancario.projeto.app;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Scanner;

import bancario.projeto.persistencia.PersistenciaArquivo;
import bancario.projeto.model.ContaCorrente;
import bancario.projeto.model.ContaPoupanca;
import bancario.projeto.model.IContaBancaria;
import bancario.projeto.model.Cliente;

public class Programa {

    public static void main(String[] args) {
        PersistenciaArquivo p = new PersistenciaArquivo();
        Scanner sc = new Scanner(System.in);
        boolean sair = false;
        
        while (!sair) {
            System.out.println("\nEscolha o numero da ação que voce deseja fazer:\n"
                    + "1 - Cadastrar de Cliente\n"
                    + "2 - Remover Cliente\n"
                    + "3 - Listar Clientes\n"
                    + "4 - Ações do Cliente\n"
                    + "5 - Sair\n");
            
            int opcao = sc.nextInt();
            
            switch(opcao) {
                case 1: {
                	String nome;
                	String cpf;
                	Scanner n = new Scanner(System.in);
                	System.out.println("Insira seu nome:");
                    nome = n.nextLine();
                    System.out.println("Insira seu CPF:");
                    cpf = n.next();
                    p.addCliente(new Cliente(cpf, nome));
                    break;
                }
                case 2: {
                    System.out.println("Insira o CPF do cliente a ser removido:");
                    String cpf = sc.next();
                    Cliente temp = new Cliente();
                    temp.setCpf(cpf);
                    p.removerCliente(temp);
                    break;
                }
                case 3: {
                    p.listarClientes();
                    break;
                }
                case 4: {
                    menuCliente(p, sc);
                    break;
                }
                case 5: {
                    sair = true;
                    System.out.println("Saindo do sistema...");
                    break;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
    
    private static void menuCliente(PersistenciaArquivo p, Scanner sc) {
        System.out.println("Insira o CPF do cliente:");
        String cpf = sc.next();
        Cliente cliente = p.localizarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.println(cliente);
        
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\nEscolha o numero da ação que voce deseja fazer:\n"
                    + "1 - Criar uma Nova Conta\n"
                    + "2 - Listar suas Contas\n"
                    + "3 - Remover Conta\n"
                    + "4 - Realizar Depósito\n"
                    + "5 - Realizar Saque\n"
                    + "6 - Transferir entre Contas\n"
                    + "7 - Consultar Saldo de uma Conta\n"
                    + "8 - Consultar Extrato Bancário (Transações)\n"
                    + "9 - Consultar Balanço Geral\n"
                    + "10 - Voltar\n");
            
            int opcao = sc.nextInt();
            
            switch(opcao) {
                case 1: { // Criar Conta
                    System.out.println("Escolha o tipo de conta:\n1 - Conta Corrente\n2 - Conta Poupança");
                    int tipo = sc.nextInt();
                    IContaBancaria conta = null;
                    // Para simplificação, estamos gerando um número aleatório dentro dos construtores
                    if (tipo == 1) {
                        conta = new ContaCorrente();
                    } else if (tipo == 2) {
                        conta = new ContaPoupanca();
                    } else {
                        System.out.println("Tipo inválido. Conta não criada.");
                        break;
                    }
                    cliente.addConta(conta);
                    p.atualizarCliente(cliente);
                    break;
                }
                case 2: {
                    p.listarContasDoCliente(cpf);
                    break;
                }
                case 3: {
                    System.out.println("Digite o número da conta que sera removida:");
                    int numero = sc.nextInt();
                    IContaBancaria conta = cliente.localizarContaPorNumero(numero);
                    if (conta != null) {
                        cliente.removerConta(conta);
                        p.atualizarCliente(cliente);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                }
                case 4: {
                    System.out.println("Digite o número da conta desejada:");
                    int numero = sc.nextInt();
                    IContaBancaria conta = cliente.localizarContaPorNumero(numero);
                    if (conta != null) {
                        System.out.println("Digite o valor a ser depositado:");
                        BigDecimal quantia = sc.nextBigDecimal();
                        try {
                            conta.realizarDeposito(quantia);
                            cliente.atualizarConta(conta);
                            p.atualizarCliente(cliente);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } 
                    break;
                }
                case 5: {
                    System.out.println("Digite o número da conta desejada:");
                    int numero = sc.nextInt();
                    IContaBancaria conta = cliente.localizarContaPorNumero(numero);
                    if (conta != null) {
                        System.out.println("Digite o valor a ser sacado:");
                        BigDecimal quantia = sc.nextBigDecimal();
                        try {
                            conta.realizarSaque(quantia);
                            cliente.atualizarConta(conta);
                            p.atualizarCliente(cliente);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 6: {
                    System.out.println("Digite o número da sua conta (origem):");
                    int numeroOrigem = sc.nextInt();
                    IContaBancaria contaOrigem = cliente.localizarContaPorNumero(numeroOrigem);
                    if (contaOrigem == null) {
                        System.out.println("Conta de origem não encontrada.");
                        break;
                    }
                    System.out.println("Digite o valor da transferência:");
                    BigDecimal quantia = sc.nextBigDecimal();
                    System.out.println("Digite o número da conta de destino:");
                    int numeroDestino = sc.nextInt();
                    IContaBancaria contaDestino = cliente.localizarContaPorNumero(numeroDestino);
                    
                    if (contaDestino == null) {
                        // Se a conta destino não pertence ao mesmo cliente, procuramos entre os demais clientes
                        for (Cliente cTemp : p.getClientes()) {
                            if (!cTemp.equals(cliente)) {
                                contaDestino = cTemp.localizarContaPorNumero(numeroDestino);
                                if (contaDestino != null) {
                                    break;
                                }
                            }
                        }
                    }
                    if (contaDestino == null) {
                        System.out.println("Conta de destino não existente.");
                    } else {
                        try {
                            contaOrigem.realizarTransferencia(contaDestino, quantia);
                            p.atualizarCliente(cliente);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 7: {
                    System.out.println("Digite o número da conta:");
                    int numConta = sc.nextInt();
                    IContaBancaria cTemp = cliente.localizarContaPorNumero(numConta);
                    cliente.consultarSaldo(cTemp);
                    break;
                }
                case 8:{
                	System.out.println("Digite o número da conta para imprimir o extrato:");
                    for (IContaBancaria c : cliente.getContas()) {
                        System.out.println("Número da conta: " + c.getNumeroConta());
                    }
                    int numConta = sc.nextInt();
                    sc.nextLine();
                    IContaBancaria contaEscolhida = cliente.localizarContaPorNumero(numConta);
                    if (contaEscolhida != null) {
                        System.out.println("Digite o ano:");
                        int year = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Digite o mês (1 a 12):");
                        int mesInt = sc.nextInt();
                        sc.nextLine();
                        try {
                            Month mes = Month.of(mesInt);
                            System.out.println("Extrato da conta " + contaEscolhida.getNumeroConta() + " para " + mes + " de " + year + ":");
                            contaEscolhida.consultarExtrato(mes, year);
                        } catch (Exception e) {
                            System.out.println("Erro ao imprimir extrato: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                }
                                
                case 9: {
                    cliente.balancoEntreContas();
                    break;
                }
                case 10: {
                    voltar = true;
                    System.out.println("Voltando para o menu principal...");
                    break;
                }
                default:
                    System.out.println("Opção inválida.");
            	}
            }
        }
    }
       
    
