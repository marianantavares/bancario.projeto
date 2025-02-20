package bancario.projeto.exceptions;

public class ValorException extends Exception{ //excessao verificada, ou seja, obriga que as 
	//assinaturas dos metodos sejam transcritas igualmente,na interface e nas classes implementadas

/**
* 
*/
private static final long serialVersionUID = 1L;
//Identificador de versao da classe para garantir compatibilidade durante serializacao.

public ValorException(String erro) {
super(erro);
}

}