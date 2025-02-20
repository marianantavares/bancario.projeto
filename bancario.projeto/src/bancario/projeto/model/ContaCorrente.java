package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import bancario.projeto.exceptions.ContaException;
import bancario.projeto.exceptions.ValorException;


public class ContaCorrente implements IContaBancaria, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numero;
	private BigDecimal saldo;
	private LocalDateTime dataAbertura;
	private boolean status;
	private List<Transacao> transacoes = new ArrayList<>();

	public ContaCorrente() {
		    // Chama o construtor parametrizado para garantir a inicialização dos atributos
		    this(new Random().nextInt(9999));
	}

	public ContaCorrente(Integer numero) {
		// Gera um número aleatório para a conta
		this.numero = new Random().nextInt(9999);
		this.saldo = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
			
	}

	public Integer getNumeroConta() {
		return numero;
	}

	@Override
	public BigDecimal getSaldo() {
		return saldo;
	}

	@Override
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	@Override
	public boolean isStatus() {
		return status;
	}

	@Override
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public void realizarSaque(BigDecimal quantia) throws ValorException, ContaException {
		if (!isStatus()) {
			throw new ContaException("Conta desativada");
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0 || quantia.compareTo(saldo) > 0) {
			throw new ValorException("Saldo insuficiente ou quantia inválida");
		}
		quantia = quantia.setScale(2, RoundingMode.HALF_EVEN);
		saldo = saldo.subtract(quantia);
		transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.DEBITO, null));
	}

	@Override
	public BigDecimal consultarSaldo() {
		return saldo.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public void realizarDeposito(BigDecimal quantia) throws ContaException, ValorException {
		if (!isStatus()) {
			throw new ContaException("Conta desativada");
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValorException("Quantia inválida para depósito");
		}
		quantia = quantia.setScale(2, RoundingMode.HALF_EVEN);
		saldo = saldo.add(quantia);
		transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.CREDITO, null));
	}

	@Override
	public void realizarTransferencia(IContaBancaria destino, BigDecimal quantia) throws ValorException, ContaException {
		if (!isStatus()) {
			throw new ContaException("Conta desativada");
		}
		if (!destino.isStatus()) {
			throw new ContaException("Conta destino desativada");
		}
		// Se a conta destino for poupança, aplicar taxa administrativa
		BigDecimal valorTotal = quantia;
		if (destino instanceof ContaPoupanca) {
			BigDecimal taxa = quantia.multiply(new BigDecimal(TAXA_ADMINISTRATIVA));
			valorTotal = quantia.add(taxa);
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0 || valorTotal.compareTo(saldo) > 0) {
			throw new ValorException("Saldo insuficiente ou quantia inválida");
		}
		quantia = quantia.setScale(2, RoundingMode.HALF_EVEN);
		saldo = saldo.subtract(valorTotal);
		destino.realizarDeposito(quantia);
		transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.DEBITO_TRANSACAO, destino));
		destino.getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.CREDITO_TRANSACAO, this));
	}

	public void consultarExtrato(Month mes, int ano) throws ContaException {
		for (Transacao t : transacoes) {
			if(t.getDataTransacao().getMonth() == mes && t.getDataTransacao().getYear() == ano) {
				System.out.println(t);
			}
		}
	}

	@Override
	public List<Transacao> getTransacoes() {
		return transacoes;
	}



	@Override
	public String toString() {
		return "Conta Corrente - Número da Conta: " + numero + "| Saldo: R$ " + saldo.setScale(2, RoundingMode.HALF_UP) + "| Data Abertura: " + dataAbertura + "| Status: " + (status ? "Ativa" : "Desativada") + "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		return Objects.equals(numero, other.numero);
	}
}
