package bancario.projeto.model;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import bancario.projeto.exceptions.ContaException;
import bancario.projeto.exceptions.ValorException;

public interface IContaBancaria {

    public final float TAXA_ADMINISTRATIVA = 0.02f; 


    default void desativarConta() {
        if (isStatus()) {
            setStatus(false);
        }
    }

    public boolean isStatus(); 
    public void setStatus(boolean status);
    public BigDecimal getSaldo();
    public void setSaldo(BigDecimal novoSaldo);
    
    public void realizarSaque(BigDecimal quantia) throws ValorException, ContaException;
    public BigDecimal consultarSaldo();
    public void realizarDeposito(BigDecimal quantia) throws ValorException, ContaException;
    public void consultarExtrato(Month mes, int ano);
    
    public List<Transacao> getTransacoes();
    
    public void realizarTransferencia(IContaBancaria destino, BigDecimal quantia) throws ValorException, ContaException;
    
    public Object getNumeroConta();
}
