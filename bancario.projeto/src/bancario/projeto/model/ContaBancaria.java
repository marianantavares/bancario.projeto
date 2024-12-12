package bancario.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ContaBancaria implements Serializable{
	/*serializable - processo de converter um obj em uma sequencia de bytes, afim de
	 *  realizar uma transmissao via rede ou salvar em um arquivo de persistencia 
	 */
	
	private static final long serialVersionUID = 1L;
	//Identificador de versao da classe para garantir compatibilidade durante serializacao.
	
	private Integer numeroConta;
	private float saldo;
	private LocalDateTime dataAbertura;
	private boolean status;

	public ContaBancaria() {
		this.numeroConta = numeroConta;
		this.saldo = 0f;
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
	}
	
	public void depositar(float quantia) {
		if (status) {
			if (quantia > 0) {
				this.saldo += quantia;
				System.out.println("Deposito realizado com sucesso.");
			} else {
				System.err.println("Valor invalido para deposito.");
			}
		} else {
			System.err.println("Operacao nao permitida. Conta desativada.");
		}
	}
	
	public void sacar(float quantia) {
		if (status) {
			if (quantia > 0) {
				if (this.saldo >= quantia) {
					this.saldo -= quantia;
					System.out.println("Saque realizado com sucesso!");
				} else {
					System.err.println("Saldo insuficiente.");
				}
			} else {
				System.err.println("Valor invalido para saque.");
			}
		} else {
			System.err.println("Operacao nao permitida. Conta desativada.");
		}

	}

	public void transferir(ContaBancaria destino, float quantia) {
		if (status && destino.isStatus()) {
			if (quantia <= 0) {
				System.err.println("Valor invalido para transferencia.");
			} else if (quantia <= saldo) {
				this.saldo -= quantia;
				destino.saldo += quantia;
			} else
				System.err.println("Saldo insuficiente para realizar a transferencia.");
		} else {
			System.err.println("Operacao nao pode ser realizada entre contas desativadas.");
		}
	}
		

	@Override
	public int hashCode() {
		return Objects.hash(numeroConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaBancaria other = (ContaBancaria) obj;
		return Objects.equals(numeroConta, other.numeroConta);
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "\nContaBancaria: \nnumeroConta=" + numeroConta + "\nsaldo=" + saldo + "\ndataAbertura=" + dataAbertura
				+ "\nstatus=" + status;
	}
}
