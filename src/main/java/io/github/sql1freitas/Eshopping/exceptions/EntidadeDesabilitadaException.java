package io.github.sql1freitas.Eshopping.exceptions;

public class EntidadeDesabilitadaException extends RuntimeException{

    public EntidadeDesabilitadaException(){super("Essa entidade já está desabilitada");}

    public EntidadeDesabilitadaException(String message) {
        super(message);
    }
}
