package com.igorfv.projetoestudoapi.exception;

public class NegocioException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public NegocioException(String message){
        super(message);
    }

}
