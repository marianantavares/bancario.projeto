package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bancario.projeto.exceptions.ContaException;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cpf;
    private String nome;
    private List<IContaBancaria> contas;

    public Cliente() {
        this.contas = new ArrayList<>();
    }

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public void addConta(IContaBancaria c) {
        if (!contas.contains(c)) {
            contas.add(c);
            System.out.println("Conta cadastrada com sucesso");
        } else {
            System.out.println("Conta já cadastrada");
        }
    }

    public void removerConta(IContaBancaria c) {
        if (contas.remove(c)) {
            System.out.println("Conta removida com sucesso");
        } else {
            System.out.println("Conta não localizada");
        }
    }

    public IContaBancaria localizarContaPorNumero(Integer numeroConta) {
        for (IContaBancaria conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }

    public void atualizarConta(IContaBancaria c) {
        int index = contas.indexOf(c);
        if (index != -1) {
            contas.set(index, c);
            System.out.println("Conta atualizada com sucesso!");
        } else {
            System.out.println("Conta não encontrada");
        }
    }

    public BigDecimal balancoEntreContas() {
        BigDecimal saldoTotal = BigDecimal.ZERO;
        for (IContaBancaria c : contas) {
            saldoTotal = saldoTotal.add(c.getSaldo());
        }
        System.out.println("Balanço entre contas: R$ " + saldoTotal);
        return saldoTotal;
    }

    public void consultarSaldo(IContaBancaria c) throws ContaException {
        if (c != null) {
            System.out.println("Saldo atual: R$ " + c.getSaldo().setScale(2, RoundingMode.HALF_UP));
        } else {
        	throw new ContaException ("Conta nao encontrada");
        }
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

    public List<IContaBancaria> getContas() {
        return contas;
    }

    public void setContas(List<IContaBancaria> contas) {
        this.contas = contas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(cpf, other.cpf);
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " \nCPF: " + cpf + " \nContas:\n" + contas + "\n";
    }
}
