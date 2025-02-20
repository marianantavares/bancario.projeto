package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private BigDecimal quantia;
    private LocalDateTime dataTransacao;
    private TipoTransacao tipoTransacao;
    private IContaBancaria destino;
    
    public Transacao(BigDecimal quantia, LocalDateTime dataTransacao, TipoTransacao tipoTransacao, IContaBancaria destino) {
        this.id = new Random().nextLong();
        this.quantia = quantia;
        this.dataTransacao = dataTransacao;
        this.tipoTransacao = tipoTransacao;
        this.destino = destino;
    }
    
    public Transacao() { }

    public Long getId() {
        return id;
    }

    public BigDecimal getQuantia() {
        return quantia;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public IContaBancaria getDestino() {
        return destino;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transacao other = (Transacao) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Transacao [id=" + id + ", quantia=" + quantia + ", dataTransacao=" + dataTransacao + ", tipoTransacao="
                + tipoTransacao + ", destino=" + destino + "]";
    }
}
