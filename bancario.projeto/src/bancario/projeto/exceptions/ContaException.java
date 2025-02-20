package bancario.projeto.exceptions;

public class ContaException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Identificador de versao da classe para garantir compatibilidade durante serializacao.
	
	public ContaException(String erro) {
		super(erro);
	}
	
}
