package io.github.sql1freitas.Eshopping.exceptions;

public class ValorInvalidoException extends RuntimeException{

    public ValorInvalidoException(){ super("Valor digitado inválido!");}

    public ValorInvalidoException(String message){
        super(message);
    }
}
