package bancario.projeto.model;


public enum TipoTransacao {
	
	CREDITO(1),
	DEBITO(2),
	CREDITO_TRANSACAO(3),
	DEBITO_TRANSACAO(4);
	
	private final int valor;
	
	private TipoTransacao(int valor) {
		
	this.valor = valor;
	
	}

	public int getValor() {
		return valor;
	}
	
	public static TipoTransacao getEnumFromValue(int valor) {
		for(TipoTransacao t : values()) {
			if(t.getValor() == valor)
				return t;
		}
		return null;
	}

}