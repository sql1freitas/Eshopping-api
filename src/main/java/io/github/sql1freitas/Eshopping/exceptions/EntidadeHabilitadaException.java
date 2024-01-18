package io.github.sql1freitas.Eshopping.exceptions;

public class EntidadeHabilitadaException extends RuntimeException{

    public EntidadeHabilitadaException (){ super("Essa entidade já está habilitada");}

    public EntidadeHabilitadaException(String message){
        super(message);
    }
}
